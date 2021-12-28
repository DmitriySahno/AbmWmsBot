package com.abmcloud.abmwmsbot.service;

import com.abmcloud.abmwmsbot.exceptions.UserNotFoundException;
import com.abmcloud.abmwmsbot.model.BotUser;
import com.abmcloud.abmwmsbot.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(BotUser user) {
        userRepository.save(user);
    }

    private BotUser getUserByName(String userName) throws UserNotFoundException {
        Optional<BotUser> user = userRepository.findUserByName(userName);
        if (user.isPresent())
            return user.get();
        else
            throw new UserNotFoundException(String.format("Can`t found user by name \"%s\"", userName));
    }

    public Optional<BotUser> getUserByTelegramId(String id) {
        return userRepository.findUserByTelegramId(id);
    }
}
