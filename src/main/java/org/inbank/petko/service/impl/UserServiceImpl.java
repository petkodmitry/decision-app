package org.inbank.petko.service.impl;

import lombok.RequiredArgsConstructor;
import org.inbank.petko.entity.UserEntity;
import org.inbank.petko.repository.UserRepository;
import org.inbank.petko.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service methods implementations of {@link UserEntity} business logic
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }
}
