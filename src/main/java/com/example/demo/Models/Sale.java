package com.example.demo.Models;

import java.math.BigDecimal;

public class Sale {
    private BigDecimal id;
    private BigDecimal amountProduct;
    private BigDecimal productId;
    private BigDecimal storeId;
    private String date;

    public Sale(BigDecimal amountProduct, BigDecimal productId, BigDecimal storeId, String date){
        this.id = id;
        this.amountProduct = amountProduct;
        this.productId = productId;
        this.storeId = storeId;
        this.date = date;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getAmountProduct() {
        return amountProduct;
    }

    public void setAmountProduct(BigDecimal amountProduct) {
        this.amountProduct = amountProduct;
    }

    public BigDecimal getProductId() {
        return productId;
    }

    public void setProductId(BigDecimal productId) {
        this.productId = productId;
    }

    public BigDecimal getStoreId() {
        return storeId;
    }

    public void setStoreId(BigDecimal storeId) {
        this.storeId = storeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
