package com.example.demo.dao;

import com.example.demo.model.WebUser;
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

public interface WebUserMapper {
    @Delete({
        "delete from web_user",
        "where account = #{account,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String account);

    @Insert({
        "insert into web_user (account, name, ",
        "telephone, age, ",
        "sex)",
        "values (#{account,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{telephone,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER}, ",
        "#{sex,jdbcType=INTEGER})"
    })
    int insert(WebUser record);

    @InsertProvider(type=WebUserSqlProvider.class, method="insertSelective")
    int insertSelective(WebUser record);

    @Select({
        "select",
        "account, name, telephone, age, sex",
        "from web_user",
        "where account = #{account,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="account", property="account", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="telephone", property="telephone", jdbcType=JdbcType.VARCHAR),
        @Result(column="age", property="age", jdbcType=JdbcType.INTEGER),
        @Result(column="sex", property="sex", jdbcType=JdbcType.INTEGER)
    })
    WebUser selectByPrimaryKey(String account);

    @UpdateProvider(type=WebUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WebUser record);

    @Update({
        "update web_user",
        "set name = #{name,jdbcType=VARCHAR},",
          "telephone = #{telephone,jdbcType=VARCHAR},",
          "age = #{age,jdbcType=INTEGER},",
          "sex = #{sex,jdbcType=INTEGER}",
        "where account = #{account,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(WebUser record);

    @Select({
            "select",
            "account, name, telephone, age, sex",
            "from web_user"
    })
    @Results({
            @Result(column="account", property="account", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="telephone", property="telephone", jdbcType=JdbcType.VARCHAR),
            @Result(column="age", property="age", jdbcType=JdbcType.INTEGER),
            @Result(column="sex", property="sex", jdbcType=JdbcType.INTEGER)
    })
    List<WebUser> selectAll();
}