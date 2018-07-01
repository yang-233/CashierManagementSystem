package com.example.demo.dao;

import com.example.demo.model.WebUser;
import org.apache.ibatis.jdbc.SQL;

public class WebUserSqlProvider {

    public String insertSelective(WebUser record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("web_user");
        
        if (record.getAccount() != null) {
            sql.VALUES("account", "#{account,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getTelephone() != null) {
            sql.VALUES("telephone", "#{telephone,jdbcType=VARCHAR}");
        }
        
        if (record.getAge() != null) {
            sql.VALUES("age", "#{age,jdbcType=INTEGER}");
        }
        
        if (record.getSex() != null) {
            sql.VALUES("sex", "#{sex,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(WebUser record) {
        SQL sql = new SQL();
        sql.UPDATE("web_user");
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getTelephone() != null) {
            sql.SET("telephone = #{telephone,jdbcType=VARCHAR}");
        }
        
        if (record.getAge() != null) {
            sql.SET("age = #{age,jdbcType=INTEGER}");
        }
        
        if (record.getSex() != null) {
            sql.SET("sex = #{sex,jdbcType=INTEGER}");
        }
        
        sql.WHERE("account = #{account,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}