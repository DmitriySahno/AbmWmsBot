package com.abmcloud.abmwmsbot.dto;

import com.abmcloud.abmwmsbot.enums.ChartTypes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "Данные отчета")
public class ReportData {

    @ApiModelProperty(value = "Название отчета")
    private String name;
    @ApiModelProperty(value = "Наименование подписи легенды")
    private String legendName;
    @ApiModelProperty(value = "Наименование подписи значений")
    private String valuesName;
    @ApiModelProperty(value = "Строки отчета")
    private List<ReportRow> data;
}
