package org.inbank.petko.repository;

import org.inbank.petko.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Jpa Repository class for {@link UserEntity}
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
