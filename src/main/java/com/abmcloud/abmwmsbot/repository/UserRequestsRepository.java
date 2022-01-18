package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.model.BotUser;
import com.abmcloud.abmwmsbot.model.BotUserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRequestsRepository extends JpaRepository<BotUserRequest, Long> {

    List<BotUserRequest> findAllByUser(BotUser user);

}
