package generics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddDate 
{
 static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

 
 public static String currentDate()
  {
   Date Date = new Date();
   String currentDate = dateFormat.format(Date); 
   //System.out.println(currentDate);               //2016-11-16 
   return currentDate;
  }
  
public static Date currentStringToDate(String datestr) throws ParseException
  
  { // TODO Auto-generated method stub
	Date	date = dateFormat.parse(datestr);  
   return date;
  }

  public static String addingDays(int days)
  {
  
   Calendar c=new GregorianCalendar();
   c.add(Calendar.DATE, days);
   Date addedDate=c.getTime();
   String date=dateFormat.format(addedDate);
   System.out.println("Date after adding the "+days+" days : " +date);
   
   return date;
  }
 
 public static void main(String[] args)
 {
  currentDate();
  int days = (int) Math.round(23.97590361445783);
  addingDays(days);
  System.out.println("Number of days to add: " +days);
 }

}