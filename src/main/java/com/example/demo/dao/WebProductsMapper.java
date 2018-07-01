package com.example.demo.dao;

import com.example.demo.model.WebProducts;
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

public interface WebProductsMapper {
    @Delete({
        "delete from web_products",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    @Insert({
        "insert into web_products (id, name, ",
        "price, number)",
        "values (#{id,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=DOUBLE}, #{number,jdbcType=INTEGER})"
    })
    int insert(WebProducts record);

    @InsertProvider(type=WebProductsSqlProvider.class, method="insertSelective")
    int insertSelective(WebProducts record);

    @Select({
        "select",
        "id, name, price, number",
        "from web_products",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="number", property="number", jdbcType=JdbcType.INTEGER)
    })
    WebProducts selectByPrimaryKey(String id);

    @UpdateProvider(type=WebProductsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WebProducts record);

    @Update({
        "update web_products",
        "set name = #{name,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DOUBLE},",
          "number = #{number,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(WebProducts record);

    @Select({
            "select",
            "id, name, price, number",
            "from web_products"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
            @Result(column="number", property="number", jdbcType=JdbcType.INTEGER)
    })
    List<WebProducts> selectAll();

    @Select({
            "select",
            "id, name, price, number",
            "from web_products",
            "where name=#{name,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
            @Result(column="number", property="number", jdbcType=JdbcType.INTEGER)
    })
    List<WebProducts> selectByName(String name);

    @Select({
            "select count(*) from web.web_products"
    })
    int getRows();
}