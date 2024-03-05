package com.example.demo.Mappers;

import com.example.demo.Models.Report;
import com.example.demo.Models.Sale;
import com.example.demo.Models.Store;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Date;

public interface SalesMapper {

    @Insert("insert into sales(amount_product, store_id, product_id, date) values(#{amountProduct}, #{storeId}, #{productId}, #{date})")
    public void insertSale(Sale sale);

    @Select("select id, amount_product, store_id, product_id, date from sales")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "amountProduct", column = "amount_product"),
            @Result(property = "storeId", column = "store_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "date", column = "date")
    })
    public List<Sale> findAll();


    @Select("select sum(amount_product) as total_amount, store_id, product_id from sales group by store_id, product_id")
    @Results({
            @Result(property = "amountProduct", column = "total_amount"),
            @Result(property = "storeId", column = "store_id"),
            @Result(property = "productId", column = "product_id")
    })
    public List<Report> reportAll();

    @Select("select sum(amount_product) as total_amount, store_id, product_id from sales where store_id=#{store.id} and date::date>=#{firstDate}::date and date::date<=#{lastDate}::date group by store_id, product_id")
    @Results({
            @Result(property = "amountProduct", column = "total_amount"),
            @Result(property = "storeId", column = "store_id"),
            @Result(property = "productId", column = "product_id")
    })
    public List<Report> getByFilter(String firstDate, String lastDate, Store store);
}
