package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class ProduceImportView extends JFrame {

    private JLabel infoLabel;

    //时间下拉框
    private JComboBox yearComboBox;
    private JComboBox monthComboBox;
    private JComboBox dayComboBox;

    private JButton confirmButton;
    private JButton cancelButton;

    public ProduceImportView(){
        init();
    }
    private void init(){


        infoLabel = new JLabel("请选择要导入的日期：");

        yearComboBox = new JComboBox();
        monthComboBox = new JComboBox();
        dayComboBox = new JComboBox();

        confirmButton = new JButton("确定");
        cancelButton = new JButton("取消");

        JPanel infoPanel = new JPanel();
        infoPanel.add(infoLabel);

        JPanel datePanel = new JPanel();
        datePanel.add(yearComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(dayComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        this.setLayout(new FlowLayout());
        this.add(infoPanel);
        this.add(datePanel);
        this.add(buttonPanel);
        this.setSize(300,150);
        this.setTitle("选择导入日期");
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    public JComboBox getYearComboBox() {
        return yearComboBox;
    }

    public JComboBox getMonthComboBox() {
        return monthComboBox;
    }

    public JComboBox getDayComboBox() {
        return dayComboBox;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public static void main(String[] args) {
        new ProduceImportView();
    }
}
