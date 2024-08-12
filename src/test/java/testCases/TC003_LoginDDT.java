package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utlilities.DataProviders;

public class TC003_LoginDDT extends BaseClass

{
	                                //path of the dataprovider class which we need to import
    @Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="DataDriven")   //getting dataprovider from another class
	public void verify_loginDDT(String email, String pwd, String exp)
	{
    	logger.info("**** starting TC003_LoginDDT****");
    	
    	try
       {	
		//Home Page
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login Page
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(email);
	    lp.setPassword(pwd);
	    lp.clickLogin();
	    
	    //MyAccount Page
	    MyAccountPage macc=new MyAccountPage(driver);
	    boolean targetPage=macc.isMyAccountPageExists();
	
	    /*Data is valid  - login success - test pass  - logout
	     				   login failed - test fail

	    Data is invalid - login success - test fail  - logout
	                      login failed - test pass
	    */
	
	if(exp.equalsIgnoreCase("Valid"))
	{
		if(targetPage==true)                //login is successfull
		{
		   macc.clickLogout();
		   Assert.assertTrue(true);   	
		}
		else
		{
			Assert.assertTrue(false);
		}
	}
		
	
	if(exp.equalsIgnoreCase("Invalid"))
	{
		if(targetPage==true)                //login is successfull
		{
		   macc.clickLogout();
		   Assert.assertTrue(false);   	
		}
		else
		{
			Assert.assertTrue(true);
		}
	}
	
    }catch(Exception e)
    	{
    	 Assert.fail();
    	}
	logger.info("**** finished TC003_LoginDDT****");

	}
}
