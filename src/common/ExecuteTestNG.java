package common;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;

public class ExecuteTestNG
{
	public static Logger log;
	
	public static void main(String[] args) throws AddressException, MessagingException
	{
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		suites.add("./testng.xml");//path to xml..
	
		testng.setTestSuites(suites);
		testng.run();
	
		EmailAttachmentSender.sendEmailWithAttachments("smtp.gmail.com", "587", "nextoryautomation130@gmail.com", "nextory12345", "nextoryautomation130@gmail.com"," Report", "HTML, LOG reports and the Success Summary Report are attached in the mail", EmailAttachmentSender.attachFiles);
	
	
	}
}