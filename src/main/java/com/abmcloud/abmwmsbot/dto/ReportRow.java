package com.abmcloud.abmwmsbot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@ApiModel(value = "Строка отчета")
public class ReportRow{
    @ApiModelProperty(value = "Параметр")
    private final String param;
    @ApiModelProperty(value = "Значение параметра")
    private final String value;
}
