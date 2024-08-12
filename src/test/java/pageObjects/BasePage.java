package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


//Basepage is the parent of all the page object class
//instead of create the constructor to all page object class create a Basepage & add construcotr to it
//Basepage is reuseable component of all the page object class
public class BasePage 
{
	
	WebDriver driver;
	
	public BasePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

}
