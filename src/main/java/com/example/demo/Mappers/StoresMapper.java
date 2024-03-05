package com.example.demo.Mappers;

import com.example.demo.Models.Store;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface StoresMapper {

    @Insert("insert into stores(name) values(#{name})")
    public void insertStore(Store store);

    @Select("select * from stores;")
    public List<Store> findAll();

    @Select("select name, id from stores where name=#{name}")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "id", column = "id")
    })
    public Store getByName(String name);
}
