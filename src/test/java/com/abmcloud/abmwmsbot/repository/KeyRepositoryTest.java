package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.model.Key;
import com.abmcloud.abmwmsbot.model.Organization;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class KeyRepositoryTest {

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    @Order(1)
    public void testKeySave(){

        Organization newOrg3 = new Organization(3L, "T-Logistick", "https://t-logi");
        organizationRepository.save(newOrg3);
        Key newKey = new Key(4L, "RfAs0_Fg", newOrg3);
        keyRepository.save(newKey);

        Optional<Key> key4Optional = keyRepository.findById(4L);
        assertTrue(key4Optional.isPresent());

        assertEquals(newKey, key4Optional.get());
    }

    @Test
    @Order(2)
    public void testKeyRead(){
        Optional<Organization> org3Optional = organizationRepository.findById(3L);
        assertTrue(org3Optional.isPresent());

        Organization org3 = org3Optional.get();
        assertEquals("T-Logistick", org3.getName());
        assertEquals("https://t-logi", org3.getUrl());

        List<Key> keys = keyRepository.findAllByOrganization(org3);
        assertEquals(1, keys.size());
        Key key4 = keys.get(0);
        assertEquals(4L, key4.getId());
        assertEquals("RfAs0_Fg", key4.getKey());
        assertEquals(org3, key4.getOrganization());
    }

    @Test
    @Order(3)
    public void testKeyUpdate(){
        Optional<Key> key4OptionalOld = keyRepository.findById(4L);
        assertTrue(key4OptionalOld.isPresent());

        Key key4 = key4OptionalOld.get();
        key4.setKey("gDsE_gbk");
        Optional<Organization> org3Optional = organizationRepository.findById(3L);
        assertTrue(org3Optional.isPresent());
        key4.setOrganization(org3Optional.get());
        keyRepository.save(key4);

        Optional<Key> key4Optional = keyRepository.findById(4L);
        assertTrue(key4Optional.isPresent());
        assertEquals(key4, key4Optional.get());
    }

    @Test
    @Order(4)
    public void testKeyDelete(){

        keyRepository.deleteById(4L);

        Optional<Key> key4Optional = keyRepository.findById(4L);
        assertTrue(key4Optional.isEmpty());
    }


}