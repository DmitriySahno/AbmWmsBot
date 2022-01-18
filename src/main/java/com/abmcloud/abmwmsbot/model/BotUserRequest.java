package com.abmcloud.abmwmsbot.model;

import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "user_requests")
@ApiModel(value = "История пользовательских запросов")
@NoArgsConstructor
@AllArgsConstructor
public class BotUserRequest {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @ApiModelProperty(value = "Идентификатор записи")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Пользователь, который делал запрос")
    private BotUser user;
    @Column(name = "request")
    @ApiModelProperty(value = "Содержимое запроса")
    private String request;
    @Column(name = "date")
    @ApiModelProperty(value = "Дата и время запроса")
    private Date date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotUserRequest that = (BotUserRequest) o;
        return id.equals(that.id)
                && user.equals(that.user)
                && request.equals(that.request)
                && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, user, request, date);
    }
}
