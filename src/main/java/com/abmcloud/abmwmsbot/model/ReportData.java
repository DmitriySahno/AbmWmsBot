package com.abmcloud.abmwmsbot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Содержимое отчета")
public class ReportData {
    @ApiModelProperty(value = "Строки отчета")
    private List<ReportRow> data;
}
