package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByName(String name);

}
