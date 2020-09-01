package com.xiami.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 */
public class ExcelUtil<T> {

    private final static String excel2003 = ".xls"; // 2003- 版本的excel
    private final static String excel2007 = ".xlsx"; // 2007+ 版本的excel


    /*
     * 导出大量数据
     */
    public static void exportSXSSF(String title, List<String> rowsHeader,
                                   List<Map<String, Object>> dataSet, OutputStream out)
            throws Exception {
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook(); // 创建工作簿对象
//            workbook.setForceFormulaRecalculation(true);
            SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(title); // 创建工作表

            sheet.setRandomAccessWindowSize(-1);
            // 产生表格标题行
            Row rowm = sheet.createRow(0);
            Cell cellTiltle = rowm.createCell(0);

            // sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
//            SXSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);// 获取列头样式对象
//            XSSFCellStyle style = getStyle(workbook); // 单元格样式对象

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowsHeader
                    .size() - 1)));
//            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);

            // 定义所需列数
            int columnNum = rowsHeader.size();
            Row rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)

            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                String headJson = rowsHeader.get(n);
                Map<String, Object> head = JSONUtils.jsonToMap(headJson);
                String headName = (String) head.get("name");
                Cell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
                XSSFRichTextString text = new XSSFRichTextString(headName);
                cellRowName.setCellValue(text); // 设置列头单元格的值
//                cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
            }

            // 将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataSet.size(); i++) {

                Map<String, Object> data = dataSet.get(i);// 遍历每个对象
                Row row = sheet.createRow(i + 3);// 创建所需的行数

                for (int j = 0; j < rowsHeader.size(); j++) {
                    String headStr = rowsHeader.get(j);
                    Map<String, Object> head = JSONUtils.jsonToMap(headStr);
                    String headKey = (String) head.get("key");
                    Object value = data.get(headKey);
                    Cell cell = null; // 设置单元格的数据类型
                    if (j == 0) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(i + 1);
                    } else {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (value != null && !"".equals(value)) {
                            cell.setCellValue(value.toString()); // 设置单元格的值
                        } else {
                            cell.setCellValue("");
                        }
                    }
//                    cell.setCellStyle(style); // 设置单元格样式
                }

                /*
                 * Set<String> keys = data.keySet(); Iterator<String> it =
                 * keys.iterator(); int j=0; while(it.hasNext()){ String key =
                 * it.next(); Object value = data.get(key); HSSFCell cell =
                 * null; // 设置单元格的数据类型 if(j==0){ cell = row.createCell(j,
                 * HSSFCell.CELL_TYPE_NUMERIC); cell.setCellValue(i + 1); }else{
                 * cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING); if
                 * (value != null && !"".equals(value)) {
                 * cell.setCellValue(value.toString()); // 设置单元格的值 }else{
                 * cell.setCellValue(""); } } cell.setCellStyle(style); //
                 * 设置单元格样式 j++; }
                 */

                /*
                 * for (int j = 0; j < obj.size(); j++) { HSSFCell cell = null;
                 * // 设置单元格的数据类型 if (j == 0) { cell = row.createCell(j,
                 * HSSFCell.CELL_TYPE_NUMERIC); cell.setCellValue(i + 1); } else
                 * { cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING); if
                 * (!"".equals(obj.get(j)) && obj.get(j) != null) {
                 * cell.setCellValue(obj.get(j).toString()); // 设置单元格的值 } }
                 * cell.setCellStyle(style); // 设置单元格样式 }
                 */
            }
            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    Row currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        Cell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue()
                                    .getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }

            if (workbook != null) {
                try {
                    workbook.write(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
