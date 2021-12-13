package com.abmcloud.abmwmsbot.service;

import com.abmcloud.abmwmsbot.exceptions.UserNotFoundException;
import com.abmcloud.abmwmsbot.entity.User;
import com.abmcloud.abmwmsbot.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private void saveUser(User user) {
        userRepository.save(user);
    }

    private User getUserByName(String userName) throws UserNotFoundException {
        Optional<User> user = userRepository.findUserByName(userName);
        if (user.isPresent())
            return user.get();
        else
            throw new UserNotFoundException(String.format("Can`t found user by name \"%s\"", userName));
    }

}
