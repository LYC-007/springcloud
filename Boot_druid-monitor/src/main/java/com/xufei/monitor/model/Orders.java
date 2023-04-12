package com.xufei.monitor.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@Data
public class Orders {
    @Id
    private Integer id;
    private String orderName;
}
