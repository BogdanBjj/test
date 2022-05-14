package io.coremaker.test.service;

import io.coremaker.test.domain.request.UserCreateRequest;
import io.coremaker.test.domain.response.UserResponse;

public interface UserService {

    UserResponse findByUsername(String email);

    void createUser(UserCreateRequest request);

}
