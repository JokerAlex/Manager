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
    private JButton delButton;//删除
    private JTable productTable;//表格
    private DefaultTableModel productTableModel;

    public ProductPanel(){

        productTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column)
            {
                //不可以编辑
                return false;
            }
        };

        productTable = new JTable(productTableModel);
        productTable.setShowHorizontalLines(true);
        productTable.setShowVerticalLines(true);
        productTable.setFillsViewportHeight(true);

        //添加表格用
        JScrollPane scrollPane = new JScrollPane(productTable);

        importButton = new JButton("导入");

        addProductButton = new JButton("添加产品");

        changeButton = new JButton("修改");

        delButton = new JButton("删除");


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addProductButton);
        buttonsPanel.add(importButton);
        buttonsPanel.add(changeButton);
        buttonsPanel.add(delButton);


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

    public JButton getDelButton() {
        return delButton;
    }

    public JTable getProductTable() {
        return productTable;
    }

    public DefaultTableModel getProductTableModel() {
        return productTableModel;
    }
}
