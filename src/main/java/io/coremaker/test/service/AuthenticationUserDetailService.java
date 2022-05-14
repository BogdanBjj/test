package io.coremaker.test.service;

import io.coremaker.test.domain.model.CustomUser;
import io.coremaker.test.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@Service
public class AuthenticationUserDetailService implements UserDetailsService {

    private final UserRepository repository;

    public AuthenticationUserDetailService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = repository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
