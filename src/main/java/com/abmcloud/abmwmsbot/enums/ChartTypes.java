package com.abmcloud.abmwmsbot.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ChartTypes {
    AREA("AREA"),
    BAR("BAR"),
    BOX_AND_WHISKER("BOX_AND_WHISKER"),
    GANTT("GANTT"),
    HIGH_AND_LOW("HIGH_AND_LOW"),
    HISTOGRAM("HISTOGRAM"),
    LINE("LINE"),
    PIE("PIE"),
    POLAR("POLAR"),
    RING("RING"),
    SCATTER_PLOT("SCATTER_PLOT"),
    TIME_SERIES("TIME_SERIES"),
    WATERFALL("WATERFALL"),
    WAFER_MAP("WAFER_MAP"),
    NONE("NONE");

    private String name;
    private static final Map<String, ChartTypes> types;

    static {
        types = Arrays.stream(ChartTypes.values()).collect(
                Collectors.toMap(ChartTypes::getName,
                        Function.identity(), (o1, o2) -> o1,
                        ConcurrentHashMap::new));
    }

    ChartTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static ChartTypes getType(String name) {
        return types.get(name);
    }

}