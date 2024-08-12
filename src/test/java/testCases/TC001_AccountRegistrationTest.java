package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegisterationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{
  
	@Test(groups={"Regression","Master"})
	public void verify_account_registastion()
	{
		logger.info("***Starting TC001_AccountRegistrationTest ***");
	try
	{
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("***Clicked on MyAccount Link ***");
		
		hp.clickRegister();
		logger.info("***Clicked on Register Link ***");
		
		AccountRegisterationPage ar=new AccountRegisterationPage(driver);
		
		logger.info("***providing customer details ***");
		ar.setFirstName(randomeString().toUpperCase());
		ar.setLastName(randomeString().toUpperCase());
		
		//calling the userdefined randomestring() method
		ar.setEmail(randomeString()+"@gmail.com");   //random generate the email
		ar.setTelephone(randomeNumber());
	   
		String password=randomeAlphaNumber();
		ar.setPassword(password);
	    ar.setConfirmPassword(password);
	    
	    ar.setPrivacyPolicy();
	    ar.clickContinue();
	    
		logger.info("***Validating expected message ***");
	    String confmsg=ar.getConfirmationMsg();
	   
	    if(confmsg.equals("Your Account Has Been Created!"))
	    {
	      Assert.assertTrue(true);
	    }
	    else
	    {
	    	logger.error("Test failed..."); //logs the error level log
	    	logger.debug("Debug logs..."); //logs the debug level log
	        Assert.assertTrue(false);
	    }
	}
	
	catch(Exception e)
	{
		Assert.fail();
	}
	logger.info("***Finished TC001_AccountRegistrationTest***");

	}
	
	
}
