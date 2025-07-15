package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
/*
 Data is valid - Login Success - test pass - logout
 Data is valid - Login failed - test fail
 
 Data is invalid - login success - test fail - logout
 Data is invalid - login failed - test pass
 */
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginTest_DataDrivenTesting extends BaseClass{
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="DataDriven")
	public void verify_Login_DDT(String email, String pwd, String exp_res)
	{
		logger.info("*******TC003_LoginTest_DataDrivenTesting Test started*******");
		
		try
		{
		
		//HomePage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//MyAccountPage
		MyAccountPage map = new MyAccountPage(driver);
		boolean targetPage = map.isMyAccountPageExist();
		
		/* Data is valid - Login success - test pass - logout
		 					Login failed - test fail
		 					
		   Data is invalid - Login success - test fail - logout
		 					Login failed - test pass
		*/
		
		if(exp_res.equalsIgnoreCase("valid"))
		{
			if(targetPage==true)
			{
				map.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		
		if(exp_res.equalsIgnoreCase("invalid"))
		{
			if(targetPage==true)
			{
				map.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
	
		logger.info("*******TC003_LoginTest_DataDrivenTesting Test finished*******");
	}
	
}
