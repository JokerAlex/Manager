package com.manager.views;

import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@Component
public class ProduceAddView extends JFrame{
    private JTextField nameTextField;
    private JTextField numTextField;
    private JButton searchButton;
    private JButton confirmButton;

    private JTable productTable;
    private DefaultTableModel productTableModel;

    public ProduceAddView() {
        init();
    }

    private void init(){

        Dimension dimensionLabel = new Dimension(80,30);
        Dimension dimensionText = new Dimension(100,30);

        JLabel nameLabel = new JLabel("产品查询");
        nameLabel.setPreferredSize(dimensionLabel);
        nameTextField = new JTextField();
        nameTextField.setPreferredSize(dimensionText);

        JLabel numLabel = new JLabel("下单数量：");
        numLabel.setPreferredSize(dimensionLabel);

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

        numTextField = new JTextField();
        numTextField.setPreferredSize(dimensionText);

        searchButton = new JButton("查询");
        confirmButton = new JButton("确定");

        JPanel namePanel = new JPanel();
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);
        namePanel.add(searchButton);

        JScrollPane scrollPane = new JScrollPane(productTable);

        JPanel numPanel = new JPanel();
        numPanel.add(numLabel);
        numPanel.add(numTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(numPanel,BorderLayout.CENTER);
        panel.add(buttonPanel,BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());
        this.add(namePanel,BorderLayout.NORTH);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(panel,BorderLayout.SOUTH);
        this.setTitle("添加新的产品进度");
        this.setSize(300,400);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTable getProductTable() {
        return productTable;
    }

    public DefaultTableModel getProductTableModel() {
        return productTableModel;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JTextField getNumTextField() {
        return numTextField;
    }

    public static void main(String[] args) {
        new ProduceAddView();
    }
}
