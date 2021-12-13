package com.abmcloud.abmwmsbot.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "keys")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "key")
    public String key;
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    public Organization organization;
}
