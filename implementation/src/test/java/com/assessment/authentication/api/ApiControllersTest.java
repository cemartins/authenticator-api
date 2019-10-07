package com.assessment.authentication.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test
 * Run a sequence of requests
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ApiControllersTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenAccount_whenSequenceRequests_thenReturnOk() throws Exception {

        // Register an account
        mockMvc.perform(post("/accounts/register")
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


        // Authenticated user calls API without token to see account details
        mockMvc.perform(get("/accounts/77853449")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());


        String token = authenticationResult.getResponse().getContentAsString();
        assert token != null && !token.isEmpty();

        // Authenticated user calls API with token to see account details
        final MvcResult getDetailsResult = mockMvc.perform(get("/accounts/77853449")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("iban")))
                .andReturn();
        assert getDetailsResult != null;


    }

}
