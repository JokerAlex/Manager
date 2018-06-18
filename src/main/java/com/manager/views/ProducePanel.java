package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class ProducePanel extends JPanel {
    private JButton addButton;//产品进度添加
    private JButton updateButton;//进度更新
    private JButton delButton;//删除进度
    private JButton importButton;//导入进度
    private JButton exportButton;//导出按钮
    private JButton changeButton;//进度信息修改
    private JTable produceTable;//表格
    private DefaultTableModel produceTableModel;

    //查询功能
    private JTextField productNametextFiled;
    private JButton searchButton;

    //时间下拉框
    private JComboBox yearComboBox;
    private JComboBox monthComboBox;
    private JComboBox dayComboBox;

    public ProducePanel(){


        //日期下拉框
        yearComboBox = new JComboBox();
        monthComboBox = new JComboBox();
        dayComboBox = new JComboBox();

        produceTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column)
            {
                //不可以编辑
                return false;
            }
        };

        produceTable = new JTable(produceTableModel);
        produceTable.setShowHorizontalLines(true);
        produceTable.setShowVerticalLines(true);
        produceTable.setFillsViewportHeight(true);

        //添加表格用
        JScrollPane scrollPane = new JScrollPane(produceTable);

        addButton = new JButton("添加产品");
        updateButton = new JButton("进度更新");
        delButton = new JButton("删除");
        importButton = new JButton("导入进度");
        exportButton = new JButton("导出");
        changeButton = new JButton("错误修改");

        JLabel productNameLabel = new JLabel("进度查询：");
        productNameLabel.setPreferredSize(new Dimension(80,30));
        productNametextFiled = new JTextField();
        productNametextFiled.setPreferredSize(new Dimension(120,30));
        searchButton = new JButton("查询");


        JPanel datePanel = new JPanel();
        datePanel.add(yearComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(dayComboBox);
        datePanel.add(importButton);

        JPanel searchPanel = new JPanel();
        searchPanel.add(productNameLabel);
        searchPanel.add(productNametextFiled);
        searchPanel.add(searchButton);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(datePanel,BorderLayout.CENTER);
        northPanel.add(searchPanel,BorderLayout.SOUTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(delButton);
        buttonsPanel.add(changeButton);
        buttonsPanel.add(exportButton);


        this.setLayout(new BorderLayout());
        this.add(northPanel,BorderLayout.NORTH);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(buttonsPanel,BorderLayout.SOUTH);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDelButton() {
        return delButton;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JButton getChangeButton() {
        return changeButton;
    }

    public JTable getProduceTable() {
        return produceTable;
    }

    public DefaultTableModel getProduceTableModel() {
        return produceTableModel;
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

    public JTextField getProductNametextFiled() {
        return productNametextFiled;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
