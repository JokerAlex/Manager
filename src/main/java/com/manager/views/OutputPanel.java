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


        exportButton = new JButton("导出");

        changeButton = new JButton("修改");

        JPanel datePanel = new JPanel();
        datePanel.add(yearComboBox);
        datePanel.add(monthComboBox);

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


}
