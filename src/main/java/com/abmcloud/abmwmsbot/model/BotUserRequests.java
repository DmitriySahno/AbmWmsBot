package com.abmcloud.abmwmsbot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_requests")
@ApiModel(value = "История пользовательских запросов")
public class BotUserRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Идентификатор записи")
    private Long id;
    @Column(name = "user_id")
    @ApiModelProperty(value = "Идентификатор пользователя, который делал запрос")
    private String user_id;
    @Column(name = "request")
    @ApiModelProperty(value = "Содержимое запроса")
    private String request;
    @Column(name = "date")
    @ApiModelProperty(value = "Дата и время запроса")
    private LocalDate date;

}
