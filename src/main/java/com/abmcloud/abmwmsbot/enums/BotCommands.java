package com.abmcloud.abmwmsbot.enums;

import org.apache.catalina.mapper.Mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum BotCommands {
    START("/start"),
    REPORTS("/reports"),
    INFO("/info"),
    SETTINGS("/settings");

    private final String command;
    private static final Map<String, BotCommands> commands;

    static {
        commands = Arrays.stream(BotCommands.values()).collect(
                Collectors.toMap(BotCommands::getCommand,
                        Function.identity(), (o1, o2) -> o1,
                        ConcurrentHashMap::new));
    }

    BotCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }

    public static BotCommands getFromString(String command){
        return commands.get(command);
    }

}
