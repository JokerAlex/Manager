package com.manager.service;


import com.manager.dao.ProductMapper;
import com.manager.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductMapper productMapper;

    @Autowired

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     * 获取所有的产品
     * @return List<Product>
     */
    public List<Product> getAllProduct(){
        return productMapper.getAll();
    }

    public List<Product> searchProduct(String name){
        if (name != null){
            return productMapper.getOne(name);
        }
        return null;
    }

    /**
     * 新增一件产品
     * @param product
     * @return boolean
     */
    public boolean insertOneProduct(Product product){
        if (product != null){
            if (productMapper.insertOne(product) == 1){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 批量添加产品
     * @param productList
     * @return
     */
    public boolean insertBatchProduct(List<Product> productList){
        if (productList == null || productList.size() == 0){
            return false;
        }
        int successNum = productMapper.insertBatch(productList);
        if (successNum == productList.size()){
            return true;
        }
        return false;
    }

    /**
     * 更新产品
     * @param product
     * @return
     */
    public boolean updateProduct(Product product){
        if (product == null){
            return false;
        }
        if (productMapper.updateProduct(product) == 1){
            return true;
        }
        return false;
    }

    /**
     * 删除产品
     * @param productId
     * @return
     */
    public boolean delProduct(int productId){
        if (productId == 0){
            return false;
        }
        if (productMapper.delProduct(productId) == 1){
            return true;
        }
        return false;
    }
}
