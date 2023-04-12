package com.xufei.jpa.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "menu")
@NamedEntityGraph(name = "menu.findAll", attributeNodes = {@NamedAttributeNode(value = "childList")})
public class Menu {
    @Id
    private Integer id;
    private String menuName;
    private Integer parentId;
    @OneToMany
    @JoinColumn(name="parentId")
    private List<Menu> childList;

}
