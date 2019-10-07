package com.assessment.authentication.client;

import com.assessment.authentication.client.dto.AccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountsApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(AccountsApiClient.class);

    @Value("${accounts-api.url-accounts}")
    private String accountsUrl;

    @Autowired
    private RestTemplate restTemplate;

    public AccountDto fetchAccount(Integer accountNumber) {
        try {
            final AccountDto account = restTemplate.getForObject(accountsUrl + "/" + accountNumber, AccountDto.class);
            return account;
        }
        catch (RestClientException e) {
            LOG.error("Error calling AccountsAPI", e);
        }
        return null;
    }
}
