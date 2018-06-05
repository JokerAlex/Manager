package com.manager.dao;

import com.manager.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    List<Product> getAll();

    Product getOne(@Param("productId") int productId, @Param("productName") String productName);

    int insertOne(Product product);

    int insertBatch(@Param("list") List<Product> list);

    int updateProduct(Product product);
}
