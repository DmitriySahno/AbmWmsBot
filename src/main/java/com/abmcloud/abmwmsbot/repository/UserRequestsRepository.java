package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.model.BotUserRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestsRepository extends JpaRepository<BotUserRequests, Long> {
}
