package generics;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
//Monday,July 10,2017
public class Zone 
{
	public static void main(String[] args)
	{
	/*	Date date = new Date();
		Date currentDate = Calendar.getInstance(TimeZone.getDefault()).getTime();
		System.out.println("Date and time in India: " + currentDate);*/
		
		//ZoneId defaultZoneId = ZoneId.of(ZoneId.SHORT_IDS.get("ECT"));
		//ZoneId g=ZoneId.of(defaultZoneId);
		//String st = "Tue May 02 10:30:10 IST 2017";
		//DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzz yyyy");
		//LocalDateTime dateTime = LocalDateTime.parse(st, ft);
		//LocalDateTime dateTime=LocalDateTime.now(defaultZoneId);
		//System.out.println("DateTime="+dateTime);
		Date date=new Date();
		Date currentDate = Calendar.getInstance(TimeZone.getDefault()).getTime();
		//Date zoneId=
		//ZoneId zone;
   	/*TimeZone timeZone1 = TimeZone.getTimeZone("America/Los_Angeles");
	TimeZone timeZone2 = TimeZone.getTimeZone("Europe/Copenhagen");
	Calendar calendar = new GregorianCalendar();
	long timeCPH = calendar.getTimeInMillis();
	System.out.println("timeCPH  = " + timeCPH);
	System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));
	calendar.setTimeZone(timeZone1);
	long timeLA = calendar.getTimeInMillis();
	System.out.println("timeLA   = " + timeLA);
	System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));
	*/
		
   /* Date date = new Date();	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	// Use Madrid's time zone to format the date in
	df.setTimeZone(TimeZone.getDefault());
	System.out.println(TimeZone.getDefault());
	df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
	System.out.println(TimeZone.getTimeZone("Europe/Madrid"));*/
	}
}
