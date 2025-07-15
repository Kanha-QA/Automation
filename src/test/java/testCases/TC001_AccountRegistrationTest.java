package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
		
	
	@Test(groups = {"Regression", "Master"})
	public void verify_account_registration()
	{
		logger.info("****** Starting TC001_AccountRegistrationTest *******");
		try
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		
		logger.info("Clicked on MyAccount Link....");
		
		hp.clickRegister();
		logger.info("Clicked on Register Link....");
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		//regpage.setFirstName("john");
		//regpage.setLastName("Cena");
		logger.info("Prividing customer details....");
		
		regpage.setFirstName(randomString());
		regpage.setLastName(randomString());
		
		//regpage.setEmail("wrestlerjohncena@gmail.com");
		regpage.setEmail(randomString()+"@gmail.com");
		
		//regpage.setTelephone("123456789");
		regpage.setTelephone(randomNumeric());
		
		//regpage.setPassword("12345");
		//regpage.setConfirmPassword("12345");
		
		String password = randomAlphaNumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message....");
		String conmsg = regpage.getConfirmationMsg();
		if(conmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed...");
			logger.debug("Debud logs");
			Assert.assertTrue(false);
		}
		
	//	Assert.assertEquals(conmsg, "Your Account Has Been Created!");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("****** Finished TC001_AccountRegistrationTest *******");		
	}
	
}
