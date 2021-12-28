package com.abmcloud.abmwmsbot.repository;

import com.abmcloud.abmwmsbot.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<BotUser, Long> {

    Optional<BotUser> findUserByName(String name);
    Optional<BotUser> findUserByTelegramId(String telegramId);
}
