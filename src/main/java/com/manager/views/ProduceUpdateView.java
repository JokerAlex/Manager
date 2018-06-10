package com.manager.views;


import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class ProduceUpdateView extends JFrame {
    private JLabel productIdLabel;
    private JLabel productIdValueLabel;
    private JLabel productNameLabel;
    private JLabel productNameValueLabel;
    private JLabel chooseLabel;
    private JComboBox chooseComboBox;
    private JLabel numLabel;
    private JTextField numTextField;
    private JLabel commentLabel;
    private JTextArea commentTextArea;
    private JButton confirmButton;
    private JButton cancelButotn;

    public ProduceUpdateView(){
        init();
    }

    private void init(){


        Dimension dimensionLabel = new Dimension(100,30);
        Dimension dimensionText = new Dimension(150,30);

        productIdLabel = new JLabel("数据编号：");
        productIdLabel.setPreferredSize(dimensionLabel);
        productIdValueLabel = new JLabel();
        productIdValueLabel.setPreferredSize(dimensionText);
        productNameLabel = new JLabel("产品名称：");
        productNameLabel.setPreferredSize(dimensionLabel);
        productNameValueLabel = new JLabel();
        productNameValueLabel.setPreferredSize(dimensionText);

        chooseLabel = new JLabel("选择修改项：");
        chooseLabel.setPreferredSize(dimensionLabel);

        chooseComboBox = new JComboBox();
        chooseComboBox.setPreferredSize(dimensionText);

        numLabel = new JLabel("  新增数量：");
        numLabel.setPreferredSize(dimensionLabel);

        numTextField = new JTextField();
        numTextField.setPreferredSize(dimensionText);

        commentLabel = new JLabel("  备    注:");
        commentLabel.setPreferredSize(dimensionLabel);

        commentTextArea = new JTextArea();
        commentTextArea.setPreferredSize(new Dimension(150,100));
        commentTextArea.setLineWrap(true);
        confirmButton = new JButton("确定");
        cancelButotn = new JButton("取消");

        JPanel productIdPanel = new JPanel();
        productIdPanel.add(productIdLabel);
        productIdPanel.add(productIdValueLabel);

        JPanel productNamePanel = new JPanel();
        productNamePanel.add(productNameLabel);
        productNamePanel.add(productNameValueLabel);

        JPanel choosePanel = new JPanel();
        choosePanel.add(chooseLabel);
        choosePanel.add(chooseComboBox);

        JPanel numPanel = new JPanel();
        numPanel.add(numLabel);
        numPanel.add(numTextField);

        JPanel commentPanel = new JPanel();
        commentPanel.add(commentLabel);
        commentPanel.add(commentTextArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButotn);

        this.setLayout(new FlowLayout());
        this.add(productIdPanel);
        this.add(productNamePanel);
        this.add(choosePanel);
        this.add(numPanel);
        this.add(commentPanel);
        this.add(buttonPanel);
        this.setTitle("进度更新");
        this.setSize(300,400);
        this.setLocationRelativeTo(null);
        this.setVisible(false);

    }

    public static void main(String[] args) {
        new ProduceUpdateView();
    }

    public JComboBox getChooseComboBox() {
        return chooseComboBox;
    }

    public JTextField getNumTextField() {
        return numTextField;
    }

    public JTextArea getCommentTextArea() {
        return commentTextArea;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getCancelButotn() {
        return cancelButotn;
    }

    public JLabel getProductIdValueLabel() {
        return productIdValueLabel;
    }

    public JLabel getProductNameValueLabel() {
        return productNameValueLabel;
    }
}
