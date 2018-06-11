package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class ProduceAddView extends JFrame{
    private JLabel chooseLabel;
    private JComboBox productComboBox;
    private JButton confirmButton;

    public ProduceAddView() {
        init();
    }

    private void init(){


        chooseLabel = new JLabel("选择要添加的产品：");

        productComboBox = new JComboBox();

        confirmButton = new JButton("确定");

        JPanel labelPanel = new JPanel();
        labelPanel.add(chooseLabel);

        JPanel productPanel = new JPanel();
        productPanel.add(productComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);

        this.setLayout(new BorderLayout());
        this.add(labelPanel,BorderLayout.NORTH);
        this.add(productPanel,BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.setTitle("添加新的产品进度");
        this.setSize(300,150);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(false);
    }

    public JComboBox getProductComboBox() {
        return productComboBox;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public static void main(String[] args) {
        new ProduceAddView();
    }
}
