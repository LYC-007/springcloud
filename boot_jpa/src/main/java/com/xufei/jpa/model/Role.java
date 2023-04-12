package com.xufei.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_role")
@Data
public class Role  extends BaseModel {

    private String roleName;

    private String roleCode;


}
