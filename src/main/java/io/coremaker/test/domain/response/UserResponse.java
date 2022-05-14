package io.coremaker.test.domain.response;

public class UserResponse {

    private String email;

    public UserResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
