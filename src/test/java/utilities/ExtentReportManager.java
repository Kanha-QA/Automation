package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext) {
		
	/*	SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.ss");
		Date dt = new Date();
		String currentDateTimeStamp = df.format(dt);
	 */
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());	// time stamp
		
		repName = " Test-Report " + timeStamp + " .html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);	//specify location of the report
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report"); 		//Title of report
		sparkReporter.config().setReportName("opencart Functional Testing");    //Name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub-Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environement", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includeGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includeGroups.toString());
		}	
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName()+" got sucessfully executed");
			
	}
	
	public void onTestFailure(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try
		{
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext context)
	{
		extent.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir")+".\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	/*	try
		{
			URL url = new URL("file:///"+System.getProperty("user.dir")+".\\reports\\"+repName);
			
			// Create the email message
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googleemail.com");	//keep changing
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("kanha@gmail.com", "password"));
			email.setSSLOnConnect(true);
			email.setFrom("kanha@gmail.com");	//sender
			email.setSubject("Test Result");
			email.setMsg("Please find attached report....");
			email.addTo("mahesh@gmail.com");	//receiver
			email.attach(url, "extent report", "please check report");
			email.send();
						
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
	}

}
