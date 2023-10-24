package org.inbank.petko.service;

import org.inbank.petko.entity.UserEntity;

import java.util.List;

/**
 * Service to perform {@link UserEntity} business logic
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
public interface UserService {

    /**
     * Loads all Users from DB
     * @return the list of all Users in DB
     */
    List<UserEntity> findAllUsers();
}
