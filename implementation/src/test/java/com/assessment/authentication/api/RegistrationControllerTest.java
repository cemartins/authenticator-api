package com.assessment.authentication.api;

import com.assessment.authentication.AuthenticationApplication;
import com.assessment.authentication.api.dto.AccountRegister;
import com.assessment.authentication.api.dto.JwtRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenAccount_whenRegister_thenReturnOk() throws Exception {

        mockMvc.perform(post("/accounts/register")
                .content("{\n" +
                        "\t\"username\": \"mememe\",\n" +
                        "\t\"password\": \"password\",\n" +
                        "\t\"account\": \"77853449\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is(alex.getName())));
    }

    private AccountRegister createRegistrationRequest() {
        AccountRegister request = new AccountRegister();
        request.setUsername("memememe");
        request.setPassword("password");
        request.setAccount(77853449);
        return request;
    }
}
