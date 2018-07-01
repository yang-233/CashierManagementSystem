package com.example.demo.dao;

import com.example.demo.model.WebAccount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface WebAccountMapper {
    @Delete({
        "delete from web_account",
        "where account = #{account,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String account);

    @Insert({
        "insert into web_account (account, password, ",
        "question, answer, ",
        "type)",
        "values (#{account,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{question,jdbcType=VARCHAR}, #{answer,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=INTEGER})"
    })
    int insert(WebAccount record);

    @InsertProvider(type=WebAccountSqlProvider.class, method="insertSelective")
    int insertSelective(WebAccount record);

    @Select({
        "select",
        "account, password, question, answer, type",
        "from web_account",
        "where account = #{account,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="account", property="account", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="question", property="question", jdbcType=JdbcType.VARCHAR),
        @Result(column="answer", property="answer", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER)
    })
    WebAccount selectByPrimaryKey(String account);

    @UpdateProvider(type=WebAccountSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WebAccount record);

    @Update({
        "update web_account",
        "set password = #{password,jdbcType=VARCHAR},",
          "question = #{question,jdbcType=VARCHAR},",
          "answer = #{answer,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER}",
        "where account = #{account,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(WebAccount record);

    @Select({
            "select",
            "account, password, question, answer, type",
            "from web_account",
            "where type = #{type,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="account", property="account", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="question", property="question", jdbcType=JdbcType.VARCHAR),
            @Result(column="answer", property="answer", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER)
    })
    List<WebAccount> selectByType(int type);

    @Select({
            "select",
            "account",
            "from web_account",
            "where type != 0"
    })
    @Results({
            @Result(column="account", property="account", jdbcType=JdbcType.VARCHAR, id=true)
    })
    List<String> selectAllUserAccount();

    @Select({
            "select",
            "type",
            "from web_account",
            "where account=#{account,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER)
    })
    int selectTypeByAccount(String account);

    @Select({
            "select",
            "account, password, question, answer, type",
            "from web_account",
            "where type=1"
    })
    @Results({
            @Result(column="account", property="account", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="question", property="question", jdbcType=JdbcType.VARCHAR),
            @Result(column="answer", property="answer", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER)
    })
    List<WebAccount> selectAllCashier();
}