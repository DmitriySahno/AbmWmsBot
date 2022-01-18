package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.model.BotUser;
import com.abmcloud.abmwmsbot.model.BotUserRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserRequestsRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRequestsRepository userRequestsRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    @Order(1)
    public void testUserRequestSave() throws ParseException {
        Optional<BotUser> user4Optional = userRepository.findById(4L);
        assertTrue(user4Optional.isPresent());
        BotUser user4 = user4Optional.get();

        Date date = dateFormat.parse("2022-01-12 13:41:23");
        BotUserRequest newUserRequest = new BotUserRequest(7L, user4, "/getReports", date);
        userRequestsRepository.save(newUserRequest);
        Optional<BotUserRequest> ur7Optional = userRequestsRepository.findById(7L);
        assertTrue(ur7Optional.isPresent());
        assertEquals(newUserRequest, ur7Optional.get());
    }

    @Test
    @Order(2)
    public void testUserRequestRead() throws ParseException {
        Optional<BotUser> user4Optional = userRepository.findById(4L);
        assertTrue(user4Optional.isPresent());
        List<BotUserRequest> user4Requests = userRequestsRepository.findAllByUser(user4Optional.get());
        assertEquals(1, user4Requests.size());
        BotUserRequest user4Request = user4Requests.get(0);

        assertEquals(7L, user4Request.getId());
        assertEquals("/getReports", user4Request.getRequest());
        assertEquals(dateFormat.parse("2022-01-12 13:41:23"), user4Request.getDate());

    }

    @Test
    @Order(3)
    public void testOrganizationUpdate() throws ParseException {
        Optional<BotUserRequest> ur1OptionalOld = userRequestsRepository.findById(1L);
        assertTrue(ur1OptionalOld.isPresent());
        BotUserRequest ur1 = ur1OptionalOld.get();

        Optional<BotUser> user2Optional = userRepository.findById(2L);
        assertTrue(user2Optional.isPresent());
        ur1.setUser(user2Optional.get());
        ur1.setRequest("https://matlab.com/wh_turnover");
        ur1.setDate(dateFormat.parse("2022-01-12 13:41:19"));

        userRequestsRepository.save(ur1);

        Optional<BotUserRequest> ur1Optional = userRequestsRepository.findById(1L);
        assertTrue(ur1Optional.isPresent());
        assertEquals(ur1, ur1Optional.get());
    }

    @Test
    @Order(4)
    public void testOrganizationDelete(){

        userRequestsRepository.deleteById(7L);

        Optional<BotUserRequest> ur7Optional = userRequestsRepository.findById(7L);
        assertTrue(ur7Optional.isEmpty());
    }


}