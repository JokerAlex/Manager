package com.manager.views;


import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class OutputPanel extends JPanel {
    private JButton exportButton;//导出按钮
    private JButton changeButton;//产值信息修改
    private JButton refreshButton;//刷新
    private JTable jTable;//表格
    private DefaultTableModel outputTableModel;

    private JComboBox yearComboBox;
    private JComboBox monthComboBox;
    private JComboBox dayComboBox;


    public OutputPanel(){

        //日期下拉框


        yearComboBox = new JComboBox();
        monthComboBox = new JComboBox();
        dayComboBox = new JComboBox();


        //表格
        outputTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column)
            {
                //不可以编辑
                return false;
            }
        };

        jTable = new JTable(outputTableModel);
        jTable.setShowHorizontalLines(true);
        jTable.setShowVerticalLines(true);
        jTable.setFillsViewportHeight(true);

        //添加表格用
        JScrollPane scrollPane = new JScrollPane(jTable);

        refreshButton = new JButton("刷新");

        exportButton = new JButton("导出");

        changeButton = new JButton("修改");

        JPanel datePanel = new JPanel();
        datePanel.add(yearComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(dayComboBox);
        datePanel.add(refreshButton);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(changeButton);
        buttonsPanel.add(exportButton);


        this.setLayout(new BorderLayout());
        this.add(datePanel,BorderLayout.NORTH);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(buttonsPanel,BorderLayout.SOUTH);
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JButton getChangeButton() {
        return changeButton;
    }

    public JTable getjTable() {
        return jTable;
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

    public JComboBox getDayComboBox() {
        return dayComboBox;
    }

}
