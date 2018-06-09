package com.manager.controller;


import com.manager.pojo.*;
import com.manager.service.ProductService;
import com.manager.utils.Excel;
import com.manager.views.MainView;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Controller
public class MainController {
    //视图
    private MainView mainView;

    //service
    private ProductService productService;

    //

    //表头
    private String[] productTitles = {"编号", "名称", "价格", "产品备注"};
    private String[] producetitles = {"名称", "下单", "备注", "木工", "备注", "油房", "备注", "包装", "备注",
            "特定", "备注", "北京", "备注", "北京特定", "备注", "本地合同", "备注", "外地合同", "备注", "等待", "备注"};
    private String[] outputTitles = {"名称", "下单", "木工", "油房", "包装", "特定", "北京", "北京特定", "本地合同", "外地合同", "等待"};

    @Autowired
    public MainController(MainView mainView, ProductService productService) {
        this.mainView = mainView;
        this.productService = productService;
    }


    //界面初始化
    public void initview() {

        mainView.init();
        //product数据填充
        loadProduct();

    }

    //按钮初始化
    public void initController() {
        //product页面按钮
        mainView.getProduct().getAddProductButton().addActionListener(e -> mainView.getProductAddView().setVisible(true));
        mainView.getProduct().getChangeButton().addActionListener(e -> getSelectedRowData());
        mainView.getProduct().getImportButton().addActionListener(e -> importProduct());

        //productChangeView
        mainView.getProductChangeView().getConfirmButton().addActionListener(e -> productInfoSave());
        mainView.getProductChangeView().getCancelButton().addActionListener(e -> mainView.getProductChangeView().setVisible(false));

        //productAddView
        mainView.getProductAddView().getConfirmButton().addActionListener(e -> insertProductOne());
        mainView.getProductAddView().getCancelButton().addActionListener(e -> mainView.getProductAddView().setVisible(false));


    }

    /**
     * 通用
     */
    //添加列名
    private Vector initTitle(String[] titles) {
        Vector titleVector = new Vector();
        for (String title : titles) {
            titleVector.add(title);
        }
        return titleVector;
    }

    /**
     * 关于product的操作
     */
    //加载表格数据
    private void loadProduct() {
        List<Product> productList = productService.getAllProduct();
        //添加数据
        Vector data = new Vector();
        for (Product product : productList) {
            Vector row = new Vector();
            row.add(product.getProductId());
            row.add(product.getProductName());
            row.add(product.getProductPrice());
            row.add(product.getProductComment());
            data.add(row);
        }
        mainView.getProduct().getProductTableModel().setDataVector(data, initTitle(productTitles));
    }

    //手动添加产品
    public void insertProductOne() {
        //显示界面
        mainView.getProductAddView().setVisible(true);

        String productName = mainView.getProductAddView().getProductNameText().getText().trim();
        String productPrice = mainView.getProductAddView().getProductPriceText().getText().trim();
        String productComment = mainView.getProductAddView().getProductCommentTextArea().getText().trim();

        if (productName.equals("")){
            JOptionPane.showMessageDialog(null, "请输入产品名称", "填写情况", JOptionPane.INFORMATION_MESSAGE);
        } else if (productPrice.equals("")){
            JOptionPane.showMessageDialog(null, "请输入产品价格", "填写情况", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Product product = new Product();
            try {
                product.setProductName(productName);
                product.setProductPrice(Float.valueOf(productPrice));
                product.setProductComment(productComment);

                boolean result = productService.insertOneProduct(product);
                if (result) {
                    JOptionPane.showMessageDialog(null, "添加成功", "添加结果", JOptionPane.INFORMATION_MESSAGE);
                    mainView.getProductAddView().setVisible(false);
                    loadProduct();
                } else {
                    JOptionPane.showMessageDialog(null, "添加失败", "添加结果", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "输入的数据错误", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //获取选中行的数据
    private void getSelectedRowData() {
        int rowNum = mainView.getProduct().getjTable().getSelectedRow();
        if (rowNum != -1) {
            Integer productId = (Integer) mainView.getProduct().getjTable().getValueAt(rowNum, 0);
            String productName = (String) mainView.getProduct().getjTable().getValueAt(rowNum, 1);
            Float productPrice = (Float) mainView.getProduct().getjTable().getValueAt(rowNum, 2);
            String productComment = (String) mainView.getProduct().getjTable().getValueAt(rowNum, 3);
            //数据填充
            mainView.getProductChangeView().getProductIdValueLabel().setText(productId.toString());
            mainView.getProductChangeView().getProductNameText().setText(productName);
            mainView.getProductChangeView().getProductPriceText().setText(productPrice.toString());
            mainView.getProductChangeView().getProductCommentTextArea().setText(productComment);
            mainView.getProductChangeView().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "没有选中数据", "提示", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    //保存修改
    private void productInfoSave() {
        String productId = mainView.getProductChangeView().getProductIdValueLabel().getText().trim();
        String productName = mainView.getProductChangeView().getProductNameText().getText().trim();
        String productPrice = mainView.getProductChangeView().getProductPriceText().getText().trim();
        String productComment = mainView.getProductChangeView().getProductCommentTextArea().getText().trim();

        if (productName.equals("")) {
            JOptionPane.showMessageDialog(null, "请输入产品名称", "填写情况", JOptionPane.INFORMATION_MESSAGE);
        } else if (productPrice.equals("")) {
            JOptionPane.showMessageDialog(null, "请输入产品价格", "填写情况", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Product product = new Product();
            try {
                product.setProductId(Integer.valueOf(productId));
                product.setProductName(productName);
                product.setProductPrice(Float.valueOf(productPrice));
                product.setProductComment(productComment);

                boolean result = productService.updateProduct(product);
                if (result) {
                    JOptionPane.showMessageDialog(null, "更新成功", "更新结果", JOptionPane.INFORMATION_MESSAGE);
                    mainView.getProductChangeView().setVisible(false);
                    loadProduct();
                } else {
                    JOptionPane.showMessageDialog(null, "更新失败", "更新结果", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "输入的数据错误", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //导入数据
    private void importProduct() {
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setFileFilter(new FileNameExtensionFilter("excel(*.xls, *.xlsx)", "xls", "xlsx"));
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        if(!file.isFile()){
            JOptionPane.showMessageDialog(null, "选取文件错误", "错误", JOptionPane.ERROR_MESSAGE);
        } else {
            String filePath = file.getAbsolutePath();
            Workbook wb = Excel.getWorkbook(filePath);
            Sheet sheet = Excel.getSheet(wb,0);
            List<List<String>> products = Excel.getExcelRows(sheet,-1,-1);

            /*for (List<String> row : products){
                for (String s : row){
                    System.out.print(s+"\t");
                }
                System.out.println(row.size());
            }*/

            products.remove(0);//去除标题
            List<Product> productList = new ArrayList<>();
            boolean isDataright = true;
            for (List<String> row : products){
                try {
                    productList.add(new Product(row.get(0),Float.valueOf(row.get(1))));
                } catch (NumberFormatException nfe){
                    isDataright = false;
                    JOptionPane.showMessageDialog(null, "产品价格数据有错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (isDataright){
                boolean isInsertSuccess = productService.insertBatchProduct(productList);
                if (isInsertSuccess){
                    JOptionPane.showMessageDialog(null, "导入成功", "导入结果", JOptionPane.INFORMATION_MESSAGE);
                    loadProduct();
                } else {
                    JOptionPane.showMessageDialog(null, "导入失败", "导入结果", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
