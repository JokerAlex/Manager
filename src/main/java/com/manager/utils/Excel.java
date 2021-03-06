package com.manager.utils;


import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;


import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Excel {

    private final static String excel2003L =".xls";    //2003- 版本的excel
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel


    /**
     * 获取工作簿
     * @param fileName
     * @return wb
     */
    public static Workbook getWorkbook(String fileName){
        Workbook wb = null;
        InputStream inStr;
        try {
            inStr = new FileInputStream(fileName);
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            if(excel2003L.equals(fileType)){
                wb = new HSSFWorkbook(inStr);  //2003-
            }else if(excel2007U.equals(fileType)){
                wb = new XSSFWorkbook(inStr);  //2007+
            }
        } catch (FileNotFoundException fofe){
            fofe.printStackTrace();
        } catch (IOException ie){
            ie.printStackTrace();
        }
        return wb;
    }

    /**
     * 获取工作表
     * @param wb
     * @param sheetIndex
     * @return sheet
     */
    public static Sheet getSheet(Workbook wb, int sheetIndex){
        if (wb == null){
            throw new RuntimeException("工作簿为空");
        }
        int sheetSize = wb.getNumberOfSheets();
        if (sheetIndex < 0 || sheetIndex > sheetSize){
            //throw new RuntimeException("工作表获取错误");
        }
        return wb.getSheetAt(sheetIndex);
    }

    /**
     * 获取单元格内容
     * @param cell
     * @return cellValue
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null){
            cellValue = "null";
        } else {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue().trim();
                    cellValue = StringUtils.isEmpty(cellValue) ? "NULL" : cellValue;
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula().trim());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        cellValue = DateUtil.parseYYYYMMDDDate(cell.getDateCellValue().toString()).toString();
                    } else {
                        cellValue = new DecimalFormat("#.##").format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "NULL";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "ERROR";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue;
    }

    /**
     * 获取工作表所有行的数据
     * @param sheet
     * @param startLine
     * @param endLine
     * @return List<List<String>>
     */
    public static List<List<String>> getExcelRows(Sheet sheet, int startLine, int endLine){
        List<List<String>> listResult = new ArrayList<List<String>>();
        // 如果开始行号和结束行号都是-1的话，则全表读取
        if (startLine == -1)
            startLine = 0;
        if (endLine == -1) {
            endLine = sheet.getLastRowNum() + 1;
        } else {
            endLine += 1;
        }

        for (int i = startLine; i < endLine; i++) {
            Row row = sheet.getRow(i);
            if (row == null){
                //空行直接跳过
                continue;
            }
            //获取列数
            int rowSize = row.getLastCellNum();
            //接受每一行的结果
            List<String> rowList = new ArrayList<String>();
            for (int j = 0; j < rowSize; j++) {
                Cell cell = row.getCell(j);
                String temp = getCellValue(cell);
                rowList.add(j,temp);
            }
            listResult.add(i,rowList);
        }
        return listResult;
    }

    /**
     * 获取工作表总行数
     * @param sheet
     * @return
     */
    public static int getRowNumber(Sheet sheet){
        return sheet.getPhysicalNumberOfRows();
    }


    //导出excel文件
    public static void exportData(String title, String[] headers,List<List<String>> list, FileOutputStream fileOutputStream, boolean isOutput) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //单元格样式
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框

        //标题样式
        CellStyle cellStyleTitle = wb.createCellStyle();
        cellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 20);
        cellStyleTitle.setFont(font);
        //第一行合并
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, headers.length-1);
        sheet.addMergedRegion(region);
        Row row;
        Cell cell;
        //填充第0行数据标题
        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue(title);
        cell.setCellStyle(cellStyleTitle);
        row.setHeightInPoints(30);
        //填充第1行表头
        row = sheet.createRow(1);
        for (int i = 0;i<headers.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(cellStyle);
        }
        float baoZhuangPriceSum = 0;
        float teDingPriceSum = 0;
        //填充数据,从第2行开始
        for (int i=0;i<list.size();i++){
            row = sheet.createRow(i+2);
            for (int j=0;j<list.get(i).size();j++){
                cell = row.createCell(j);
                cell.setCellValue(list.get(i).get(j));
                cell.setCellStyle(cellStyle);
                if (isOutput){
                    if (j == 5){
                        baoZhuangPriceSum += Float.valueOf(list.get(i).get(j));
                    } else if (j == 7){
                        teDingPriceSum += Float.valueOf(list.get(i).get(j));
                    }
                }
            }
        }
        if (isOutput) {
            row = sheet.createRow(sheet.getLastRowNum() + 1);
            cell = row.createCell(4);
            cell.setCellValue("包装金额合计");
            cell = row.createCell(5);
            cell.setCellValue(baoZhuangPriceSum);
            cell = row.createCell(6);
            cell.setCellValue("特定金额合计");
            cell = row.createCell(7);
            cell.setCellValue(teDingPriceSum);
        }
        wb.write(fileOutputStream);
    }
}
