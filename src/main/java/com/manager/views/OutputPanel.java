package com.manager.views;


import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class OutputPanel extends JPanel {
    private JButton exportButton;//导出按钮
    private JButton changeButton;//产值信息修改
    private JTable outputTable;//表格
    private DefaultTableModel outputTableModel;

    private JLabel baoZhuangPriceSumLabel;
    private JLabel teDingPriceSumLabel;

    private JComboBox yearComboBox;
    private JComboBox monthComboBox;


    public OutputPanel(){

        //日期下拉框
        yearComboBox = new JComboBox();
        monthComboBox = new JComboBox();

        //表格
        outputTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column)
            {
                //不可以编辑
                return false;
            }
        };

        outputTable = new JTable(outputTableModel);
        outputTable.setShowHorizontalLines(true);
        outputTable.setShowVerticalLines(true);
        outputTable.setFillsViewportHeight(true);

        //添加表格用
        JScrollPane scrollPane = new JScrollPane(outputTable);


        Dimension dimension = new Dimension(100,30);
        JLabel baoZhuangLabel = new JLabel("包装金额合计：");
        baoZhuangLabel.setPreferredSize(dimension);
        baoZhuangPriceSumLabel = new JLabel();
        baoZhuangPriceSumLabel.setPreferredSize(dimension);
        JLabel teDingLabel = new JLabel("特定金额合计：");
        teDingLabel.setPreferredSize(dimension);
        teDingPriceSumLabel = new JLabel();
        teDingPriceSumLabel.setPreferredSize(dimension);

        exportButton = new JButton("导出");

        changeButton = new JButton("修改");

        JPanel datePanel = new JPanel();
        datePanel.add(yearComboBox);
        datePanel.add(monthComboBox);

        JPanel sumPanel = new JPanel();
        sumPanel.add(baoZhuangLabel);
        sumPanel.add(baoZhuangPriceSumLabel);
        sumPanel.add(teDingLabel);
        sumPanel.add(teDingPriceSumLabel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(changeButton);
        buttonsPanel.add(exportButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(sumPanel,BorderLayout.CENTER);
        bottomPanel.add(buttonsPanel,BorderLayout.SOUTH);


        this.setLayout(new BorderLayout());
        this.add(datePanel,BorderLayout.NORTH);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(bottomPanel,BorderLayout.SOUTH);
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JButton getChangeButton() {
        return changeButton;
    }

    public JTable getOutputTable() {
        return outputTable;
    }

    public DefaultTableModel getOutputTableModel() {
        return outputTableModel;
    }

    public JComboBox getYearComboBox() {
        return yearComboBox;
    }

    public JComboBox getMonthComboBox() {
        return monthComboBox;
    }

    public JLabel getBaoZhuangPriceSumLabel() {
        return baoZhuangPriceSumLabel;
    }

    public JLabel getTeDingPriceSumLabel() {
        return teDingPriceSumLabel;
    }
}
