package com.example.demo.dao;

import com.example.demo.model.WebAccount;
import org.apache.ibatis.jdbc.SQL;

public class WebAccountSqlProvider {

    public String insertSelective(WebAccount record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("web_account");
        
        if (record.getAccount() != null) {
            sql.VALUES("account", "#{account,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getQuestion() != null) {
            sql.VALUES("question", "#{question,jdbcType=VARCHAR}");
        }
        
        if (record.getAnswer() != null) {
            sql.VALUES("answer", "#{answer,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(WebAccount record) {
        SQL sql = new SQL();
        sql.UPDATE("web_account");
        
        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getQuestion() != null) {
            sql.SET("question = #{question,jdbcType=VARCHAR}");
        }
        
        if (record.getAnswer() != null) {
            sql.SET("answer = #{answer,jdbcType=VARCHAR}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=INTEGER}");
        }
        
        sql.WHERE("account = #{account,jdbcType=VARCHAR}");
        
        return sql.toString();
    }
}