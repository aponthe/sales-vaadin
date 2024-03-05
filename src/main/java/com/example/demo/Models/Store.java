package com.example.demo.Models;

import java.math.BigDecimal;

public class Store {
    private BigDecimal id;
    private String name;

    public Store(){
    }

    public Store(String name){
        this.name=name;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return this.id+" "+this.name;
    }
}
