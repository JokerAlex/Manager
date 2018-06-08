package com.manager.controller;


import com.manager.pojo.*;
import com.manager.service.ProductService;
import com.manager.views.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class Maincontroller {
    //视图
    private MainView mainView;

    //service
    private ProductService productService;

    //表头
    String[] productTitles = {"名称","价格","产品备注"};
    String[] producetitles = {"名称","下单","备注","木工","备注","油房","备注","包装","备注",
            "特定","备注","北京","备注","北京特定","备注","本地合同","备注","外地合同","备注","等待","备注"};
    String[] outputTitles = {"名称","下单","木工","油房","包装", "特定","北京","北京特定","本地合同","外地合同","等待"};

    @Autowired
    public Maincontroller(MainView mainView,ProductService productService){
        this.mainView = mainView;
        this.productService = productService;
    }


    //界面初始化
    public void initview(){
        //product数据填充
        //mainView.getProduct().getjTable().add

        mainView.init();
    }

    //按钮初始化
    public void inintController(){

    }

    /**
     * 关于product的操作
     */
    public void loadProduct(){
        List<Product> productList = productService.getAllProduct();

    }
}
