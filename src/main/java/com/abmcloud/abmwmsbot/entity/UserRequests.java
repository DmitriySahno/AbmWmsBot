package com.abmcloud.abmwmsbot.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_requests")
public class UserRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "request")
    private String request;
    @Column(name = "date")
    private LocalDate date;

}
