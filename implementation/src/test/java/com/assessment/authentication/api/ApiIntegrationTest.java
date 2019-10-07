package com.assessment.authentication.api;

import com.assessment.authentication.client.AccountsApiClient;
import com.assessment.authentication.client.dto.AccountDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test
 * Run a sequence of requests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private AccountsApiClient seaccountsApiClient;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenAccount_whenSequenceRequests_thenReturnOk() throws Exception {

        AccountDto account = new AccountDto();
        account.setIban("NL24INGB7785344909");

        given(seaccountsApiClient.fetchAccount(77853449)).willReturn(account);

        // Register an account
        mockMvc.perform(post("/register")
                .content("{" +
                        "\"username\": \"mememe\"," +
                        "\"password\": \"password\"," +
                        "\"account\": \"77853449\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Authenticate the user previously registered
        final MvcResult authenticationResult = mockMvc.perform(post("/authenticate")
                .content("{" +
                        "\"username\": \"mememe\"," +
                        "\"password\": \"password\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String token = authenticationResult.getResponse().getContentAsString();
        assert token != null && !token.isEmpty();

    }

}
