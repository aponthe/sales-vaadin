package com.example.demo.Mappers;

import com.example.demo.Models.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

public interface ProductsMapper {

    @Insert("insert into products(name, description, purchase_price, sale_price) values(#{name}, #{description}, #{purchasePrice}, #{salePrice})")
    public void insertProduct(Product product);

    @Select("select name, description, purchase_price, sale_price, id from products")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "purchasePrice", column = "purchase_price"),
            @Result(property = "salePrice", column = "sale_price"),
            @Result(property = "id", column = "id")
    })
    public List<Product> findAll();

    @Select("select name, description, purchase_price, sale_price, id from products where id=#{productId}")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "purchasePrice", column = "purchase_price"),
            @Result(property = "salePrice", column = "sale_price"),
            @Result(property = "id", column = "id")
    })
    public Product getById(BigDecimal productId);

    @Select("select name, description, purchase_price, sale_price, id from products where name=#{name}")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "purchasePrice", column = "purchase_price"),
            @Result(property = "salePrice", column = "sale_price"),
            @Result(property = "id", column = "id")
    })
    public Product getByName(String name);


}
