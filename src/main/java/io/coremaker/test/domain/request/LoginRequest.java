package io.coremaker.test.domain.request;

import javax.validation.constraints.Email;

public class LoginRequest {

    @Email(message = "invalid email")
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
