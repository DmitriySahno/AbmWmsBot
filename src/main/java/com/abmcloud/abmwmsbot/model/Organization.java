package com.abmcloud.abmwmsbot.model;


import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "organizations")
@ApiModel(value = "Организации")
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @ApiModelProperty(value = "Идентификатор организации")
    private Long id;
    @Column(name = "name")
    @ApiModelProperty(value = "Идентификатор организации")
    private String name;
    @Column(name = "url")
    @ApiModelProperty(value = "Путь для подключения к базе организации")
    private String url;
/*    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Доступные ключи организации")
    private Set<Key> keys = new HashSet<>();*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return id.equals(that.id)
                && name.equals(that.name)
                && url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, url);
    }
}

