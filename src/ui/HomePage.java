package ui;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import common.AutomationConstants;
import generics.Property;

public class HomePage implements AutomationConstants
{
	public static String adminUrl;
	public static long timeout;
	
	public static void main(String[] args) 
	{
		adminUrl = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE,"ADMINURL");
		timeout=Long.parseLong(Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "IMPLICIT"));
		LoginPage.main(args);
	/*	System.setProperty(CHROME_KEY,DRIVER_PATH+CHROME_FILE);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		driver.get(adminUrl);*/
		
	}
}
