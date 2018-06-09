package com.manager.controller;


import com.manager.pojo.*;
import com.manager.service.DateService;
import com.manager.service.OutputService;
import com.manager.service.ProduceService;
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
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

@Controller
public class MainController {
    //视图
    private MainView mainView;

    //service
    private ProductService productService;
    private OutputService outputService;
    private ProduceService produceService;
    private DateService dateService;

    //

    //表头
    private String[] productTitles = {"编号", "名称", "价格", "产品备注"};
    private String[] producetitles = {"名称", "下单", "备注", "木工", "备注", "油房", "备注", "包装", "备注",
            "特定", "备注", "北京", "备注", "北京特定", "备注", "本地合同", "备注", "外地合同", "备注", "等待", "备注"};
    private String[] outputTitles = {"名称", "下单", "木工", "油房", "包装", "特定", "北京", "北京特定", "本地合同", "外地合同", "等待"};

    @Autowired
    public MainController(MainView mainView, ProductService productService, OutputService outputService, ProduceService produceService, DateService dateService) {
        this.mainView = mainView;
        this.productService = productService;
        this.outputService = outputService;
        this.produceService = produceService;
        this.dateService = dateService;
    }

    //界面初始化
    public void initview() {

        mainView.init();
        //product数据填充
        loadProduct();

        //produce

        //output
        initOutputDate();

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
    private Vector<String> initTitle(String[] titles) {
        Vector<String> titleVector = new Vector<String>();
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
        Vector<Vector<java.io.Serializable>> data = new Vector<>();
        for (Product product : productList) {
            Vector<java.io.Serializable> row = new Vector<>();
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

        if (productName.equals("")) {
            JOptionPane.showMessageDialog(null, "请输入产品名称", "填写情况", JOptionPane.INFORMATION_MESSAGE);
        } else if (productPrice.equals("")) {
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
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setFileFilter(new FileNameExtensionFilter("excel(*.xls, *.xlsx)", "xls", "xlsx"));
        jfc.showDialog(new JLabel(), "选择");
        File file = jfc.getSelectedFile();
        if (!file.isFile()) {
            JOptionPane.showMessageDialog(null, "选取文件错误", "错误", JOptionPane.ERROR_MESSAGE);
        } else {
            String filePath = file.getAbsolutePath();
            Workbook wb = Excel.getWorkbook(filePath);
            Sheet sheet = Excel.getSheet(wb, 0);
            List<List<String>> products = Excel.getExcelRows(sheet, -1, -1);

            /*for (List<String> row : products){
                for (String s : row){
                    System.out.print(s+"\t");
                }
                System.out.println(row.size());
            }*/

            products.remove(0);//去除标题
            List<Product> productList = new ArrayList<>();
            boolean isDataright = true;
            for (List<String> row : products) {
                try {
                    if (row.size() == 2) {
                        productList.add(new Product(row.get(0), Float.valueOf(row.get(1))));
                    } else {
                        productList.add(new Product(row.get(0), Float.valueOf(row.get(1)), row.get(2)));
                    }
                } catch (NumberFormatException nfe) {
                    isDataright = false;
                    JOptionPane.showMessageDialog(null, "产品价格数据有错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (isDataright) {
                boolean isInsertSuccess = productService.insertBatchProduct(productList);
                if (isInsertSuccess) {
                    JOptionPane.showMessageDialog(null, "导入成功", "导入结果", JOptionPane.INFORMATION_MESSAGE);
                    loadProduct();
                } else {
                    JOptionPane.showMessageDialog(null, "导入失败", "导入结果", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    /**
     * 关于output的操作
     */

    private void initOutputDate() {
        int[] years = dateService.getAllYears();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        for (int i : years) {
            mainView.getOutput().getYearComboBox().addItem(i);
        }
        for (int i = 1; i <= month; i++) {
            mainView.getOutput().getMonthComboBox().addItem(i);
        }
        for (int i = 1; i <= day; i++) {
            mainView.getOutput().getDayComboBox().addItem(i);
        }
        //设置初始值
        mainView.getOutput().getYearComboBox().setSelectedItem(year);
        mainView.getOutput().getMonthComboBox().setSelectedItem(month);
        mainView.getOutput().getDayComboBox().setSelectedItem(day);

        mainView.getOutput().getYearComboBox().addActionListener(e -> {
            int yearSelected = (int) mainView.getOutput().getYearComboBox().getSelectedItem();
            int monthRefresh = 0;
            if (yearSelected < year) {
                monthRefresh = 12;
            } else if (yearSelected == year) {
                monthRefresh = month;
            }
            //mainView.getOutput().getMonthComboBox().removeAll();
            mainView.getOutput().getMonthComboBox().removeAllItems();
            for (int i = 1; i <= monthRefresh; i++) {
                mainView.getOutput().getMonthComboBox().addItem(i);
            }
        });
        mainView.getOutput().getMonthComboBox().addActionListener(e -> {
            if (mainView.getOutput().getMonthComboBox().getItemCount() != 0) {
                int yearSelected = (int) mainView.getOutput().getYearComboBox().getSelectedItem();
                int monthSelected = (int) mainView.getOutput().getMonthComboBox().getSelectedItem();
                int dayRefresh = 0;
                switch (monthSelected) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12: {
                        dayRefresh = 31;
                        break;
                    }
                    case 4:
                    case 6:
                    case 9:
                    case 11: {
                        dayRefresh = 30;
                        break;
                    }
                    case 2: {
                        if (yearSelected % 400 == 0 || (yearSelected % 4 == 0 && yearSelected % 100 != 0)) {
                            dayRefresh = 29;//闰年
                        } else {
                            dayRefresh = 28;//平年
                        }
                    }
                }
                if (yearSelected == year && monthSelected == month) {
                    dayRefresh = day;
                }
                mainView.getOutput().getDayComboBox().removeAllItems();
                //mainView.getOutput().getDayComboBox().removeAll();
                for (int i = 1; i <= dayRefresh; i++) {
                    mainView.getOutput().getDayComboBox().addItem(i);
                }
            } else {
                mainView.getOutput().getDayComboBox().removeAllItems();
            }

        });
    }

    //加载产值数据
    private void loadOutput(){
        int year = (int) mainView.getOutput().getYearComboBox().getSelectedItem();
        int month = (int) mainView.getOutput().getMonthComboBox().getSelectedItem();
        List<Output> outputList = outputService.getAllOutput(year,month);
        Vector<Vector<java.io.Serializable>> data = new Vector<>();
        for (Output output : outputList){
            Vector<java.io.Serializable> row = new Vector<>();
            row.add(output.getProductId());
            row.add(output.getProductName());
            row.add(output.getXiaDan());
            row.add(output.getMuGong());
            row.add(output.getYouFang());
            row.add(output.getBaoZhuang());
            row.add(output.getTeDing());
            row.add(output.getBeiJing());
            row.add(output.getBeiJingTeDing());
            data.add(row);
        }
        mainView.getOutput().getOutputTableModel().setDataVector(data,initTitle(outputTitles));
    }
}
