package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.model.BotUser;
import com.abmcloud.abmwmsbot.model.Organization;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    @Order(1)
    public void testUserRead() {
        Optional<BotUser> user1Optional = userRepository.findById(1L);
        assertTrue(user1Optional.isPresent());
        BotUser user1 = user1Optional.get();
        assertEquals("Vasiliy", user1.getName());
        assertEquals("Petrov", user1.getSurname());
        assertEquals("3021541", user1.getTelegramId());
        assertEquals("vasiliy_petrov", user1.getLogin());
        assertEquals("Matlab", user1.getOrganization().getName());
    }

    @Test
    @Order(2)
    public void testUserSave() {
        BotUser newUser = new BotUser("Fedor", "Petryakov", "6324512", "f_petryakoff");

        Optional<Organization> orgById = organizationRepository.findById(1L);
        assertTrue(orgById.isPresent());

        newUser.setOrganization(orgById.get());
        userRepository.save(newUser);

        Optional<BotUser> user5 = userRepository.findById(5L);
        assertTrue(user5.isPresent());
        assertEquals(newUser, user5.get());

    }

    @Test
    @Order(3)
    public void testUserUpdate() {

        Optional<BotUser> user5OldOptional = userRepository.findById(5L);
        assertTrue(user5OldOptional.isPresent());

        BotUser user5 = user5OldOptional.get();
        user5.setName("Elena");
        user5.setSurname("Motyga");
        user5.setLogin("e_mot");
        user5.setTelegramId("526431");

        Optional<Organization> org2Optional = organizationRepository.findById(2L);
        assertTrue(org2Optional.isPresent());
        user5.setOrganization(org2Optional.get());

        userRepository.save(user5);

        Optional<BotUser> user5Optional = userRepository.findById(5L);
        assertTrue(user5Optional.isPresent());

        assertEquals(user5, user5Optional.get());
    }

    @Test
    @Order(4)
    public void testUserDelete() {
        userRepository.deleteById(5L);
        Optional<BotUser> user5 = userRepository.findById(5L);
        assertTrue(user5.isEmpty());
    }

}