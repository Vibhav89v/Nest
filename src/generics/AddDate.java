package generics;

import java.text.DateFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

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
  
	{
		Date	date = dateFormat.parse(datestr);  
		return date;
	}

	public static String addingDays(int days)
	{
  
		Calendar c=new GregorianCalendar();
		c.add(Calendar.DATE, days);
		Date addedDate=c.getTime();
		String date=dateFormat.format(addedDate);
		System.out.println("Date after "+days+" days : " +date);
   
		return date;
	}
  
  
  
	public static long calculateNumberOfDays(String initDate, String finalDate)
	{
		 initDate=initDate.replaceAll("-", " ");
		 finalDate = finalDate.replaceAll("-", " ");
		 final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
		   		/*final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		   		final String firstInput = reader.readLine();
		        final String secondInput = reader.readLine();*/
		 final LocalDate firstDate = LocalDate.parse(initDate, formatter);
		 final LocalDate secondDate = LocalDate.parse(finalDate, formatter);
		 final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
		     //System.out.println("Days between: " + days);
		 return days;
    }
 
 public static void main(String[] args) throws IOException
 {
	calculateNumberOfDays("2016-07-31", "2017-07-31");
 }


}