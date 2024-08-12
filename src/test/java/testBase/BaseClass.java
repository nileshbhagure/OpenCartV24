package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; ////log4j
import org.apache.logging.log4j.Logger;   //log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;         //for paraellel testing

//This class is base class for all the test method
//Base class will contain all the reusable method
public class BaseClass 
{
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	  
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})    //for parallel testing passing parameter from master.xml
	public void setup(String os, String br) throws IOException
	{
		//loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		
		//LOG4j2
		logger=LogManager.getLogger(this.getClass());  //get the current classs logger. This stmt load logger to xml file
		
		//executing the TC using Grid- if the parameter is remote
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capablities=new DesiredCapabilities();
			
			//os- taking from XML file
			if(os.equalsIgnoreCase("windows"))
			{
				capablities.setPlatform(Platform.WIN11);	
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capablities.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("Linux"))
			{
				capablities.setPlatform(Platform.LINUX);
			}
			else
			{
				System.out.println("No matching os");
			    return;
			}
			
			//browser- taking from XML file
			switch(br.toLowerCase())
			{
			case "chrome": capablities.setBrowserName("chrome"); break;
			case "edge": capablities.setBrowserName("MicrosoftEdge"); break;
			case "firefox": capablities.setBrowserName("firefox"); break;
            default: System.out.println("No matching browser"); return;
			}
			
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capablities);
	}
		
		//if executing env is local
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
	{
		switch(br.toLowerCase())
		{
		case "chrome": driver=new ChromeDriver(); break;
		case "firefox": driver=new FirefoxDriver(); break;
		case "edge": driver=new EdgeDriver(); break;
		default: System.out.println("Invalid browser name.."); return;
		}		
	}
			
		//executing the TC using Grid- if the parameter is local
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
	{
		
		switch(br.toLowerCase())
		{
		case "chrome": driver=new ChromeDriver(); break;
		case "firefox": driver=new FirefoxDriver(); break;
		case "edge": driver=new EdgeDriver(); break;
		default: System.out.println("Invalid browser name.."); return;    //return will terminate from entire block    
		}
	}
		driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL2"));   //reading url from properties file
		driver.manage().window().maximize();
	}
	
		@AfterClass(groups= {"Sanity","Regression","Master"})
		void tearDown()
		{
			driver.quit();
		}
	
		//user defined method to generate randome email id
		 public String randomeString()
		 {
			 //RandomStringUtils- prefefined java class. Its is not available in java utls pkg. Is available in commons.lang3 lib
			 
			 String generatestring=RandomStringUtils.randomAlphabetic(5);    //5 character to be generated randomly
		     return generatestring;
		 }
		 
		 public String randomeNumber()
		 {
			 
			 String generatenumber=RandomStringUtils.randomNumeric(10);   //10 digit number to be generated randomly
		     return generatenumber;
		 }
		 
		 public String randomeAlphaNumber()
		 {
			 String generatestring=RandomStringUtils.randomAlphabetic(5);    //5 character to be generated randomly
			 String generatenumber=RandomStringUtils.randomNumeric(10);   //10 digit number to be generated randomly
		     return (generatenumber+"@"+generatenumber);
		 }
		 
		 public String captureScreen(String tname) throws IOException {

				String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(0));
						
				TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
				File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
				
				String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
				File targetFile=new File(targetFilePath);
				
				sourceFile.renameTo(targetFile);
					
				return targetFilePath;   //attaching to the extend report

			}
			
	}

