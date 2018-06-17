package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class ProduceChangeView extends JFrame {
    private JLabel productIdValueLabel;
    private JTextField productNameTextField;
    private JComboBox chooseComboBox;
    private JTextField numTextField;
    private JTextArea commentTextArea;
    private JButton confirmButton;
    private JButton cancelButton;

    public ProduceChangeView(){
        init();
    }

    private void init() {


        Dimension dimensionLabel = new Dimension(100, 30);
        Dimension dimensionText = new Dimension(150, 30);

        JLabel productIdLabel = new JLabel("数据编号：");
        productIdLabel.setPreferredSize(dimensionLabel);
        productIdValueLabel = new JLabel();
        productIdValueLabel.setPreferredSize(dimensionText);
        JLabel productNameLabel = new JLabel("产品名称：");
        productNameLabel.setPreferredSize(dimensionLabel);
        productNameTextField = new JTextField();
        productNameTextField.setPreferredSize(dimensionText);

        JLabel chooseLabel = new JLabel("选择修改项：");
        chooseLabel.setPreferredSize(dimensionLabel);

        chooseComboBox = new JComboBox();
        chooseComboBox.setPreferredSize(dimensionText);

        JLabel numLabel = new JLabel("  修改为：");
        numLabel.setPreferredSize(dimensionLabel);

        numTextField = new JTextField();
        numTextField.setPreferredSize(dimensionText);

        JLabel commentLabel = new JLabel("  备    注:");
        commentLabel.setPreferredSize(dimensionLabel);

        commentTextArea = new JTextArea();
        commentTextArea.setPreferredSize(new Dimension(150, 100));
        commentTextArea.setLineWrap(true);
        confirmButton = new JButton("确定");
        cancelButton = new JButton("取消");

        JPanel productIdPanel = new JPanel();
        productIdPanel.add(productIdLabel);
        productIdPanel.add(productIdValueLabel);

        JPanel productNamePanel = new JPanel();
        productNamePanel.add(productNameLabel);
        productNamePanel.add(productNameTextField);

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
        buttonPanel.add(cancelButton);

        this.setLayout(new FlowLayout());
        this.add(productIdPanel);
        this.add(productNamePanel);
        this.add(choosePanel);
        this.add(numPanel);
        this.add(commentPanel);
        this.add(buttonPanel);
        this.setTitle("错误修正");
        this.setSize(300, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
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

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JLabel getProductIdValueLabel() {
        return productIdValueLabel;
    }

    public JTextField getProductNameTextField() {
        return productNameTextField;
    }
}
