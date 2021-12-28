package com.abmcloud.abmwmsbot.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "organizations")
@ApiModel(value = "Организации")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Идентификатор организации")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(value = "Идентификатор организации")
    private String name;
    @Column(name = "url")
    @ApiModelProperty(value = "Путь для подключения к базе организации")
    private String url;
    @OneToMany(mappedBy = "organization")
    @ApiModelProperty(value = "Доступные ключи организации")
    private Set<Key> keys;
//    @OneToMany(mappedBy = "organization")
//    private Set<BotUser> users;
//    @OneToMany(mappedBy = "organization")
//    private Set<Report> reports;
}

