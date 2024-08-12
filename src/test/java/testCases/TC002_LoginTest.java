package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;


public class TC002_LoginTest extends BaseClass {

	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
		logger.info("**** starting TC002_LoginTest ****");
		try
		{
		//Home Page
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login Page
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
	    lp.setPassword(p.getProperty("password"));
	    lp.clickLogin();
	    
	    //MyAccount Page
	    MyAccountPage macc=new MyAccountPage(driver);
	    boolean targetPage=macc.isMyAccountPageExists();
	
	    Assert.assertTrue(targetPage);
	   // Assert.assertEquals(targetPage, true,"Login Failed");
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("**** finished TC002_LoginTest ****");

	}
}
