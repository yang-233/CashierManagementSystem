package com.example.demo.model;

import java.util.Date;

public class WebOrder {
    private Integer id;

    private String productId;

    private String cashierAccount;

    private Integer number;

    private Date orderTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getCashierAccount() {
        return cashierAccount;
    }

    public WebOrder() {

    }

    public WebOrder(Integer id, String productId, String cashierAccount, Integer number, Date orderTime) {
        this.id = id;
        this.productId = productId;
        this.cashierAccount = cashierAccount;
        this.number = number;
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "WebOrder{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", cashierAccount='" + cashierAccount + '\'' +
                ", number=" + number +
                ", orderTime=" + orderTime +
                '}';
    }

    public void setCashierAccount(String cashierAccount) {
        this.cashierAccount = cashierAccount == null ? null : cashierAccount.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}