package io.coremaker.test.service;

import io.coremaker.test.domain.model.CustomUser;
import io.coremaker.test.domain.request.UserCreateRequest;

public interface UserService {

    CustomUser findByUsername(String username);

    void createUser(UserCreateRequest request);

}
