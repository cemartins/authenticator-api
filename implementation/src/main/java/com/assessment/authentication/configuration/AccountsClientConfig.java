package com.assessment.authentication.configuration;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Configuration
public class AccountsClientConfig {

    @Value("${accounts-api.key-store}")
    private String keystorePath;

    @Value("${accounts-api.key-store-password}")
    private String keystorePassword;

    @Value("${accounts-api.trust-store}")
    private String truststorePath;

    @Value("${accounts-api.trust-store-password}")
    private String truststorePassword;

    @Bean
    public RestTemplate restTemplate() throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {

        final File keystoreFile = ResourceUtils.getFile(keystorePath);
        final File truststoreFile = ResourceUtils.getFile(truststorePath);

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(keystoreFile, keystorePassword.toCharArray(), keystorePassword.toCharArray())
                .loadTrustMaterial(truststoreFile, truststorePassword.toCharArray())
                .build();

        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(client);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        return restTemplate;
    }
}
