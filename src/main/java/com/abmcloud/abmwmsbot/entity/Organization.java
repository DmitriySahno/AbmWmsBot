package com.abmcloud.abmwmsbot.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "connection")
    private String connection;
    @OneToMany(mappedBy = "organization")
    private Set<Key> keys;
}

