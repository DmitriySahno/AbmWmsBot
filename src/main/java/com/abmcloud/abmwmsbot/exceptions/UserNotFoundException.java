package com.abmcloud.abmwmsbot.exceptions;

import javassist.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
