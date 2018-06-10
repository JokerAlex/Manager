package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class ProductChangeView extends JFrame {
    private JLabel productIdValueLabel;
    private JTextField productNameText;
    private JTextField productPriceText;
    private JTextArea productCommentTextArea;
    private JButton confirmButton;
    private JButton cancelButton;

    public ProductChangeView(){
        init();
    }

    private void init(){

        Dimension dimensionLabel = new Dimension(100,30);
        Dimension dimensionText = new Dimension(150,30);

        JLabel productIdLabel = new JLabel("数据编号：");
        productIdLabel.setPreferredSize(dimensionLabel);
        productIdValueLabel = new JLabel();
        productIdValueLabel.setPreferredSize(dimensionText);

        JLabel productNameLabel = new JLabel("产品名称：");
        productNameLabel.setPreferredSize(dimensionLabel);

        JLabel productPriceLabel = new JLabel("产品价格：");
        productPriceLabel.setPreferredSize(dimensionLabel);

        JLabel productCommentLabel = new JLabel("产品备注：");
        productCommentLabel.setPreferredSize(dimensionLabel);

        productNameText = new JTextField();
        productNameText.setPreferredSize(dimensionText);

        productPriceText = new JTextField();
        productPriceText.setPreferredSize(dimensionText);

        productCommentTextArea = new JTextArea();
        productCommentTextArea.setPreferredSize(new Dimension(150,100));
        productCommentTextArea.setLineWrap(true);

        confirmButton = new JButton("确定");
        cancelButton = new JButton("取消");

        JPanel productIdP = new JPanel();
        productIdP.add(productIdLabel);
        productIdP.add(productIdValueLabel);

        JPanel productNameP = new JPanel();
        productNameP.add(productNameLabel);
        productNameP.add(productNameText);

        JPanel productPriceP = new JPanel();
        productPriceP.add(productPriceLabel);
        productPriceP.add(productPriceText);

        JPanel productCommentP = new JPanel();
        productCommentP.add(productCommentLabel);
        productCommentP.add(productCommentTextArea);

        JPanel productButotnP = new JPanel();
        productButotnP.add(confirmButton);
        productButotnP.add(cancelButton);

        this.setLayout(new FlowLayout());
        this.add(productIdP);
        this.add(productNameP);
        this.add(productPriceP);
        this.add(productCommentP);
        this.add(productButotnP);
        this.setTitle("产品信息修改");
        this.setSize(350,350);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    public JLabel getProductIdValueLabel() {
        return productIdValueLabel;
    }

    public JTextField getProductNameText() {
        return productNameText;
    }

    public JTextField getProductPriceText() {
        return productPriceText;
    }

    public JTextArea getProductCommentTextArea() {
        return productCommentTextArea;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public static void main(String[] args) {
        new ProductChangeView();
    }
}
