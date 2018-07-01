package com.example.demo.dao;

import com.example.demo.model.WebOrder;
import org.apache.ibatis.jdbc.SQL;

public class WebOrderSqlProvider {

    public String insertSelective(WebOrder record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("web_order");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getProductId() != null) {
            sql.VALUES("product_id", "#{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getCashierAccount() != null) {
            sql.VALUES("cashier_account", "#{cashierAccount,jdbcType=VARCHAR}");
        }
        
        if (record.getNumber() != null) {
            sql.VALUES("number", "#{number,jdbcType=INTEGER}");
        }
        
        if (record.getOrderTime() != null) {
            sql.VALUES("order_time", "#{orderTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(WebOrder record) {
        SQL sql = new SQL();
        sql.UPDATE("web_order");
        
        if (record.getProductId() != null) {
            sql.SET("product_id = #{productId,jdbcType=VARCHAR}");
        }
        
        if (record.getCashierAccount() != null) {
            sql.SET("cashier_account = #{cashierAccount,jdbcType=VARCHAR}");
        }
        
        if (record.getNumber() != null) {
            sql.SET("number = #{number,jdbcType=INTEGER}");
        }
        
        if (record.getOrderTime() != null) {
            sql.SET("order_time = #{orderTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}