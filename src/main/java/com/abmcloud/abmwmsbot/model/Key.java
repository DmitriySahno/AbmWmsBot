package com.abmcloud.abmwmsbot.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "keys")
@ApiModel(value = "Ключи организаций")
public class Key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Идентификатор ключа")
    public Long id;
    @Column(name = "key")
    @ApiModelProperty(value = "Ключ")
    public String key;
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Организация")
    public Organization organization;
}
