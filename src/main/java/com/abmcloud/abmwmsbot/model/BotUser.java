package com.abmcloud.abmwmsbot.model;

import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "users")
@ApiModel(value = "Пользователи бота")
@RequiredArgsConstructor
@NoArgsConstructor
public class BotUser {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @ApiModelProperty(value = "Идентификатор пользователя")
    private Long id;
    @NonNull
    @Column(name = "name")
    @ApiModelProperty(value = "Имя пользователя")
    private String name;
    @NonNull
    @Column(name = "surname")
    @ApiModelProperty(value = "Фамилия пользователя")
    private String surname;
    @NonNull
    @Column(name = "telegram_id")
    @ApiModelProperty(value = "Идентификатор пользователя в telegram")
    private String telegramId;
    @NonNull
    @Column(name = "login")
    @ApiModelProperty(value = "Логин пользователя в telegram")
    private String login;
    @ManyToOne(targetEntity = Organization.class)
//    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Организация пользователя")
    private Organization organization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotUser botUser = (BotUser) o;
        return id.equals(botUser.id)
                && name.equals(botUser.name)
                && surname.equals(botUser.surname)
                && telegramId.equals(botUser.telegramId)
                && login.equals(botUser.login)
                && organization.getId().equals(botUser.organization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, surname, telegramId, login, organization);
    }
}
