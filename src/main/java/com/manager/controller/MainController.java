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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

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
    private String[] produceTitles = {"编号", "名称", "下单", "备注", "木工", "备注", "油房", "备注", "包装", "备注",
            "特定", "备注", "北京", "备注", "北京特定", "备注", "本地合同", "备注", "外地合同", "备注", "等待", "备注"};
    private String[] outputTitles = {"编号", "名称", "下单", "木工", "油房", "包装", "金额", "特定", "金额"};

    @Autowired
    public MainController(MainView mainView, ProductService productService, OutputService outputService, ProduceService produceService, DateService dateService) {
        this.mainView = mainView;
        this.productService = productService;
        this.outputService = outputService;
        this.produceService = produceService;
        this.dateService = dateService;
    }

    //界面初始化
    public void initView() {

        mainView.init();
        //product数据填充
        loadProduct();

        //produce
        initProduceDate();

        //output
        initOutputDate();

    }

    //按钮初始化
    public void initController() {
        //product页面按钮
        mainView.getProduct().getAddProductButton().addActionListener(e -> mainView.getProductAddView().setVisible(true));
        mainView.getProduct().getChangeButton().addActionListener(e -> getSelectedRowData());
        mainView.getProduct().getImportButton().addActionListener(e -> importProduct());
        mainView.getProduct().getDelButton().addActionListener(e -> delProduct());

        //productChangeView
        mainView.getProductChangeView().getConfirmButton().addActionListener(e -> productInfoSave());
        mainView.getProductChangeView().getCancelButton().addActionListener(e -> mainView.getProductChangeView().setVisible(false));

        //productAddView
        mainView.getProductAddView().getConfirmButton().addActionListener(e -> insertProductOne());
        mainView.getProductAddView().getCancelButton().addActionListener(e -> mainView.getProductAddView().setVisible(false));

        //produce页面按钮
        mainView.getProduce().getImportButton().addActionListener(e -> importProduceView());
        mainView.getProduce().getExportButton().addActionListener(e -> exportProduce());
        mainView.getProduce().getAddButton().addActionListener(e -> addProduceView());
        mainView.getProduce().getUpdateButton().addActionListener(e -> updateProduceView());
        mainView.getProduce().getDelButton().addActionListener(e -> delProduce());
        mainView.getProduce().getChangeButton().addActionListener(e -> changeProduceView());

        //produceImportView
        mainView.getProduceImportView().getYearComboBox().addActionListener(e -> importProduceYearComboBoxListener());
        mainView.getProduceImportView().getMonthComboBox().addActionListener(e -> importProduceMonthComboBoxListener());
        mainView.getProduceImportView().getDayComboBox().addActionListener(e -> importProduceDayComboBoxListener());
        mainView.getProduceImportView().getCancelButton().addActionListener(e -> mainView.getProduceImportView().setVisible(false));
        mainView.getProduceImportView().getConfirmButton().addActionListener(e -> importProduceToDatabase());

        //produceAddView
        mainView.getProduceAddView().getConfirmButton().addActionListener(e -> addProduceToDataBase());
        mainView.getProduceAddView().getSearchButton().addActionListener(e -> addProduceView());

        //produceUpdateView
        mainView.getProduceUpdateView().getCancelButton().addActionListener(e -> mainView.getProduceUpdateView().setVisible(false));
        mainView.getProduceUpdateView().getChooseComboBox().addActionListener(e -> updateProduceComboBoxListener());
        mainView.getProduceUpdateView().getConfirmButton().addActionListener(e -> saveUpdateProduceToDatabase());

        //produceChangeView
        mainView.getProduceChangeView().getCancelButton().addActionListener(e -> mainView.getProduceChangeView().setVisible(false));
        mainView.getProduceChangeView().getChooseComboBox().addActionListener(e -> changeProduceComboBoxListener());
        mainView.getProduceChangeView().getConfirmButton().addActionListener(e -> saveChangeProduceToDatabase());

        //output页面按钮
        mainView.getOutput().getChangeButton().addActionListener(e -> changeOutputView());
        mainView.getOutput().getExportButton().addActionListener(e -> exportOutput());

        //outputChangeView
        mainView.getOutputChangeView().getChooseComboBox().addActionListener(e -> changeOutputComboBoxListener());
        mainView.getOutputChangeView().getCancelButton().addActionListener(e -> mainView.getOutputChangeView().setVisible(false));
        mainView.getOutputChangeView().getConfirmButton().addActionListener(e -> saveChangeOutputToDatabase());

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
     * 隐藏表格中的某一列
     *
     * @param table 表格
     * @param index 要隐藏的列 的索引
     */
    private void hideColumn(JTable table, int index) {

        table.getColumnModel().getColumn(index).setMaxWidth(0);
        table.getColumnModel().getColumn(index).setPreferredWidth(0);
        table.getColumnModel().getColumn(index).setMinWidth(0);
        table.getColumnModel().getColumn(index).setWidth(0);

        table.getTableHeader().getColumnModel().getColumn(index).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(0);
    }

    private int getDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH) + 1;
        int dayNow = calendar.get(Calendar.DATE);
        int day = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: {
                day = 31;
                break;
            }
            case 4:
            case 6:
            case 9:
            case 11: {
                day = 30;
                break;
            }
            case 2: {
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                    day = 29;//闰年
                } else {
                    day = 28;//平年
                }
            }
        }
        if (year == yearNow && month == monthNow) {
            day = dayNow;
        }
        return day;
    }


    /*******************************************************************************************************************
     * 关于product的操作
     *******************************************************************************************************************
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
        hideColumn(mainView.getProduct().getProductTable(), 0);
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
        int rowNum = mainView.getProduct().getProductTable().getSelectedRow();
        if (rowNum != -1) {
            Integer productId = (Integer) mainView.getProduct().getProductTable().getValueAt(rowNum, 0);
            String productName = (String) mainView.getProduct().getProductTable().getValueAt(rowNum, 1);
            Float productPrice = (Float) mainView.getProduct().getProductTable().getValueAt(rowNum, 2);
            String productComment = (String) mainView.getProduct().getProductTable().getValueAt(rowNum, 3);
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
        //获取各项数据
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
            boolean isDataRight = true;
            for (List<String> row : products) {
                try {
                    if (row.size() == 2) {
                        productList.add(new Product(row.get(0), Float.valueOf(row.get(1))));
                    } else {
                        productList.add(new Product(row.get(0), Float.valueOf(row.get(1)), row.get(2)));
                    }
                } catch (NumberFormatException nfe) {
                    isDataRight = false;
                    JOptionPane.showMessageDialog(null, "产品价格数据有错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (isDataRight) {
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

    //delProduct
    private void delProduct() {
        int selectedRow = mainView.getProduct().getProductTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "没有选中数据", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //yes->0,no->1
            int n = JOptionPane.showConfirmDialog(null, "确定删除？", "删除", JOptionPane.YES_NO_OPTION);
            if (n == 0) {
                boolean isDel = productService.delProduct((int) mainView.getProduct().getProductTable().getValueAt(selectedRow, 0));
                if (isDel) {
                    JOptionPane.showMessageDialog(null, "删除成功", "删除结果", JOptionPane.INFORMATION_MESSAGE);
                    loadProduct();
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败", "删除结果", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    /*******************************************************************************************************************
     * 关于produce的操作
     *******************************************************************************************************************
     */

    //进度页面日期下拉框
    private void initProduceDate() {
        int[] years = dateService.getAllYears();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        for (int i : years) {
            mainView.getProduce().getYearComboBox().addItem(i);
        }
        for (int i = 1; i <= month; i++) {
            mainView.getProduce().getMonthComboBox().addItem(i);
        }
        for (int i = 1; i <= day; i++) {
            mainView.getProduce().getDayComboBox().addItem(i);
        }

        //设置初始值
        mainView.getProduce().getYearComboBox().setSelectedItem(year);
        mainView.getProduce().getMonthComboBox().setSelectedItem(month);
        mainView.getProduce().getDayComboBox().setSelectedItem(day);
        loadProduce();

        mainView.getProduce().getYearComboBox().addActionListener(e -> {
            int yearSelected = (int) mainView.getProduce().getYearComboBox().getSelectedItem();
            int monthRefresh = 0;
            if (yearSelected < year) {
                monthRefresh = 12;
            } else if (yearSelected == year) {
                monthRefresh = month;
            }
            mainView.getProduce().getMonthComboBox().removeAllItems();
            for (int i = 1; i <= monthRefresh; i++) {
                mainView.getProduce().getMonthComboBox().addItem(i);
            }
        });

        mainView.getProduce().getMonthComboBox().addActionListener(e -> {
            if (mainView.getProduce().getMonthComboBox().getItemCount() != 0) {
                int yearSelected = (int) mainView.getProduce().getYearComboBox().getSelectedItem();
                int monthSelected = (int) mainView.getProduce().getMonthComboBox().getSelectedItem();
                int dayRefresh = getDayOfMonth(yearSelected, monthSelected);
                mainView.getProduce().getDayComboBox().removeAllItems();
                for (int i = 1; i <= dayRefresh; i++) {
                    mainView.getProduce().getDayComboBox().addItem(i);
                }
            } else {
                mainView.getProduce().getDayComboBox().removeAllItems();
            }

        });

        mainView.getProduce().getDayComboBox().addActionListener(e -> {
            if (mainView.getProduce().getDayComboBox().getItemCount() != 0) {
                loadProduce();
            } else {
                //日期超前
                mainView.getProduce().getExportButton().setEnabled(false);
                mainView.getProduce().getImportButton().setEnabled(false);
                mainView.getProduce().getAddButton().setEnabled(false);
                mainView.getProduce().getUpdateButton().setEnabled(false);
                mainView.getProduce().getDelButton().setEnabled(false);
                int numOfRows = mainView.getProduce().getProduceTable().getRowCount();
                for (int i = 0; i < numOfRows; i++) {
                    //size和下标会变化
                    mainView.getProduce().getProduceTableModel().removeRow(0);
                }
            }
        });
    }

    //loadProduce
    private void loadProduce() {
        int year = (int) mainView.getProduce().getYearComboBox().getSelectedItem();
        int month = (int) mainView.getProduce().getMonthComboBox().getSelectedItem();
        int day = (int) mainView.getProduce().getDayComboBox().getSelectedItem();

        List<Produce> produceList = produceService.getAllProduce(year, month, day);
        if (produceList.size() == 0) {
            mainView.getProduce().getImportButton().setEnabled(true);
            mainView.getProduce().getExportButton().setEnabled(false);
        } else {
            mainView.getProduce().getImportButton().setEnabled(false);
            mainView.getProduce().getExportButton().setEnabled(true);
        }

        Calendar calendarSelected = Calendar.getInstance();
        calendarSelected.set(year, month - 1, day);
        Date select = calendarSelected.getTime();
        Calendar calendarNow = Calendar.getInstance();
        Date now = calendarNow.getTime();

        if (select.before(now) || select.after(now)) {
            mainView.getProduce().getImportButton().setEnabled(false);
            mainView.getProduce().getAddButton().setEnabled(false);
            mainView.getProduce().getUpdateButton().setEnabled(false);
            mainView.getProduce().getDelButton().setEnabled(false);
        } else {
            mainView.getProduce().getAddButton().setEnabled(true);
            mainView.getProduce().getUpdateButton().setEnabled(true);
            mainView.getProduce().getDelButton().setEnabled(true);
        }

        Vector<Vector<java.io.Serializable>> data = new Vector<>();
        for (Produce produce : produceList) {
            Vector<java.io.Serializable> row = new Vector<>();
            row.add(produce.getProductId());//0
            row.add(produce.getProductName());//1
            row.add(produce.getXiaDan());//2
            row.add(produce.getXiaDanComment());//3
            row.add(produce.getMuGong());//4
            row.add(produce.getMuGongComment());//5
            row.add(produce.getYouFang());//6
            row.add(produce.getYouFangComment());//7
            row.add(produce.getBaoZhuang());//8
            row.add(produce.getBaoZhuangComment());//9
            row.add(produce.getTeDing());//10
            row.add(produce.getTeDingComment());//11
            row.add(produce.getBeiJing());//12
            row.add(produce.getBeiJingComment());//13
            row.add(produce.getBeiJingTeDing());//14
            row.add(produce.getBeiJingTeDingComment());//15
            row.add(produce.getBenDiHeTong());//16
            row.add(produce.getBenDiHeTongComment());//17
            row.add(produce.getWaiDiHeTong());//18
            row.add(produce.getWaiDiHeTongComment());//19
            row.add(produce.getDeng());//20
            row.add(produce.getDengComment());//21
            data.add(row);
        }
        mainView.getProduce().getProduceTableModel().setDataVector(data, initTitle(produceTitles));
        hideColumn(mainView.getProduce().getProduceTable(), 0);
    }

    //addProduce-view
    private void addProduceView() {
        String name = mainView.getProduceAddView().getNameTextField().getText().trim();
        if (name == null) {
            name = "";
        }
        List<Product> productList = productService.searchProduct(name);
        Vector<Vector<java.io.Serializable>> data = new Vector<>();
        for (Product product : productList) {
            Vector<java.io.Serializable> row = new Vector<>();
            row.add(product.getProductId());
            row.add(product.getProductName());
            row.add(product.getProductPrice());
            data.add(row);
        }
        String[] titles = {"编号", "名称", "价格"};
        mainView.getProduceAddView().getProductTableModel().setDataVector(data, initTitle(titles));
        hideColumn(mainView.getProduceAddView().getProductTable(), 0);
        mainView.getProduceAddView().setVisible(true);
    }


    //addProduce-toDatabase
    private void addProduceToDataBase() {
        //获取下拉框选中的数据
        int selectedRow = mainView.getProduceAddView().getProductTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "没有选中数据", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int year = (int) mainView.getProduce().getYearComboBox().getSelectedItem();
            int month = (int) mainView.getProduce().getMonthComboBox().getSelectedItem();
            int day = (int) mainView.getProduce().getDayComboBox().getSelectedItem();
            int num = 0;
            boolean isDataRight = true;
            try {
                num = Integer.valueOf(mainView.getProduceAddView().getNumTextField().getText());
            } catch (NumberFormatException nfe) {
                isDataRight = false;
                JOptionPane.showMessageDialog(null, "下单数据有错误", "错误", JOptionPane.ERROR_MESSAGE);
            }
            if (isDataRight) {
                Produce produce = new Produce(year, month, day,
                        (int) mainView.getProduceAddView().getProductTable().getValueAt(selectedRow, 0),
                        (String) mainView.getProduceAddView().getProductTable().getValueAt(selectedRow, 1)
                );
                produce.setXiaDan(num);
                try {
                    boolean isInsert = produceService.insertOne(produce);
                    if (isInsert) {
                        JOptionPane.showMessageDialog(null, "添加成功", "添加结果", JOptionPane.INFORMATION_MESSAGE);
                        mainView.getProduceAddView().setVisible(false);
                        loadProduce();
                    } else {
                        JOptionPane.showMessageDialog(null, "添加失败", "添加结果", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "添加失败,产品不能重复添加", "添加结果", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    //updateProduceView
    private void updateProduceView() {
        int rowNum = mainView.getProduce().getProduceTable().getSelectedRow();
        if (rowNum == -1) {
            JOptionPane.showMessageDialog(null, "没有选中数据", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //下拉框初始化
            mainView.getProduceUpdateView().getChooseComboBox().removeAllItems();
            String[] produceTitles = {"下单", "木工", "油房", "包装", "特定", "北京", "北京特定", "本地合同", "外地合同", "等待"};
            for (String s : produceTitles) {
                mainView.getProduceUpdateView().getChooseComboBox().addItem(s);
            }
            //页面数据初始化
            mainView.getProduceUpdateView().getProductIdValueLabel().setText(String.valueOf((int) mainView.getProduce().getProduceTable().getValueAt(rowNum, 0)));//productId
            mainView.getProduceUpdateView().getProductNameValueLabel().setText((String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 1));//productName
            mainView.getProduceUpdateView().getNumTextField().setText("");
            mainView.getProduceUpdateView().getCommentTextArea().setText((String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 3));//默认下单备注
            mainView.getProduceUpdateView().setVisible(true);
        }
    }

    //updateProduceView下拉框listener
    private void updateProduceComboBoxListener() {
        if (mainView.getProduceUpdateView().getChooseComboBox().getItemCount() == 10) {
            //根据下拉框选项显示对应的备注信息
            int rowNum = mainView.getProduce().getProduceTable().getSelectedRow();
            String comment = null;
            String choose = (String) mainView.getProduceUpdateView().getChooseComboBox().getSelectedItem();
            switch (choose) {
                case "下单": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 3);
                    break;
                }
                case "木工": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 5);
                    break;
                }
                case "油房": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 7);
                    break;
                }
                case "包装": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 9);
                    break;
                }
                case "特定": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 11);
                    break;
                }
                case "北京": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 13);
                    break;
                }
                case "北京特定": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 15);
                    break;
                }
                case "本地合同": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 17);
                    break;
                }
                case "外地合同": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 19);
                    break;
                }
                case "等待": {
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(rowNum, 21);
                    break;
                }
            }
            mainView.getProduceUpdateView().getCommentTextArea().setText(comment);
        }
    }

    //saveUpdateProduce
    private void saveUpdateProduceToDatabase() {
        Produce produce = new Produce();
        produce.setYear((int) mainView.getProduce().getYearComboBox().getSelectedItem());
        produce.setMonth((int) mainView.getProduce().getMonthComboBox().getSelectedItem());
        produce.setDay((int) mainView.getProduce().getDayComboBox().getSelectedItem());
        produce.setProductId(Integer.valueOf(mainView.getProduceUpdateView().getProductIdValueLabel().getText()));
        produce.setProductName(mainView.getProduceUpdateView().getProductNameValueLabel().getText());
        boolean isDataRight = true;
        //获取新增的数量
        int updateNum = 0;
        try {
            updateNum = Integer.valueOf(mainView.getProduceUpdateView().getNumTextField().getText());
        } catch (NumberFormatException nfe) {
            isDataRight = false;
            JOptionPane.showMessageDialog(null, "数量数据有错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
        if (isDataRight) {
            int selectedRow = mainView.getProduce().getProduceTable().getSelectedRow();
            //获取备注信息
            String comment = mainView.getProduceUpdateView().getCommentTextArea().getText();
            String choose = (String) mainView.getProduceUpdateView().getChooseComboBox().getSelectedItem();
            boolean isNumOk = true;
            //更具修改项进行赋值
            Output output = new Output(produce.getYear(), produce.getMonth(), produce.getProductId(), produce.getProductName());
            switch (choose) {
                case "下单": {
                    if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 2) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，下单值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setXiaDan(updateNum);
                        produce.setXiaDanComment(comment);
                    }
                    break;
                }
                case "木工": {
                    if (updateNum > (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 2)) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，下单值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 4) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，木工值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setMuGong(updateNum);
                        produce.setMuGongComment(comment);
                        output.setXiaDan(updateNum);
                    }
                    break;
                }
                case "油房": {
                    if (updateNum > (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 4)) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，木工值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 6) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，油房值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setYouFang(updateNum);
                        produce.setYouFangComment(comment);
                        output.setMuGong(updateNum);
                    }
                    break;
                }
                case "包装": {
                    if (updateNum > (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 6)) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，油房值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 8) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，包装值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setBaoZhuang(updateNum);
                        produce.setBaoZhuangComment(comment);
                        output.setYouFang(updateNum);
                    }
                    break;
                }
                case "特定": {
                    if (updateNum > (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 6)) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，油房值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 10) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，特定值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setTeDing(updateNum);
                        produce.setTeDingComment(comment);
                        output.setYouFang(updateNum);
                    }
                    break;
                }
                case "北京": {
                    if (updateNum > (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 8)) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，包装值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 12) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，北京值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setBeiJing(updateNum);
                        produce.setBeiJingComment(comment);
                        output.setBaoZhuang(updateNum);
                    }
                    break;
                }
                case "北京特定": {
                    if (updateNum > (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 10)) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，特定值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 14) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，北京特定值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setBeiJingTeDing(updateNum);
                        produce.setBeiJingTeDingComment(comment);
                        output.setTeDing(updateNum);
                    }
                    break;
                }
                case "本地合同": {
                    if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 16) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，本地合同值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setBenDiHeTong(updateNum);
                        produce.setBenDiHeTongComment(comment);
                    }
                    break;
                }
                case "外地合同": {
                    if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 18) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，外地合同值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setWaiDiHeTong(updateNum);
                        produce.setWaiDiHeTongComment(comment);
                    }
                    break;
                }
                case "等待": {
                    if (updateNum + (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 20) < 0) {
                        isNumOk = false;
                        JOptionPane.showMessageDialog(null, "更新后，等待值为负数", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        produce.setDeng(updateNum);
                        produce.setDengComment(comment);
                    }
                    break;
                }
            }
            //根据选项判断操作
            if (isNumOk) {
                if (choose.equals("本地合同") || choose.equals("外地合同") || choose.equals("等待")) {
                    //这三个数据不影响产值表
                    boolean isSuccess = produceService.updateProduce(produce);
                    if (isSuccess) {
                        JOptionPane.showMessageDialog(null, "更新成功", "更新结果", JOptionPane.INFORMATION_MESSAGE);
                        mainView.getProduceUpdateView().setVisible(false);
                        loadProduce();
                    } else {
                        JOptionPane.showMessageDialog(null, "更新失败", "更新结果", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    //其他数据会影响产值表,先检查是否有改产品，若没有则插入
                    Output outputTemp = outputService.getOne(output);
                    if (outputTemp == null) {
                        outputService.insertOneOutput(output);
                    }
                    //判断是否插入成功
                    boolean isProduceSuccess = produceService.updateProduce(produce);
                    boolean isOutputSuccess = true;
                    if (!choose.equals("下单")) {
                        if (choose.equals("北京")) {
                            //产值价格计算
                            float price = (updateNum + outputTemp.getBaoZhuang()) * productService.getOne(produce.getProductId()).getProductPrice();
                            output.setBaoZhuangJinE(price);
                        } else if (choose.equals("北京特定")) {
                            float price = (updateNum + outputTemp.getTeDing()) * productService.getOne(produce.getProductId()).getProductPrice();
                            output.setTeDingJinE(price);
                        }
                        isOutputSuccess = outputService.updateOutput(output);
                    }
                    if (isOutputSuccess && isProduceSuccess) {
                        JOptionPane.showMessageDialog(null, "更新成功", "更新结果", JOptionPane.INFORMATION_MESSAGE);
                        mainView.getProduceUpdateView().setVisible(false);
                        loadProduce();
                        loadOutput();
                    } else {
                        JOptionPane.showMessageDialog(null, "更新失败", "更新结果", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    //删除产品进度
    private void delProduce() {
        int selectedRow = mainView.getProduce().getProduceTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "没有选中数据", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //yes->0,no->1
            int n = JOptionPane.showConfirmDialog(null, "确定删除？", "删除", JOptionPane.YES_NO_OPTION);
            if (n == 0) {
                Produce produce = new Produce(
                        (int) mainView.getProduce().getYearComboBox().getSelectedItem(),
                        (int) mainView.getProduce().getMonthComboBox().getSelectedItem(),
                        (int) mainView.getProduce().getDayComboBox().getSelectedItem(),
                        (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 0),
                        (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 1)
                );
                boolean isDel = produceService.delProduce(produce);
                if (isDel) {
                    JOptionPane.showMessageDialog(null, "删除成功", "删除结果", JOptionPane.INFORMATION_MESSAGE);
                    loadProduce();
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败", "删除结果", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    //错误修正
    private void changeProduceView() {
        int selectedRow = mainView.getProduce().getProduceTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "没有选中数据", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //下拉框初始化
            mainView.getProduceChangeView().getChooseComboBox().removeAllItems();
            String[] produceTitles = {"下单", "木工", "油房", "包装", "特定", "北京", "北京特定", "本地合同", "外地合同", "等待"};
            for (String s : produceTitles) {
                mainView.getProduceChangeView().getChooseComboBox().addItem(s);
            }
            //页面数据初始化
            mainView.getProduceChangeView().getProductIdValueLabel().setText(String.valueOf((int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 0)));//productId
            mainView.getProduceChangeView().getProductNameTextField().setText((String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 1));//productName
            mainView.getProduceChangeView().getNumTextField().setText(String.valueOf((int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 2)));//默认下单
            mainView.getProduceChangeView().getCommentTextArea().setText((String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 3));//默认下单备注
            mainView.getProduceChangeView().setVisible(true);
        }
    }

    //changeProduceView下拉框listener
    private void changeProduceComboBoxListener() {
        if (mainView.getProduceChangeView().getChooseComboBox().getItemCount() == 10) {
            //根据下拉框选项显示对应的备注信息
            int selectedRow = mainView.getProduce().getProduceTable().getSelectedRow();
            int num = 0;
            String comment = null;
            String choose = (String) mainView.getProduceChangeView().getChooseComboBox().getSelectedItem();
            switch (choose) {
                case "下单": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 2);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 3);
                    break;
                }
                case "木工": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 4);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 5);
                    break;
                }
                case "油房": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 6);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 7);
                    break;
                }
                case "包装": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 8);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 9);
                    break;
                }
                case "特定": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 10);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 11);
                    break;
                }
                case "北京": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 12);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 13);
                    break;
                }
                case "北京特定": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 14);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 15);
                    break;
                }
                case "本地合同": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 16);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 17);
                    break;
                }
                case "外地合同": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 18);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 19);
                    break;
                }
                case "等待": {
                    num = (int) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 20);
                    comment = (String) mainView.getProduce().getProduceTable().getValueAt(selectedRow, 21);
                    break;
                }
            }
            mainView.getProduceChangeView().getNumTextField().setText(String.valueOf(num));
            mainView.getProduceChangeView().getCommentTextArea().setText(comment);
        }
    }

    //saveUpdateProduce
    private void saveChangeProduceToDatabase() {
        Produce produce = new Produce();
        produce.setYear((int) mainView.getProduce().getYearComboBox().getSelectedItem());
        produce.setMonth((int) mainView.getProduce().getMonthComboBox().getSelectedItem());
        produce.setDay((int) mainView.getProduce().getDayComboBox().getSelectedItem());
        produce.setProductId(Integer.valueOf(mainView.getProduceChangeView().getProductIdValueLabel().getText()));
        produce.setProductName(mainView.getProduceChangeView().getProductNameTextField().getText());
        boolean isDataRight = true;
        //获取修改值
        int changeNum = 0;
        try {
            changeNum = Integer.valueOf(mainView.getProduceChangeView().getNumTextField().getText());
        } catch (NumberFormatException nfe) {
            isDataRight = false;
            JOptionPane.showMessageDialog(null, "数量数据有错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
        if (changeNum <= 0) {
            isDataRight = false;
            JOptionPane.showMessageDialog(null, "数量数据不能为0或负数", "错误", JOptionPane.ERROR_MESSAGE);
        }
        if (isDataRight) {
            //获取备注信息
            String comment = mainView.getProduceChangeView().getCommentTextArea().getText();
            String choose = (String) mainView.getProduceChangeView().getChooseComboBox().getSelectedItem();
            //更具修改项进行赋值
            switch (choose) {
                case "下单": {
                    produce.setXiaDan(changeNum);
                    produce.setXiaDanComment(comment);
                    break;
                }
                case "木工": {
                    produce.setMuGong(changeNum);
                    produce.setMuGongComment(comment);
                    break;
                }
                case "油房": {
                    produce.setYouFang(changeNum);
                    produce.setYouFangComment(comment);
                    break;
                }
                case "包装": {
                    produce.setBaoZhuang(changeNum);
                    produce.setBaoZhuangComment(comment);
                    break;
                }
                case "特定": {
                    produce.setTeDing(changeNum);
                    produce.setTeDingComment(comment);
                    break;
                }
                case "北京": {
                    produce.setBeiJing(changeNum);
                    produce.setBeiJingComment(comment);
                    break;
                }
                case "北京特定": {
                    produce.setBeiJingTeDing(changeNum);
                    produce.setBeiJingTeDingComment(comment);
                    break;
                }
                case "本地合同": {
                    produce.setBenDiHeTong(changeNum);
                    produce.setBenDiHeTongComment(comment);

                    break;
                }
                case "外地合同": {
                    produce.setWaiDiHeTong(changeNum);
                    produce.setWaiDiHeTongComment(comment);

                    break;
                }
                case "等待": {
                    produce.setDeng(changeNum);
                    produce.setDengComment(comment);
                    break;
                }
            }
            boolean isChange = produceService.changeProduce(produce);
            if (isChange) {
                JOptionPane.showMessageDialog(null, "修改成功", "修改结果", JOptionPane.INFORMATION_MESSAGE);
                mainView.getProduceChangeView().setVisible(false);
                loadProduce();
            } else {
                JOptionPane.showMessageDialog(null, "修改失败", "修改结果", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //importProduceView
    private void importProduceView() {
        //加载日期下拉框
        int[] years = dateService.getAllYears();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        for (int i : years) {
            mainView.getProduceImportView().getYearComboBox().addItem(i);
        }
        for (int i = 1; i <= month; i++) {
            mainView.getProduceImportView().getMonthComboBox().addItem(i);
        }
        for (int i = 1; i <= day; i++) {
            mainView.getProduceImportView().getDayComboBox().addItem(i);
        }

        //设置初始值
        mainView.getProduceImportView().getYearComboBox().setSelectedItem(year);
        mainView.getProduceImportView().getMonthComboBox().setSelectedItem(month);
        mainView.getProduceImportView().getDayComboBox().setSelectedItem(day - 1);
        mainView.getProduceImportView().setVisible(true);
    }

    //importProduceYearComboBoxListener
    private void importProduceYearComboBoxListener() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int yearSelected = (int) mainView.getProduceImportView().getYearComboBox().getSelectedItem();
        int monthRefresh = 0;
        if (yearSelected < year) {
            monthRefresh = 12;
        } else if (yearSelected == year) {
            monthRefresh = month;
        }
        mainView.getProduceImportView().getMonthComboBox().removeAllItems();
        for (int i = 1; i <= monthRefresh; i++) {
            mainView.getProduceImportView().getMonthComboBox().addItem(i);
        }
    }

    //importProduceMonthComboBoxListener
    private void importProduceMonthComboBoxListener() {
        if (mainView.getProduceImportView().getMonthComboBox().getItemCount() != 0) {
            int yearSelected = (int) mainView.getProduceImportView().getYearComboBox().getSelectedItem();
            int monthSelected = (int) mainView.getProduceImportView().getMonthComboBox().getSelectedItem();
            int dayRefresh = getDayOfMonth(yearSelected, monthSelected);
            Calendar calendar = Calendar.getInstance();
            int yearNow = calendar.get(Calendar.YEAR);
            int monthNow = calendar.get(Calendar.MONTH) + 1;
            if (yearSelected == yearNow && monthSelected == monthNow) {
                dayRefresh -= 1;
            }
            mainView.getProduceImportView().getDayComboBox().removeAllItems();
            for (int i = 1; i <= dayRefresh; i++) {
                mainView.getProduceImportView().getDayComboBox().addItem(i);
            }
        } else {
            mainView.getProduceImportView().getDayComboBox().removeAllItems();
        }
    }

    //importProduceDayComboBoxListener
    private void importProduceDayComboBoxListener() {
        if (mainView.getProduceImportView().getDayComboBox().getItemCount() != 0) {
            mainView.getProduceImportView().getConfirmButton().setEnabled(true);
        } else {
            mainView.getProduceImportView().getConfirmButton().setEnabled(false);
        }
    }

    //importProduceToDatabase
    private void importProduceToDatabase() {
        int year = (int) mainView.getProduceImportView().getYearComboBox().getSelectedItem();
        int month = (int) mainView.getProduceImportView().getMonthComboBox().getSelectedItem();
        int day = (int) mainView.getProduceImportView().getDayComboBox().getSelectedItem();

        Calendar calendar = Calendar.getInstance();
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH) + 1;
        int dayNow = calendar.get(Calendar.DATE);

        List<Produce> produceList = produceService.getAllProduce(year, month, day);
        for (Produce produce : produceList) {
            produce.setYear(yearNow);
            produce.setMonth(monthNow);
            produce.setDay(dayNow);
        }
        boolean isSuccess = produceService.insertBatchProduce(produceList);
        if (isSuccess) {
            JOptionPane.showMessageDialog(null, "导入成功", "导入结果", JOptionPane.INFORMATION_MESSAGE);
            mainView.getProduceImportView().setVisible(false);
            loadProduce();
        } else {
            JOptionPane.showMessageDialog(null, "导入失败", "导入结果", JOptionPane.ERROR_MESSAGE);
        }
    }

    //exportProduce
    public void exportProduce() {
        int year = (int) mainView.getProduce().getYearComboBox().getSelectedItem();
        int month = (int) mainView.getProduce().getMonthComboBox().getSelectedItem();
        int day = (int) mainView.getProduce().getDayComboBox().getSelectedItem();
        //弹出文件选择框
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("excel(*.xls, *.xlsx)", "xls", "xlsx"));

        //下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
        int option = chooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {    //假如用户选择了保存
            File file = chooser.getSelectedFile();

            String fileName = chooser.getName(file);   //从文件名输入框中获取文件名

            //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                file = new File(chooser.getCurrentDirectory(), fileName + ".xlsx");
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);

                String title = year + "-" + month + "-" + day + "\t" + "进度报表";
                String[] headers = {"名称", "下单", "备注", "木工", "备注", "油房", "备注", "包装", "备注",
                        "特定", "备注", "北京", "备注", "北京特定", "备注", "本地合同", "备注", "外地合同", "备注", "等待", "备注"};
                List<Produce> produceList = produceService.getAllProduce(year, month, day);
                List<List<String>> listList = new ArrayList<>();
                for (Produce produce : produceList) {
                    List<String> list = new ArrayList<>();
                    list.add(0, produce.getProductName());
                    list.add(1, String.valueOf(produce.getXiaDan()));
                    list.add(2, produce.getXiaDanComment());
                    list.add(3, String.valueOf(produce.getMuGong()));
                    list.add(4, produce.getMuGongComment());
                    list.add(5, String.valueOf(produce.getYouFang()));
                    list.add(6, produce.getYouFangComment());
                    list.add(7, String.valueOf(produce.getBaoZhuang()));
                    list.add(8, produce.getBaoZhuangComment());
                    list.add(9, String.valueOf(produce.getTeDing()));
                    list.add(10, produce.getTeDingComment());
                    list.add(11, String.valueOf(produce.getBeiJing()));
                    list.add(12, produce.getBeiJingComment());
                    list.add(13, String.valueOf(produce.getBeiJingTeDing()));
                    list.add(14, produce.getBeiJingTeDingComment());
                    list.add(15, String.valueOf(produce.getBenDiHeTong()));
                    list.add(16, produce.getBenDiHeTongComment());
                    list.add(17, String.valueOf(produce.getWaiDiHeTong()));
                    list.add(18, produce.getWaiDiHeTongComment());
                    list.add(19, String.valueOf(produce.getDeng()));
                    list.add(20, produce.getDengComment());
                    listList.add(list);
                }
                Excel.exportData(title, headers, listList, fos, false);
                fos.close();
                JOptionPane.showMessageDialog(null, "导出成功", "导出结果", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "导出失败", "导出结果", JOptionPane.ERROR_MESSAGE);
                System.err.println("IO异常");
                e.printStackTrace();
            }
        }
    }

    /*******************************************************************************************************************
     * 关于output的操作
     *******************************************************************************************************************
     */

    private void initOutputDate() {
        int[] years = dateService.getAllYears();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        for (int i : years) {
            mainView.getOutput().getYearComboBox().addItem(i);
        }
        for (int i = 1; i <= month; i++) {
            mainView.getOutput().getMonthComboBox().addItem(i);
        }

        //设置初始值
        mainView.getOutput().getYearComboBox().setSelectedItem(year);
        mainView.getOutput().getMonthComboBox().setSelectedItem(month);
        loadOutput();

        mainView.getOutput().getYearComboBox().addActionListener(e -> {
            int yearSelected = (int) mainView.getOutput().getYearComboBox().getSelectedItem();
            int monthRefresh = 0;
            if (yearSelected < year) {
                monthRefresh = 12;
            } else if (yearSelected == year) {
                monthRefresh = month;
            }
            mainView.getOutput().getMonthComboBox().removeAllItems();
            for (int i = 1; i <= monthRefresh; i++) {
                mainView.getOutput().getMonthComboBox().addItem(i);
            }
        });

        mainView.getOutput().getMonthComboBox().addActionListener(e -> {
            if (mainView.getOutput().getMonthComboBox().getItemCount() != 0) {
                loadOutput();
            } else {
                //日期产前导出按钮不可用
                mainView.getOutput().getExportButton().setEnabled(false);
                int numOfRows = mainView.getOutput().getOutputTableModel().getRowCount();
                for (int i = 0; i < numOfRows; i++) {
                    //size和下标会变化
                    mainView.getOutput().getOutputTableModel().removeRow(0);
                }
            }
        });
    }

    //加载产值数据
    private void loadOutput() {
        int year = (int) mainView.getOutput().getYearComboBox().getSelectedItem();
        int month = (int) mainView.getOutput().getMonthComboBox().getSelectedItem();
        List<Output> outputList = outputService.getAllOutput(year, month);

        //结果集设置导出按钮能否点击
        if (outputList.size() == 0) {
            mainView.getOutput().getExportButton().setEnabled(false);
        } else {
            mainView.getOutput().getExportButton().setEnabled(true);
        }

        float baoZhuangPriceSum = 0;
        float teDingPriceSum = 0;
        Vector<Vector<java.io.Serializable>> data = new Vector<>();
        for (Output output : outputList) {
            Vector<java.io.Serializable> row = new Vector<>();
            row.add(output.getProductId());
            row.add(output.getProductName());
            row.add(output.getXiaDan());
            row.add(output.getMuGong());
            row.add(output.getYouFang());
            row.add(output.getBaoZhuang());
            row.add(output.getBaoZhuangJinE());
            row.add(output.getTeDing());
            row.add(output.getTeDingJinE());
            data.add(row);
            baoZhuangPriceSum += output.getBaoZhuangJinE();
            teDingPriceSum += output.getTeDingJinE();
        }
        mainView.getOutput().getOutputTableModel().setDataVector(data, initTitle(outputTitles));
        hideColumn(mainView.getOutput().getOutputTable(), 0);
        mainView.getOutput().getBaoZhuangPriceSumLabel().setText(String.valueOf(baoZhuangPriceSum));
        mainView.getOutput().getTeDingPriceSumLabel().setText(String.valueOf(teDingPriceSum));
    }


    //changeOutputView
    private void changeOutputView() {
        int selectedRow = mainView.getOutput().getOutputTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "没有选中数据", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //下拉框初始化
            mainView.getOutputChangeView().getChooseComboBox().removeAllItems();
            String[] outputTitles = {"下单", "木工", "油房", "包装", "特定"};
            for (String s : outputTitles) {
                mainView.getOutputChangeView().getChooseComboBox().addItem(s);
            }
            //页面数据初始化
            mainView.getOutputChangeView().getProductIdValueLabel().setText(String.valueOf((int) mainView.getOutput().getOutputTable().getValueAt(selectedRow, 0)));
            mainView.getOutputChangeView().getProductNameTextFiled().setText((String) mainView.getOutput().getOutputTable().getValueAt(selectedRow, 1));
            mainView.getOutputChangeView().getNumTextField().setText(String.valueOf((int) mainView.getOutput().getOutputTable().getValueAt(selectedRow, 2)));//默认下单数据
            mainView.getOutputChangeView().setVisible(true);
        }
    }

    //changeOutputView下拉框listener
    private void changeOutputComboBoxListener() {
        if (mainView.getOutputChangeView().getChooseComboBox().getItemCount() == 5) {
            int selectedRow = mainView.getOutput().getOutputTable().getSelectedRow();
            int num = 0;
            String choose = (String) mainView.getOutputChangeView().getChooseComboBox().getSelectedItem();
            switch (choose) {
                case "下单": {
                    num = (int) mainView.getOutput().getOutputTable().getValueAt(selectedRow, 2);
                    break;
                }
                case "木工": {
                    num = (int) mainView.getOutput().getOutputTable().getValueAt(selectedRow, 3);
                    break;
                }
                case "油房": {
                    num = (int) mainView.getOutput().getOutputTable().getValueAt(selectedRow, 4);
                    break;
                }
                case "包装": {
                    num = (int) mainView.getOutput().getOutputTable().getValueAt(selectedRow, 5);
                    break;
                }
                case "特定": {
                    num = (int) mainView.getOutput().getOutputTable().getValueAt(selectedRow, 7);
                    break;
                }
            }
            mainView.getOutputChangeView().getNumTextField().setText(String.valueOf(num));
        }
    }

    //saveChangeOutput
    private void saveChangeOutputToDatabase() {
        Output output = new Output(
                (int) mainView.getOutput().getYearComboBox().getSelectedItem(),
                (int) mainView.getOutput().getMonthComboBox().getSelectedItem(),
                Integer.valueOf(mainView.getOutputChangeView().getProductIdValueLabel().getText()),
                mainView.getOutputChangeView().getProductNameTextFiled().getText()
        );
        boolean isDataRight = true;
        //获取修改值
        int changeNum = 0;
        try {
            changeNum = Integer.valueOf(mainView.getOutputChangeView().getNumTextField().getText());
        } catch (NumberFormatException nfe) {
            isDataRight = false;
            JOptionPane.showMessageDialog(null, "数量数据有错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
        if (changeNum <= 0) {
            isDataRight = false;
            JOptionPane.showMessageDialog(null, "数量数据不能为0或负数", "错误", JOptionPane.ERROR_MESSAGE);
        }
        if (isDataRight) {
            String choose = (String) mainView.getOutputChangeView().getChooseComboBox().getSelectedItem();
            switch (choose) {
                case "下单": {
                    output.setXiaDan(changeNum);
                    break;
                }
                case "木工": {
                    output.setMuGong(changeNum);
                    break;
                }
                case "油房": {
                    output.setYouFang(changeNum);
                    break;
                }
                case "包装": {
                    output.setBaoZhuang(changeNum);
                    output.setBaoZhuangJinE(changeNum * productService.getOne(output.getProductId()).getProductPrice());
                    break;
                }
                case "特定": {
                    output.setTeDing(changeNum);
                    output.setTeDingJinE(changeNum * productService.getOne(output.getProductId()).getProductPrice());
                    break;
                }
            }
            boolean isChange = outputService.changeOutput(output);
            if (isChange) {
                JOptionPane.showMessageDialog(null, "修改成功", "修改结果", JOptionPane.INFORMATION_MESSAGE);
                mainView.getOutputChangeView().setVisible(false);
                loadOutput();
            } else {
                JOptionPane.showMessageDialog(null, "修改失败", "修改结果", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //exportProduce
    public void exportOutput() {
        int year = (int) mainView.getOutput().getYearComboBox().getSelectedItem();
        int month = (int) mainView.getOutput().getMonthComboBox().getSelectedItem();
        //弹出文件选择框
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("excel(*.xls, *.xlsx)", "xls", "xlsx"));

        //下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
        int option = chooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {    //假如用户选择了保存
            File file = chooser.getSelectedFile();

            String fileName = chooser.getName(file);   //从文件名输入框中获取文件名

            //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                file = new File(chooser.getCurrentDirectory(), fileName + ".xlsx");
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);

                String title = year + "-" + month + "\t" + "产值报表";
                String[] headers = {"名称", "下单", "木工", "油房", "包装", "金额", "特定", "金额"};
                List<Output> outputList = outputService.getAllOutput(year, month);
                List<List<String>> listList = new ArrayList<>();
                for (Output output : outputList) {
                    List<String> list = new ArrayList<>();
                    list.add(0, output.getProductName());
                    list.add(1, String.valueOf(output.getXiaDan()));
                    list.add(2, String.valueOf(output.getMuGong()));
                    list.add(3, String.valueOf(output.getYouFang()));
                    list.add(4, String.valueOf(output.getBaoZhuang()));
                    list.add(5, String.valueOf(output.getBaoZhuangJinE()));
                    list.add(6, String.valueOf(output.getTeDing()));
                    list.add(7, String.valueOf(output.getTeDingJinE()));
                    listList.add(list);
                }
                Excel.exportData(title, headers, listList, fos, true);
                fos.close();
                JOptionPane.showMessageDialog(null, "导出成功", "导出结果", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "导出失败", "导出结果", JOptionPane.ERROR_MESSAGE);
                System.err.println("IO异常");
                e.printStackTrace();
            }
        }
    }

}
