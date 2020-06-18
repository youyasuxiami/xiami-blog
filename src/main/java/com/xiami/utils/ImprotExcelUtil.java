package com.xiami.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class ImprotExcelUtil {


	/**
	 * 解析excel
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
      * @throws IOException
      * @throws FileNotFoundException */
     private static List<List<Object>> read2003Excel(InputStream inputStream) throws IOException{
         List<List<Object>> list = new LinkedList<List<Object>>();
         HSSFWorkbook hwb = new HSSFWorkbook(inputStream);
         HSSFSheet sheet = hwb.getSheetAt(0);
         Object value = null;
         HSSFRow row = null;
         HSSFCell cell = null;
         for(int i = sheet.getFirstRowNum();i<= sheet.getPhysicalNumberOfRows();i++){
          row = sheet.getRow(i);
          if (row == null) {
           continue;
          }
          List<Object> linked = new LinkedList<Object>();
          for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
           cell = row.getCell(j);
           if (cell == null) {
            continue;
           }
           DecimalFormat df = new DecimalFormat("0");// 格式化 number String 字符
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
           DecimalFormat nf = new DecimalFormat("0");// 格式化数字
           switch (cell.getCellType()) {
           case XSSFCell.CELL_TYPE_STRING:
          //  System.out.println(i+"行"+j+" 列 is String type");
            value = cell.getStringCellValue();
            break;
           case XSSFCell.CELL_TYPE_NUMERIC:
         //   System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
            if("@".equals(cell.getCellStyle().getDataFormatString())){
               value = df.format(cell.getNumericCellValue());
            } else if("General".equals(cell.getCellStyle().getDataFormatString())){
               value = nf.format(cell.getNumericCellValue());
            }else{
              value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
            }
            break;
           case XSSFCell.CELL_TYPE_BOOLEAN:
         //   System.out.println(i+"行"+j+" 列 is Boolean type");
            value = cell.getBooleanCellValue();
            break;
           case XSSFCell.CELL_TYPE_BLANK:
         //   System.out.println(i+"行"+j+" 列 is Blank type");
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
      * */
      private static List<List<Object>> read2007Excel(InputStream inputStream) throws IOException {
         List<List<Object>> list = new LinkedList<List<Object>>();
         // 构造 XSSFWorkbook 对象，strPath 传入文件路径
         XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
         // 读取第一章表格内容
         XSSFSheet sheet = xwb.getSheetAt(0);
         Object value = null;
         XSSFRow row = null;
         XSSFCell cell = null;
          return read(list, sheet);
      }

    private static List<List<Object>> read(List<List<Object>> list, XSSFSheet sheet) {
        XSSFRow row;
        XSSFCell cell;
        Object value;
        for (int i = sheet.getFirstRowNum(); i <= sheet
          .getPhysicalNumberOfRows(); i++) {
         row = sheet.getRow(i);
         if (row == null) {
          continue;
         }
         List<Object> linked = new LinkedList<Object>();
         for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
          cell = row.getCell(j);
          if (cell == null) {
           continue;
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
        //   System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());
           if("@".equals(cell.getCellStyle().getDataFormatString())){
             value = df.format(cell.getNumericCellValue());
           } else if("General".equals(cell.getCellStyle().getDataFormatString())){
             value = nf.format(cell.getNumericCellValue());
           }else{
            value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
           }
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

    public static boolean ifXlsx(MultipartFile file){
          if (!file.isEmpty()) {
              //对文文件的全名进行截取然后在后缀名进行删选。
              int begin = file.getOriginalFilename().indexOf(".");
              int last = file.getOriginalFilename().length();
              //获得文件后缀名
              String a = file.getOriginalFilename().substring(begin, last);
              //需要的xlsx文件
              //                成功
//                错误
              return a.endsWith(".xlsx");
          }
          return false;
      }

}