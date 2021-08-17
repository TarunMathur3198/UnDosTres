package frameworkSupportLibraries;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelDataUtils {

	// Method to extract data from excel sheet
	public static String getExcelData(String row,String columnName, String sheetName, String filepath) {
		String column = "", testcase = "";
		String value = "";
		double intvalue = 0;
		int i = 0, rowid =0;
		String intToString ="";
		org.apache.poi.ss.usermodel.Cell columnname = null;
		org.apache.poi.ss.usermodel.Cell rowName = null;
		try {
			File file = new File(filepath);
			FileInputStream inputStream = new FileInputStream(file);
			Workbook newWorkBook = null;
			String fileExtensionName = filepath.substring(filepath.indexOf("."));
			
			if(fileExtensionName.contentEquals(".xlsx")){
				newWorkBook = new XSSFWorkbook(inputStream);
				
			}
			else if (fileExtensionName.equals(".xls")) {
				newWorkBook = new HSSFWorkbook(inputStream);
			}
			
			org.apache.poi.ss.usermodel.Sheet newWorkSheet = newWorkBook.getSheet(sheetName);
			int k=0;
			int rowCount = newWorkSheet.getLastRowNum() - newWorkSheet.getFirstRowNum();
			
			for(k=0;k<=rowCount;k++) {
				Row currentRow = newWorkSheet.getRow(k);
				rowName = currentRow.getCell(0);
				testcase = rowName.getStringCellValue();
				
				if(testcase.equalsIgnoreCase(row)) {
					rowid = k;
					break;
				}
				
			}
			do {
				Row currentRow = newWorkSheet.getRow(0);
				columnname = currentRow.getCell(i);
				column = columnname.getStringCellValue();
				
				Row checkRow = newWorkSheet.getRow(rowid);
				org.apache.poi.ss.usermodel.Cell columnCheck = checkRow.getCell(i);
				if(column.equals(columnName))
				{
					if(columnCheck.getCellType() == CellType.NUMERIC) {
						intvalue = newWorkSheet.getRow(rowid).getCell(i).getNumericCellValue();
						int ab = (int) Math.round(intvalue);
						value = String.valueOf(ab);
						break;
					}
					else if(columnCheck.getCellType()== CellType.STRING) {
						
					
					value =  newWorkSheet.getRow(rowid).getCell(i).getStringCellValue();
					}
					 
				}
				i++;
			}while(!column.contentEquals(columnName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
