package com.example.demo.Models;

import java.math.BigDecimal;

public class Report {
    private Product product;
    private Store store;
    private BigDecimal amountProduct;
    private BigDecimal subtotal;
    private BigDecimal totalIVA;
    private BigDecimal total;
    private BigDecimal productId;
    private BigDecimal storeId;


    public Product getProduct() {
        return product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getAmountProduct() {
        return amountProduct;
    }

    public void setAmountProduct(BigDecimal amountProduct) {
        this.amountProduct = amountProduct;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotalIVA() {
        return totalIVA;
    }

    public void setTotalIVA(BigDecimal totalIVA) {
        this.totalIVA = totalIVA;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
}
