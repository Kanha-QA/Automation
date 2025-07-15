package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{
	
	@Test(groups={"Sanity", "Master"})
	public void verify_Login()
	{
		logger.info("*******TC002_Login Test started*******");
		
		try
		{
		//HomePage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//MyAccountPage
		MyAccountPage map = new MyAccountPage(driver);
		boolean targetpage = map.isMyAccountPageExist();
		Assert.assertEquals(targetpage, true, "Login Failed");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("*******TC002_Login Test finished*******");
	}

}
