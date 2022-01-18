package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.model.BotUser;
import com.abmcloud.abmwmsbot.model.Key;
import com.abmcloud.abmwmsbot.model.Organization;
import org.hibernate.Hibernate;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private KeyRepository keyRepository;

    @Test
    @Order(1)
    public void testOrganizationRead(){
        Optional<Organization> org1Optional = organizationRepository.findById(1L);
        assertTrue(org1Optional.isPresent());

        List<Key> keys = new ArrayList<>();
        Collections.addAll(keys, new Key(1L, "_g7DghYp", org1Optional.get()));

        Organization org1 = org1Optional.get();
        assertEquals("Matlab", org1.getName());
        assertEquals("https://matlab.com", org1.getUrl());
        Hibernate.initialize(Key.class);
        assertEquals(keys, keyRepository.findAllByOrganization(org1));
    }

    @Test
    @Order(2)
    public void testOrganizationSave(){
        Organization newOrganization = new Organization(3L, "N-Logistick", "https://n-logistick");

        organizationRepository.save(newOrganization);

        Optional<Organization> org3 = organizationRepository.findById(3L);
        assertTrue(org3.isPresent());
        assertEquals(newOrganization, org3.get());
    }

    @Test
    @Order(3)
    public void testOrganizationUpdate(){

        Optional<Organization> org3Optional = organizationRepository.findById(3L);
        assertTrue(org3Optional.isPresent());

        Organization org3 = org3Optional.get();
        org3.setName("T-Logi");
        org3.setName("https://t-logi");
        organizationRepository.save(org3);

        Optional<Organization> newOrg3Optional = organizationRepository.findById(3L);
        assertTrue(newOrg3Optional.isPresent());
        assertEquals(org3, newOrg3Optional.get());
    }

    @Test
    @Order(4)
    public void testOrganizationDelete(){

        organizationRepository.deleteById(3L);

        Optional<Organization> org3Optional = organizationRepository.findById(3L);
        assertTrue(org3Optional.isEmpty());

    }




}