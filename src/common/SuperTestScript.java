package common;

import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Factory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import generics.DeleteFileFromFolder;
import generics.Property;



@Listeners(CustomListener.class)
	public class SuperTestScript implements AutomationConstants
	{

		public Logger log;
		public static WebDriver driver;
		public static String url;
		public static String un;
		public static String pwd;
		public static long timeout;
		public static boolean loginRequired=true;
		public static boolean logoutRequired=true;
		
		public SuperTestScript()
		{
			DeleteFileFromFolder.deleteFile("./result/result.html");
			DeleteFileFromFolder.deleteFile("./result/result.log");
			DeleteFileFromFolder.deleteFile("./test-output/emailable-report.html");
			log=Logger.getLogger(this.getClass());
			Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
			
		}
		
		@BeforeSuite
		public void initFramework()
		{	
			
			log.info("Initializing Framework");
//			url=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "URL");
//			un=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "UN");
//			pwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "PWD");
//			timeout=Long.parseLong(Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "IMPLICIT"));
		}
		@AfterSuite
		public void closeFramework() throws AddressException, MessagingException
		{
			
			driver= new ChromeDriver();
			driver.get("file:///D:/Nest/result/result.html");
			
			
			log.info("Closing Framework");
		}
		
		
		
		@Parameters({"browser"})
		
		@BeforeTest
		public void initBrowser(@Optional("chrome")String browser)
		{
			log.info("Execution Started on Browser: " +browser);
		}
		
		@Parameters({"browser"})
		
		@AfterTest
		public void closeBrowser(@Optional("chrome")String browser)
		{
			log.info("Execution completed on browser: " +browser);
		}
		
		@Parameters({"browser"})
		//@BeforeClass(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative", "GiftCardRuleMailer" , "ConfirmationsRuleMailerPositive", "ConfirmationsRuleMailerNegative" ,"CustomerEducationRuleMail", "Registrations", "All"})
		
		
	//	@AfterClass(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative", "GiftCardRuleMailer" , "ConfirmationsRuleMailerPositive","ConfirmationsRuleMailerNegative" , "CustomerEducationRuleMail", "Registrations", "All"})
		
		
		@BeforeMethod
		public void initApplication(@Optional("chrome") String browser)
		{
			log.info("Browser: " +browser);
			if(browser.equals("firefox"))
			{
				System.setProperty(GECKO_KEY, DRIVER_PATH+GECKO_FILE);
				driver= new FirefoxDriver();
				
			}
			
			else if(browser.equals("phantom"))
			{
				System.setProperty(PHANTOM_KEY, DRIVER_PATH+PHANTOM_FILE);
				driver= new PhantomJSDriver();
			}
			else
			{
				System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
				
			}
			
//			driver.manage().window().maximize();
//			driver.get(url);
//			driver.manage().deleteAllCookies();
//			log.info("Timeout:" +timeout);
//			driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		}
		
		@AfterMethod
		public void cleanApplication() throws InterruptedException
		{
//			Thread.sleep(5000);
//			driver.quit();
		}
		
		
		
	}


