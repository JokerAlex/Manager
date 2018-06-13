package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class ProduceAddView extends JFrame{
    private JLabel chooseLabel;
    private JComboBox productComboBox;
    private JLabel numLabel;
    private JTextField numTextField;
    private JButton confirmButton;

    public ProduceAddView() {
        init();
    }

    private void init(){

        Dimension dimensionLabel = new Dimension(100,30);
        Dimension dimensionText = new Dimension(150,30);

        chooseLabel = new JLabel("选择产品：");
        chooseLabel.setPreferredSize(dimensionLabel);
        numLabel = new JLabel("下单数量：");
        numLabel.setPreferredSize(dimensionLabel);

        productComboBox = new JComboBox();
        productComboBox.setPreferredSize(dimensionText);
        numTextField = new JTextField();
        numTextField.setPreferredSize(dimensionText);

        confirmButton = new JButton("确定");

        JPanel productPanel = new JPanel();
        productPanel.add(chooseLabel);
        productPanel.add(productComboBox);

        JPanel numPanel = new JPanel();
        numPanel.add(numLabel);
        numPanel.add(numTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);

        this.setLayout(new FlowLayout());
        this.add(productPanel);
        this.add(numPanel);
        this.add(buttonPanel);
        this.setTitle("添加新的产品进度");
        this.setSize(300,200);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    public JComboBox getProductComboBox() {
        return productComboBox;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JTextField getNumTextField() {
        return numTextField;
    }

    public static void main(String[] args) {
        new ProduceAddView();
    }
}
