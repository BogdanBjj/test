package io.coremaker.test.domain.response;

public class CustomToken {
    private String header;
    private String token;

    public CustomToken(String header, String token) {
        this.header = header;
        this.token = token;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
