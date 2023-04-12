package com.xufei.domain;

import lombok.Data;

@Data
public class Computer {
    private String name;

    public Computer(String str) {
        this.name=str;
    }
}
