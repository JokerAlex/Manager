package com.manager.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class MainView extends JFrame {
    private ProductPanel product;
    private ProducePanel produce;
    private OutputPanel output;


    @Autowired
    public MainView(ProductPanel product,ProducePanel produce,OutputPanel output){
        this.product = product;
        this.produce = produce;
        this.output = output;
    }

    public void init(){

        product.init();

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("产品",product);
        jTabbedPane.add("进度",produce);
        jTabbedPane.add("产值",output);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(jTabbedPane);
        this.setSize(700,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public ProductPanel getProduct() {
        return product;
    }

    public ProducePanel getProduce() {
        return produce;
    }

    public OutputPanel getOutput() {
        return output;
    }

    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.init();
    }
}
