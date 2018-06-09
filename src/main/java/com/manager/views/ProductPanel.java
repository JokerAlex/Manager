package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


@Component
public class ProductPanel extends JPanel {
    private JButton importButton;//导入按钮
    private JButton addProductButton;//添加商品
    private JButton changeButton;//产品信息修改
    private JTable jTable;//表格
    private DefaultTableModel productTableModel;

    public ProductPanel(){

        productTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column)
            {
                //不可以编辑
                return false;
            }
        };

        jTable = new JTable(productTableModel);
        jTable.setShowHorizontalLines(true);
        jTable.setShowVerticalLines(true);
        jTable.setFillsViewportHeight(true);

        //添加表格用
        JScrollPane scrollPane = new JScrollPane(jTable);

        importButton = new JButton("导入");

        addProductButton = new JButton("添加产品");

        changeButton = new JButton("修改");


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addProductButton);
        buttonsPanel.add(changeButton);
        buttonsPanel.add(importButton);


        this.setLayout(new BorderLayout());
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(buttonsPanel,BorderLayout.SOUTH);
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JButton getAddProductButton() {
        return addProductButton;
    }

    public JButton getChangeButton() {
        return changeButton;
    }

    public JTable getjTable() {
        return jTable;
    }

    public DefaultTableModel getProductTableModel() {
        return productTableModel;
    }
}
