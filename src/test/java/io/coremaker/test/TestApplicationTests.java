package io.coremaker.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.coremaker.test.domain.request.LoginRequest;
import io.coremaker.test.domain.request.UserCreateRequest;
import io.coremaker.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Test
    public void usersUnauthenticated() throws Exception {
        mvc.perform(get("/users?username=b")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void usersAuthenticated() throws Exception {
        var user = new UserCreateRequest("bogdan", "123");
        userService.createUser(user);

        mvc.perform(get("/users?username=bogdan"))
                .andExpect(content().json("{\"email\":\"bogdan\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginUserNotFound() throws Exception {
        mvc.perform(formLogin("/login").user("b").password("123"))
                .andExpect(status().isNotFound())
                .andExpect(unauthenticated());
    }

    @Test
    public void loginUserInvalidRequestBody() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LoginRequest login = new LoginRequest("bogdan", "123");

        mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(login)))
                .andExpect(status().isBadRequest())
                .andExpect(unauthenticated());
    }

    @Test
    public void loginUserValidatedAndAuthenticated() throws Exception {
        var user = new UserCreateRequest("b@gmail.com", "123");
        userService.createUser(user);

        ObjectMapper mapper = new ObjectMapper();
        LoginRequest login = new LoginRequest("b@gmail.com", "123");

        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(login)))
                .andExpect(status().isOk());
    }

}