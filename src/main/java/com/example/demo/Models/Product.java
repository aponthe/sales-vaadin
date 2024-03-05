package com.example.demo.Models;

import java.math.BigDecimal;

public class Product {
    private BigDecimal id;
    private String name;
    private String description;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Product(String name, String description, BigDecimal purchasePrice, BigDecimal salePrice){
        this.name=name;
        this.description=description;
        this.purchasePrice=purchasePrice;
        this.salePrice=salePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String toString(){
        return this.id+" "+this.name;
    }
}
