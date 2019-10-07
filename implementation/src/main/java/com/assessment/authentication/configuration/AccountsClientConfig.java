package com.assessment.authentication.configuration;

import ch.qos.logback.core.net.ssl.KeyStoreFactoryBean;
import org.apache.catalina.webresources.FileResource;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.security.*;
import java.security.cert.CertificateException;

@Configuration
public class AccountsClientConfig {

    @Value("${server.ssl.key-store}")
    private String keystorePath;

    @Value("${server.ssl.key-store-type}")
    private String keystoreType;

    @Value("${server.ssl.key-store-password}")
    private String keystorePassword;

    @Value("${server.ssl.trust-store}")
    private String truststorePath;

    @Value("${server.ssl.trust-store-type}")
    private String truststoreType;

    @Value("${server.ssl.trust-store-password}")
    private String truststorePassword;

    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(createHttpClient(applicationKeyAlias)));
    }

    private static Resource makeResource(final String path) {
        if (path.startsWith(FILE_RESOURCE_PREFIX)) {
            return new FileResource(new File(path.substring(FILE_RESOURCE_PREFIX.length())));
        } else if (path.startsWith(CLASSPATH_RESOURCE_PREFIX)) {
            return new ClassPathResource(path.substring(CLASSPATH_RESOURCE_PREFIX.length()));
        }
        // Assume a path without a known prefix is a file
        return new FileResource(new File(path));
    }

    private HttpClient createHttpClient(final String keyAlias) {
        logger.info("Creating HTTP client using keystore={} and alias={}", keystorePath, keyAlias);
        final KeyStore trustStore = new KeyStoreFactoryBean(
                makeResource("classpath:/truststore.jks"), "JKS", "changeit").newInstance();
        final KeyStore keyStore = new KeyStoreFactoryBean(
                makeResource(keystorePath), keystoreType, keystorePassword).newInstance();
        final SSLContext sslContext;
        try {
            sslContext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, keystorePassword.toCharArray(), (aliases, socket) -> keyAlias)
                    .loadTrustMaterial(trustStore, (x509Certificates, s) -> false)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | UnrecoverableKeyException e) {
            throw new IllegalStateException("Error loading key or trust material", e);
        }
        final SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                new String[] { "TLSv1.2" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(httpClientPoolSize);
        connectionManager.setDefaultMaxPerRoute(httpClientPoolSize);
        return HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .setConnectionManager(connectionManager)
                .build();
    }
}
