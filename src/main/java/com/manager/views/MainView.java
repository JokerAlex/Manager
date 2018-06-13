package com.manager.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class MainView extends JFrame {

    //界面以及弹窗
    private ProductPanel product;
    private ProductChangeView productChangeView;
    private ProductAddView productAddView;


    private ProducePanel produce;
    private ProduceImportView produceImportView;
    private ProduceAddView produceAddView;
    private ProduceUpdateView produceUpdateView;
    private ProduceChangeView produceChangeView;


    private OutputPanel output;
    private OutputChangeView outputChangeView;


    @Autowired
    public MainView(ProductPanel product,ProducePanel produce,OutputPanel output){
        this.product = product;
        this.produce = produce;
        this.output = output;
    }
    @Autowired
    public void setProductChangeView(ProductChangeView productChangeView) {
        this.productChangeView = productChangeView;
    }
    @Autowired
    public void setProductAddView(ProductAddView productAddView) {
        this.productAddView = productAddView;
    }
    @Autowired
    public void setProduceAddView(ProduceAddView produceAddView) {
        this.produceAddView = produceAddView;
    }
    @Autowired
    public void setProduceUpdateView(ProduceUpdateView produceUpdateView) {
        this.produceUpdateView = produceUpdateView;
    }
    @Autowired
    public void setProduceChangeView(ProduceChangeView produceChangeView) {
        this.produceChangeView = produceChangeView;
    }
    @Autowired
    public void setProduceImportView(ProduceImportView produceImportView) {
        this.produceImportView = produceImportView;
    }
    @Autowired
    public void setOutputChangeView(OutputChangeView outputChangeView) {
        this.outputChangeView = outputChangeView;
    }

    public void init(){

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("产品",product);
        jTabbedPane.add("进度",produce);
        jTabbedPane.add("产值",output);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(jTabbedPane);
        this.setSize(700,500);
        this.setTitle("信息管理");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public ProductPanel getProduct() {
        return product;
    }

    public ProductChangeView getProductChangeView() {
        return productChangeView;
    }

    public ProductAddView getProductAddView() {
        return productAddView;
    }

    public ProducePanel getProduce() {
        return produce;
    }

    public ProduceImportView getProduceImportView() {
        return produceImportView;
    }

    public ProduceAddView getProduceAddView() {
        return produceAddView;
    }

    public ProduceUpdateView getProduceUpdateView() {
        return produceUpdateView;
    }

    public ProduceChangeView getProduceChangeView() {
        return produceChangeView;
    }

    public OutputPanel getOutput() {
        return output;
    }

    public OutputChangeView getOutputChangeView() {
        return outputChangeView;
    }
}
