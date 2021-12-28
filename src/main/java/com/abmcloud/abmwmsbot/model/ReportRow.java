package com.abmcloud.abmwmsbot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@ApiModel(value = "Строка отчета")
public class ReportRow{
    @ApiModelProperty(value = "Показатель")
    private final String param;
    @ApiModelProperty(value = "Значение")
    private final String value;
}
