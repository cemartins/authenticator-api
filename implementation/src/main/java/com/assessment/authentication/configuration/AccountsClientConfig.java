package com.assessment.authentication.configuration;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
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

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        KeyStore keyStore;
        KeyStore trustStore;
        HttpComponentsClientHttpRequestFactory requestFactory = null;

        try {
            keyStore = KeyStore.getInstance(keystoreType);
            InputStream inputStream = this.getClass().getResourceAsStream(keystorePath);
            keyStore.load(inputStream, keystorePassword.toCharArray());

            trustStore = KeyStore.getInstance(truststoreType);
            InputStream trustInputStream = this.getClass().getResourceAsStream(truststorePath);
            trustStore.load(trustInputStream, truststorePassword.toCharArray());

            SSLContextBuilder contextBuilder = new SSLContextBuilder();
            contextBuilder.setProtocol("TLS");
            contextBuilder.loadKeyMaterial(keyStore, keystorePassword.toCharArray());
            contextBuilder.setKeyStoreType(keystoreType);
            contextBuilder.loadTrustMaterial(trustStore, new TrustAllStrategy());

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(contextBuilder.build());

            final CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

            requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setConnectTimeout(10000); // 10 seconds
            requestFactory.setReadTimeout(10000); // 10 seconds

            restTemplate.setRequestFactory(requestFactory);

        } catch (KeyStoreException e) {
            throw new BeanInitializationException("Cannot instantiate keystore", e);
        } catch (IOException e) {
            throw new BeanInitializationException("Cannot read keystore path", e);
        } catch (CertificateException | NoSuchAlgorithmException e) {
            throw new BeanInitializationException("Cannot load keystore", e);
        } catch (UnrecoverableKeyException | KeyManagementException e) {
            throw new BeanInitializationException("Cannot create socket factory", e);
        }

        return restTemplate;
    }

}
