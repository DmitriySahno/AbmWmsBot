package com.abmcloud.abmwmsbot.model;


import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "keys")
@ApiModel(value = "Ключи организаций")
@NoArgsConstructor
@AllArgsConstructor
public class Key {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @ApiModelProperty(value = "Идентификатор ключа")
    public Long id;
    @Column(name = "key")
    @ApiModelProperty(value = "Ключ")
    public String key;
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Организация")
    public Organization organization;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key1 = (Key) o;
        return id.equals(key1.id)
                && key.equals(key1.key)
                && organization.getId().equals(key1.getOrganization().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, key);
    }
}
