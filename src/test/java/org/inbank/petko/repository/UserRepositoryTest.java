package org.inbank.petko.repository;

import org.inbank.petko.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test of the DB connection and consistency
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUpBeforeAll() {
        testEntityManager.persistAndFlush(UserEntity.builder().name("Test1").surname("SurTest1").segmentId(1).build());
        testEntityManager.persistAndFlush(UserEntity.builder().name("Test2").surname("SurTest2").segmentId(2).build());
    }

    @Test
    void dbConnectionTest() throws SQLException {
        List<UserEntity> users = userRepository.findAll();
        assertEquals(6, users.size());
    }

}
