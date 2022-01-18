package com.abmcloud.abmwmsbot.model;

import com.abmcloud.abmwmsbot.enums.ChartTypes;
import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reports")
@ApiModel(value = "Отчеты организаций")
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @ApiModelProperty(value = "Идентификатор отчета")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(value = "Название отчета в базе клиента без пробелом")
    private String name;
    @Column(name = "uri")
    @ApiModelProperty(value = "Путь к отчету, начиная от пути организации")
    private String uri;
    @Column(name = "alias")
    @ApiModelProperty(value = "Наименование отчета для отображения клиенту")
    private String alias;
    @Column(name = "chart_type")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "Тип графика, который будет построен и отправлен в виде картинки")
    private ChartTypes chartType;
    @Column(name = "description")
    @ApiModelProperty(value = "Описание отчета")
    private String description;
//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Организация, которой доступен отчет")
    private Organization organization;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id.equals(report.id)
                && name.equals(report.name)
                && uri.equals(report.uri)
                && alias.equals(report.alias)
                && chartType == report.chartType
                && description.equals(report.description)
                && organization.getId().equals(report.organization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, uri, alias, chartType, description, organization.getId());
    }
}
