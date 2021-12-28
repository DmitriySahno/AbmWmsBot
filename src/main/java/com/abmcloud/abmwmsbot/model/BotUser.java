package com.abmcloud.abmwmsbot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "users")
@ApiModel(value = "Пользователи бота")
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Идентификатор пользователя")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(value = "Имя пользователя")
    private String name;
    @Column(name = "surname")
    @ApiModelProperty(value = "Фамилия пользователя")
    private String surname;
    @Column(name = "telegram_id")
    @ApiModelProperty(value = "Идентификатор пользователя в telegram")
    private String telegramId;
    @Column(name = "login")
    @ApiModelProperty(value = "Логин пользователя в telegram")
    private String login;
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Организация пользователя")
    private Organization organization;
}
