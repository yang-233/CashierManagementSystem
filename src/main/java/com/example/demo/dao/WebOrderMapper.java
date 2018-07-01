package com.example.demo.dao;

import com.example.demo.model.WebOrder;
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

public interface WebOrderMapper {
    @Delete({
        "delete from web_order",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into web_order (id, product_id, ",
        "cashier_account, number, ",
        "order_time)",
        "values (#{id,jdbcType=INTEGER}, #{productId,jdbcType=VARCHAR}, ",
        "#{cashierAccount,jdbcType=VARCHAR}, #{number,jdbcType=INTEGER}, ",
        "#{orderTime,jdbcType=TIMESTAMP})"
    })
    int insert(WebOrder record);

    @InsertProvider(type=WebOrderSqlProvider.class, method="insertSelective")
    int insertSelective(WebOrder record);

    @Select({
        "select",
        "id, product_id, cashier_account, number, order_time",
        "from web_order",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="product_id", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="cashier_account", property="cashierAccount", jdbcType=JdbcType.VARCHAR),
        @Result(column="number", property="number", jdbcType=JdbcType.INTEGER),
        @Result(column="order_time", property="orderTime", jdbcType=JdbcType.TIMESTAMP)
    })
    WebOrder selectByPrimaryKey(Integer id);

    @UpdateProvider(type=WebOrderSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WebOrder record);

    @Update({
        "update web_order",
        "set product_id = #{productId,jdbcType=VARCHAR},",
          "cashier_account = #{cashierAccount,jdbcType=VARCHAR},",
          "number = #{number,jdbcType=INTEGER},",
          "order_time = #{orderTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(WebOrder record);


    @Select({
            "select",
            "id, product_id, cashier_account, number, order_time",
            "from web_order"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="product_id", property="productId", jdbcType=JdbcType.VARCHAR),
            @Result(column="cashier_account", property="cashierAccount", jdbcType=JdbcType.VARCHAR),
            @Result(column="number", property="number", jdbcType=JdbcType.INTEGER),
            @Result(column="order_time", property="orderTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<WebOrder> selectAll();

    @Insert({
            "insert into web_order (product_id, ",
            "cashier_account, number)",
            "values (#{productId,jdbcType=VARCHAR}, ",
            "#{cashierAccount,jdbcType=VARCHAR}, #{number,jdbcType=INTEGER}) "
    })
    int insertOrder(WebOrder record);
}