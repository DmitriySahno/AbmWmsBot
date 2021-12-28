package com.abmcloud.abmwmsbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reports")
@ApiModel(value = "Отчеты организаций")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "description")
    @ApiModelProperty(value = "Описание отчета")
    private String description;
//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Организация, которой доступен отчет")
    private Organization organization;
}
