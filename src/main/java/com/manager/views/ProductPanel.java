package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;


@Component
public class ProductPanel extends JPanel {
    private JButton importButton;//导入按钮
    private JButton addProductButton;//添加商品
    private JButton refreshButton;//刷新
    private JScrollPane scrollPane;//添加表格用
    private JTable jTable;//表格

    public ProductPanel(){
    }

    public void init(){

        jTable = new JTable();

        scrollPane = new JScrollPane();
        scrollPane.add(jTable);

        importButton = new JButton("导入");

        addProductButton = new JButton("添加产品");

        refreshButton = new JButton("刷新");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addProductButton);
        buttonsPanel.add(importButton);
        buttonsPanel.add(refreshButton);


        this.add(scrollPane,BorderLayout.CENTER);
        this.add(buttonsPanel,BorderLayout.SOUTH);
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JButton getAddProductButton() {
        return addProductButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JTable getjTable() {
        return jTable;
    }
}
