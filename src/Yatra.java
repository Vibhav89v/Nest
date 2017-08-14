import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import common.AutomationConstants;
import generics.Property;

public class Yatra implements AutomationConstants
{
	public static WebDriver driver;
	public static long timeout;
	public static String yatraurl;
	
	public static void main(String[] args) 
	{
	    timeout = Long.parseLong(Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE,"IMPLICIT"));
	    yatraurl = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE,"YATRAURL");
	    System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
	    driver = new ChromeDriver();
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	    driver.get(yatraurl);
	    driver.findElement(By.xpath("//input[@id='BE_flight_depart_date']/following-sibling::i")).click();
	    driver.findElement(By.xpath("//div[@id='PegasusCal-0']//a[@id='a_2017_7_31']")).click();
	    driver.close();
	}
}
