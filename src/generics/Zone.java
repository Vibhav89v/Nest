package generics;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Zone 
{
	public static void main(String[] args)
	{
		Date date = new Date();
		Date currentDate = Calendar.getInstance(TimeZone.getDefault()).getTime();
		System.out.println("Date and time in India: " + currentDate);
		currentDate.toString();
		final String pubDate = "2017-07-11T12:08:56.235Z";
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date d = f.parse(pubDate, new ParsePosition(0));
		f.setTimeZone(TimeZone.getTimeZone("UTC"));
		System.out.println(f. getTimeZone().getID() + "\t" + f.format(d));
		//f.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		//System.out.println(f. getTimeZone().getID() + "\t" + f.format(d));
		
		
		//DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzz yyyy");
		//LocalDateTime dateTime = LocalDateTime.parse(st, ft);
		//LocalDateTime dateTime=LocalDateTime.now(defaultZoneId);
		//System.out.println("DateTime="+dateTime);
	
   	/*TimeZone timeZone1 = TimeZone.getTimeZone("America/Los_Angeles");
	
	Calendar calendar = new GregorianCalendar();
	long timeCPH = calendar.getTimeInMillis();
	System.out.println("timeCPH  = " + timeCPH);
	System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));
	calendar.setTimeZone(timeZone1);
	long timeLA = calendar.getTimeInMillis();
	System.out.println("timeLA   = " + timeLA);
	System.out.println("hour     = " + calendar.get(Calendar.HOUR_OF_DAY));
	*/
	}
}
