package io.coremaker.test.service;

import io.coremaker.test.domain.model.CustomUser;
import io.coremaker.test.domain.request.UserCreateRequest;
import io.coremaker.test.domain.response.UserResponse;
import io.coremaker.test.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserResponse findByUsername(String email) {
        return repository.findByUsername(email)
                .map(u -> new UserResponse(u.getUsername()))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void createUser(UserCreateRequest request) {
        Optional<CustomUser> optional = repository.findByUsername(request.getUsername());
        if (optional.isPresent()) {
            throw new RuntimeException("Username already exists. Please choose a different one.");
        }

        CustomUser user = new CustomUser();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        repository.save(user);
    }
}
