package generics;

import java.io.File;
import java.io.FileInputStream;
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

public class Excel 
{
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		int value=getRowCount("./data/StatusCheck.xlsx","trial");
		System.out.println(value);
		String cellValue = getCellValue("./data/StatusCheck.xlsx","trial",20,1);
		System.out.println(cellValue);
		setExcelDataNest("./data/StatusCheck.xlsx","trial","2017-07-25", 23 ,45,  0,  0, 9, 8, 6,  4,  4,  3, 5);
		setExcelDataNest("./data/StatusCheck.xlsx","trial","2017-07-20", 23 ,45, 10, 10,19, 18,62, 65, 14, 32, 35);
		String sheetName = shiftingRowsUp("./data/StatusCheck.xlsx", "trial" , 21);
		System.out.println(sheetName);
	}
	
	
	
	
	public static int getRowCount(String path,String sheet)
	{
		int r=0;
		try
		{
			r=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getLastRowNum();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return r;
	}
	
	public static String getCellValue(String path,String sheet,int r,int c)
	{
		String v="";
		try
		{	
			CellType cs=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getRow(r).getCell(c).getCellTypeEnum();
			v=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getRow(r).getCell(c).toString();
			if(cs == CellType.NUMERIC)
			{
				//System.out.println("V:::"+ v);
				v = v.split("\\.")[0];
				//System.out.println("V:::"+ v);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return v;	
	}
	
	//FOR NEXTORY
	public static void setExcelDataNextory(String path, String sheet, int rowNum, int colNum,String data) 
			throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		FileInputStream fis = new FileInputStream(path);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheet);
	
			Row row = sh.getRow(rowNum);
		    Cell cel = row.createCell(colNum);
		    cel.setCellValue(data);

		
	    FileOutputStream fos = new FileOutputStream(path);
	    wb.write(fos);
	    wb.close();
	}
	
	//FOR NEST
	
	public static void setExcelDataNest(String path,String sheetName,String date, long countActive, long countDeleted, long countA_INACTIVE,long countP_INACTIVE,long countA_OMITTED, long countPARKED, long countL_INACTIVE,long countERROR,
			long countP_DEFERRED, long countUPCOMING, long countHIGH_PRICE) 
	{
       date= AddDate.currentDate();
        try
        {
        	String v="";
            FileInputStream inputStream = new FileInputStream(new File(path));
            Workbook workbook = WorkbookFactory.create(inputStream);
 
            Sheet sheet = workbook.getSheet(sheetName);
 
            Object[][] bookData = {
                    {date, countActive, countA_INACTIVE, countP_INACTIVE, countDeleted, countPARKED, countUPCOMING, countA_OMITTED, countDeleted , countL_INACTIVE , countERROR , countP_DEFERRED , countHIGH_PRICE },
//                    {"Software Craftmanship", "Pete McBreen", 26},
//                    {"The Art of Agile Development", "James Shore", 32},
//                    {"Continuous Delivery", "Jez Humble", 41},
            };
 
            int rowCount = sheet.getLastRowNum();
            
           
			v =WorkbookFactory.create(new FileInputStream(path)).getSheet(sheetName).getRow(rowCount).getCell(1).toString();

			for (Object[] aBook : bookData) 
			{
            	Row row;
            	
            	if(date.equals(v))
    			{
    				row = sheet.getRow(rowCount);
    			}
            	
            	else
            	{
            		row = sheet.createRow(++rowCount);
            	}
                
 
                int columnCount = 0;
                 
                Cell cell = row.createCell(columnCount);
                cell.setCellValue(rowCount);
                 
                for (Object field : aBook) 
                {
                    cell = row.createCell(++columnCount);
                    
                    if (field instanceof String) 
                    {
                        cell.setCellValue((String) field);
                    } 
                    else if (field instanceof Long)
                    {
                        cell.setCellValue((Long) field);
                    }
                    else if (field instanceof Integer)
                    {
                    	cell.setCellValue((Integer) field);
                    }
                }
 
            }
 
            inputStream.close();
 
            FileOutputStream outputStream = new FileOutputStream("./data/StatusCheck.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
             
        } catch (IOException | EncryptedDocumentException
                | InvalidFormatException ex) {
            ex.printStackTrace();
        }
    }
 

	
	public static String shiftingRowsUp(String path,String sheet,int r) 
	{
		try
		{	
			FileInputStream fis=new FileInputStream(path);
			Workbook wb= WorkbookFactory.create(fis);
			Sheet sh=wb.getSheet(sheet);
			int lastRowNum = sh.getLastRowNum();
		    if (r >= 0 && r < lastRowNum) 
		    {
		        sh.shiftRows(r + 1, lastRowNum, -1);
		    }
		    if (r == lastRowNum) 
		    {
		        Row removingRow = sh.getRow(r);
		        if (removingRow != null)
		        {
		            sh.removeRow(removingRow);
		        }
		    }
		    FileOutputStream fos = new FileOutputStream(path);
		    wb.write(fos);
		    fos.flush();
		    fos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sheet;
		
		
	}
	
	public static String shiftingRowsDown(String path,String sheet,int r)
	{

		try
		{	
			FileInputStream fis=new FileInputStream(path);
			Workbook wb= WorkbookFactory.create(fis);
			Sheet sh=wb.getSheet(sheet);
			int lastRowNum = sh.getLastRowNum();
			sh.shiftRows(r, lastRowNum, 1);
			sh.createRow(2);	
		    FileOutputStream fos = new FileOutputStream(path);
		    wb.write(fos);
		    fos.flush();
		    fos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sheet;
	
	}
	
	public static double comparingCells(String path, String sheetName, int colNum)
	{
		double diff=0;
		try
		
		{
			Sheet sheet=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheetName);
			int row= sheet.getLastRowNum();
			
			String topRow=(String) sheet.getRow(row-1).getCell(colNum).toString();
			String lowRow =(String) sheet.getRow(row).getCell(colNum).toString();
			
			double top= Double.parseDouble(topRow);
			double low= Double.parseDouble(lowRow);
			
			diff= low-top;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return diff;
		
	}
	
}



