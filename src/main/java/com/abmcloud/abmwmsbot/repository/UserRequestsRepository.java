package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.entity.UserRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRequestsRepository extends JpaRepository<UserRequests, Long> {
}
