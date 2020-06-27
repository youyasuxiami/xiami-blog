package com.xiami.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;


public class ImprotExcelUtil {


    /**
     * 解析excel
     *
     * @param inputStream
     * @param originalFilename
     * @return
     * @throws IOException
     */
    public static List<List<Object>> analysisExcel(InputStream inputStream, String originalFilename) throws IOException {

        String extension = originalFilename.lastIndexOf(".") == -1 ? "" : originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if ("xls".equals(extension)) {
            return read2003Excel(inputStream);
        } else if ("xlsx".equals(extension)) {
            return read2007Excel(inputStream);
        } else {
            throw new IOException("不支持的文件类型");
        }

    }

    /**
     * 读取 office 2003 excel
     *
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static List<List<Object>> read2003Excel(InputStream inputStream) throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        HSSFWorkbook xwb = new HSSFWorkbook(inputStream);
        // 读取第一章表格内容
        HSSFSheet sheet = xwb.getSheetAt(0);
        // 获取标题内容
        Row rowTitle = sheet.getRow(1);

        Object value = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        for (int i = 0; i <= sheet
                .getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            List<Object> linked = new LinkedList<Object>();

            // 读取列
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int j = 0; j < cellCount; j++) {
                cell = row.getCell(j);
                if(cell==null){
                    cell=row.createCell(j);
                }
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
                DecimalFormat nf = new DecimalFormat("0");// 格式化数字
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        //   System.out.println(i+"行"+j+" 列 is String type");
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        //String dataFormat = cell.getCellStyle().getDataFormatString();    // 单元格格式
                        boolean isDate = DateUtil.isCellDateFormatted(cell);//判断是否是日期
                        //if ("General".equals(dataFormat)) {
                        //    value = df.format(cell.getNumericCellValue());
                        //} else
                        if (isDate) {//日期
                            Date date = cell.getDateCellValue();
                            //value = sdf.format(date);
                            value = new DateTime(date).toString("yyyy-MM-dd");
                        } else {// 数字，不是日期格式，防止数字过长！
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            value = cell.toString();
                            //value = cell.getNumericCellValue();
                        }
                        //   System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        //   System.out.println(i+"行"+j+" 列 is Boolean type");
                        value = cell.getBooleanCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
//          System.out.println(i+"行"+j+" 列 is Blank type");
                        value = "";
                        break;
                    default:
                        //   System.out.println(i+"行"+j+" 列 is default type");
                        value = cell.toString();
                }
                if (value == null || "".equals(value)) {
                    //  continue;
                }
                linked.add(value);
            }
            list.add(linked);
        }
        return list;
    }

    /**
     * 读取Office 2007 excel
     */
    private static List<List<Object>> read2007Excel(InputStream inputStream) throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
        // 读取第一章表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        // 获取标题内容
        Row rowTitle = sheet.getRow(1);

        Object value = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        for (int i = 0; i <= sheet
                .getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            List<Object> linked = new LinkedList<Object>();

            // 读取列
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int j = 0; j < cellCount; j++) {
                cell = row.getCell(j);
                if(cell==null){
                    cell=row.createCell(j);
                }
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
                DecimalFormat nf = new DecimalFormat("0");// 格式化数字
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        //   System.out.println(i+"行"+j+" 列 is String type");
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        //String dataFormat = cell.getCellStyle().getDataFormatString();    // 单元格格式
                        boolean isDate = DateUtil.isCellDateFormatted(cell);//判断是否是日期
                        //if ("General".equals(dataFormat)) {
                        //    value = df.format(cell.getNumericCellValue());
                        //} else
                        if (isDate) {//日期
                            Date date = cell.getDateCellValue();
                            //value = sdf.format(date);
                            value = new DateTime(date).toString("yyyy-MM-dd");
                        } else {// 数字，不是日期格式，防止数字过长！
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            value = cell.toString();
                            //value = cell.getNumericCellValue();
                        }
                        //   System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        //   System.out.println(i+"行"+j+" 列 is Boolean type");
                        value = cell.getBooleanCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
//          System.out.println(i+"行"+j+" 列 is Blank type");
                        value = "";
                        break;
                    default:
                        //   System.out.println(i+"行"+j+" 列 is default type");
                        value = cell.toString();
                }
                if (value == null || "".equals(value)) {
                    //  continue;
                }
                linked.add(value);
            }
            list.add(linked);
        }
        return list;
    }
}