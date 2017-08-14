import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelDataFetching 
{
	public static int getRowCount(String filepath,String sheet) 
	{
		int rowNum=0;
		try
		{
			rowNum= WorkbookFactory.create(new FileInputStream(filepath)).getSheet(sheet).getLastRowNum();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rowNum;
	}
	
	public static String getCellData(String filepath,String sheet,int rowNum,int colNum)
	{
		String v = "";
		try
		{
			CellType cs = WorkbookFactory.create(new FileInputStream(filepath)).getSheet(sheet).getRow(rowNum).getCell(colNum).getCellTypeEnum();
			v = WorkbookFactory.create(new FileInputStream(filepath)).getSheet(sheet).getRow(rowNum).getCell(colNum).toString();
			if(cs == CellType.NUMERIC)
				v =	v.split("\\.")[0];
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return v;
	}
	
	public static void setExcelData(String filepath, String sheet, int r,int c,String data)
	{
		try
		{
			FileInputStream fis = new FileInputStream(filepath);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheet);
			Row row = s.getRow(r);
			Cell cell = row.createCell(c);
			cell.setCellValue(data);
			
			FileOutputStream fos = new FileOutputStream(filepath);
			wb.write(fos);
			wb.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	 public static String shiftingRowsUp(String filepath,String sheetName,int rowNum)
	 {
		 try
		 {
			 FileInputStream fis = new FileInputStream(filepath);
			 Workbook wb = WorkbookFactory.create(fis);
			 Sheet sh= wb.getSheet(sheetName);
			 int lastRow = sh.getLastRowNum();
			 if(rowNum > 0 && rowNum < lastRow)
				 sh.shiftRows(rowNum+1, lastRow, -1);
			 if(rowNum == lastRow)
			 {
				 Row removingRow = sh.getRow(rowNum);
				 if(removingRow !=null)
					 sh.removeRow(removingRow);
			 }
			 FileOutputStream fos = new FileOutputStream(filepath);
			 wb.write(fos);
			 fos.flush();
			 fos.close();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return sheetName;
	 }
}