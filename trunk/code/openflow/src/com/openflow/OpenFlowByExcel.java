package com.openflow;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class OpenFlowByExcel {
	

	// 将分享的中奖信息导入数据库
	private static void addByExcel() {
		OpenFlow flow = new OpenFlow();
		String excelPath = "/Users/ray/Desktop/150.xls";
		// 将数据从excel表中取出来
		String num = "";
		String area = "";
		try {
			FileInputStream fis = new FileInputStream(excelPath);
			
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				HSSFSheet sheet = wb.getSheetAt(i);
				for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext();) {
					Row r = rowIt.next();
					for (int j = 0; j < r.getLastCellNum(); j++) {
						Cell cell = r.getCell(j);
						String cellContent = "";

						// 如果是数字类型
						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							Double value = cell.getNumericCellValue();
							BigDecimal bd = new BigDecimal(value);
							cellContent = bd.toString();
						}
						// 如果是字符串类型
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							cellContent = cell.getStringCellValue();
						}
						if (cellContent != null && !cellContent.equals("")) {

							if (j == 0) {
								num = cellContent;
							}
							if (j == 1) {
								area = cellContent;
							}
						}

					}
					//System.out.println(num);
					flow.openflowBy150(num, area);
				}
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args){
		addByExcel();
	}
	
}