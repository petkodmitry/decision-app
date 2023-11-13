package org.inbank.petko.service;

import org.inbank.petko.entity.UserEntity;
import org.inbank.petko.repository.UserRepository;
import org.inbank.petko.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testShouldReturnUsers() {
        when(userRepository.findAll()).thenReturn(List.of(new UserEntity()));
        assertFalse(userService.findAllUsers().isEmpty());
    }
}
