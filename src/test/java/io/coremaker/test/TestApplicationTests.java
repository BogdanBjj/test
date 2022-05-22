package io.coremaker.test;

import io.coremaker.test.domain.request.UserCreateRequest;
import io.coremaker.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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

}