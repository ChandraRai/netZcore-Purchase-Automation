package com.itcsystems.www;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class TestApplication {
	
	WebDriver driver;
	ExtentReports report;
	ExtentTest logger;
		
	@Parameters({"location", "webDriver", "url"})
	@BeforeTest(groups = {"Initialization", "TestAll"})
	public void initializeChrome(String location, String webDriver, String url) {
		
		report = new ExtentReports(location);
		report.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));	
		logger = report.startTest("Initialize Chrome");
		
		//set chrome driver and open page
		System.setProperty("webdriver.chrome.driver", webDriver);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		logger.log(LogStatus.PASS, "Chrome browser Started.");		
		driver.get(url);
		logger.log(LogStatus.PASS, "Web address inserted.");		
		logger.log(LogStatus.INFO, "Localhost server is running.");		
		//report.flush();
	}
	
	//Login test
	@Parameters({"cardNumber", "password"})
	@Test(priority=1, groups = {"Initialization", "TestAll"})
	public void validateLoginPage(String cardNumber, String password) {
		
		//Wait for last event to settle
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Event started
		logger = report.startTest("LOGIN");
				
		//Entering values in the textbox
		WebElement c = driver.findElement(By.xpath("//a[@id='ctl00_lgnView_cpLogin_lnkStudentWeb']"));
		c.click();
		WebElement a = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpLogin_lgnLogin_UserName']"));
		a.sendKeys(cardNumber);	
		WebElement element = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpLogin_lgnLogin_Password']"));
		element.sendKeys(password);		
		logger.log(LogStatus.PASS, "User name and password completed!");
		
		WebElement b = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpLogin_lgnLogin_btnLogin']"));
		b.click();		
		logger.log(LogStatus.PASS, "netZcore Purchase login successful.");
		//report.flush();
	}
	
	//Check page title
	@Test(priority=2, groups = {"UX", "checkPageTitle", "TestAll"})
	public void checkPageTitle() {
		
		//Event started
		logger = report.startTest("Title check");
		logger.log(LogStatus.INFO, "Check Page title");
		String title = driver.getTitle();
		//System.out.println("Page title:"+title);
		Assert.assertTrue(title.contains("ITC Systems - netZcore Purchase"));
		logger.log(LogStatus.PASS, "Title check completed.");
		//report.flush();
			
		}

	//Check Account summary
	@Test(priority=3, groups = {"AccountSummary", "TestAll"},  alwaysRun = true)
	public void checkAccountSummary() {
		
		//Wait for previous event to settle
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		//Event started
		logger = report.startTest("ACCOUNT SUMMARY");			
		WebElement c = driver.findElement(By.xpath("//a[@title='Account summary']"));
		c.click();		
		logger.log(LogStatus.PASS, "Account summary");
		//report.flush();
	}
	
	//Check Transaction History
	@Test(priority = 4, groups = {"Transaction", "checkTransactionHistory", "TestAll"})
	public void checkTransactionHistory() {
		
		//Wait for previous event to settle
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Event started
		logger = report.startTest("TRANSACTION HISTORY");		
		WebElement c = driver.findElement(By.xpath("//a[@title='Transaction history']"));
		c.click();		
		logger.log(LogStatus.PASS, "Transaction History");
		//report.flush();
	}
	
	//Add money functionality test
	@Parameters({"filePath"})
	@Test(priority=8, groups = {"Transaction", "TestAll", "addMoney", "ValidatePaymentProcessor"}, alwaysRun = true)
	public void addMoney(String filePath) throws InterruptedException, IOException {
		
		//Event started
		logger = report.startTest("ADD MONEY");		
		//report.flush();
		
		WebElement d = driver.findElement(By.xpath("//a[@title='Add Money']"));
		d.click();
		report.flush();
				
				
		ReadFile rd = new ReadFile();		
		if(rd.hostedPaypage(filePath) == 1) {
			
		//Wait for previous event to settle
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement e = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpMain_txtAmount']"));
		e.sendKeys("1");	
		logger.log(LogStatus.PASS, "Amount added.");
		//report.flush();
				
		WebElement f = driver.findElement(By.xpath("//div[@id='ctl00_lgnView_cpMain_pnlPaymentMethod']//span[1]"));
		f.click();
		logger.log(LogStatus.PASS, "Payment method selected.");
		//report.flush();
		
		WebElement g = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpMain_btnNext']"));
		g.click();	
		WebElement h = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_ctl01_btnNext\']"));
		h.click();
		logger.log(LogStatus.PASS, "Next button clicked.");
		//report.flush();
		
		WebElement i = driver.findElement(By.xpath("//input[@id=\"cc_radio\"]"));
		i.click();
		logger.log(LogStatus.PASS, "Radio button clicked.");
		
		WebElement j = driver.findElement(By.xpath("//input[@id=\'cardholder\']"));
		j.sendKeys("Nikhil");
		//logger.log(LogStatus.INFO, "Credit card holder name entered.");
		
		//logger.log(LogStatus.INFO, "Credit card number entered.");
		WebElement k = driver.findElement(By.xpath("//input[@id=\'pan\']"));
		k.sendKeys("4030000010001234");
		
		//logger.log(LogStatus.INFO, "Expiry date entered.");
		WebElement l = driver.findElement(By.xpath("//input[@id=\'exp_date\']"));
		l.sendKeys("0121");
		logger.log(LogStatus.INFO, "Credit card info entered.");		
		
		WebElement m = driver.findElement(By.xpath("//input[@id=\'buttonProcessCC\']"));
		m.click();	
		logger.log(LogStatus.PASS, "Process transaction.");	
		}		
		
		if(rd.hostedPaypage(filePath) == 4) {
			
			//Wait for previous event to settle
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
			
			
			//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			WebElement e = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpMain_txtAmount']"));
			e.sendKeys("1");	
			logger.log(LogStatus.PASS, "Amount added.");
			report.flush();
					
			WebElement f = driver.findElement(By.xpath("//div[@id='ctl00_lgnView_cpMain_pnlPaymentMethod']//span[1]"));
			f.click();
			logger.log(LogStatus.PASS, "Payment method selected.");
			report.flush();
			
			WebElement g = driver.findElement(By.xpath("//input[@id='ctl00_lgnView_cpMain_btnNext']"));
			g.click();	
			WebElement h = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_ctl01_btnNext\']"));
			h.click();
			logger.log(LogStatus.PASS, "Next button clicked.");
			report.flush();
			
			//Wait for previous event to settle
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
			WebElement i = driver.findElement(By.xpath("//input[@name=\'trnCardOwner\']"));
			i.sendKeys("Nikhil");
						
			WebElement j = driver.findElement(By.xpath("//input[@name=\'trnCardNumber\']"));
			j.sendKeys("4030000010001234");
						
			WebElement k = driver.findElement(By.xpath("//input[@name=\'trnCardCvd\']"));
			k.sendKeys("123");
			logger.log(LogStatus.PASS, "Credit card info entered.");
			report.flush();		
			
			WebElement l = driver.findElement(By.xpath("//input[@name=\'submitButton\']"));
			l.click();
			logger.log(LogStatus.PASS, "Card info submitted.");
			report.flush();
		}
			
	}
	
	//Purchase Mealplan test
	@Parameters({"filePath"})
	@Test(priority=10, groups = {"Transaction", "TestAll", "purchaseMealplan", "ValidatePaymentProcessor"})
	public void purchaseMealplan(String filePath) throws InterruptedException, IOException {
				
		//Wait for last event to settle
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {			
			e1.printStackTrace();
		}
		
		//Purchase Mealplan started
		logger = report.startTest("MEAL PLANS");			
		WebElement d = driver.findElement(By.xpath("//a[@title='Meal Plans']"));
		d.click();
		logger.log(LogStatus.PASS, "Meal plan selected.");
		report.flush();
		
		//driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement e = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_lvPlans_ctrl0_btnSelect\']"));
		e.click();
		logger.log(LogStatus.PASS, "Meal plan item selected.");
		report.flush();
		
		//check if mealplan is already present.
		if(isAlertPresent()) {		
			
			//if mealplan is already present
			if(driver.switchTo().alert().getText().contains("Selected plan already purchased.")) {
			   
			   //alert.accept();
			   driver.switchTo().alert().accept();
			   logger.log(LogStatus.PASS, "Selected plan already purchased.");			   
			} 
		
		} else {
			
			ReadFile rd = new ReadFile();		
			if(rd.hostedPaypage(filePath) == 1) {
					
			WebElement h = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_ctl01_btnNext\']"));
			h.click();
			logger.log(LogStatus.PASS, "Next button clicked.");	
			report.flush();			
			
			WebElement i = driver.findElement(By.xpath("//input[@id=\"cc_radio\"]"));
			i.click();
			logger.log(LogStatus.PASS, "Visa option selected.");	
			report.flush();			
			
			WebElement j = driver.findElement(By.xpath("//input[@id=\'cardholder\']"));
			j.sendKeys("Nikhil");			
			
			WebElement k = driver.findElement(By.xpath("//input[@id=\'pan\']"));
			k.sendKeys("4030000010001234");
			
			WebElement l = driver.findElement(By.xpath("//input[@id=\'exp_date\']"));
			l.sendKeys("0121");
			logger.log(LogStatus.PASS, "Card information entered.");	
			report.flush();
						
			WebElement m = driver.findElement(By.xpath("//input[@id=\'buttonProcessCC\']"));
			m.click();	
			logger.log(LogStatus.PASS, "Process transaction.");	
			}
			
			if(rd.hostedPaypage(filePath) == 4) {
				WebElement h = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_ctl01_btnNext\']"));
				h.click();
				logger.log(LogStatus.PASS, "Next button clicked.");	
				report.flush();	
				
				WebElement i = driver.findElement(By.xpath("//input[@name=\'trnCardOwner\']"));
				i.sendKeys("Nikhil");
							
				WebElement j = driver.findElement(By.xpath("//input[@name=\'trnCardNumber\']"));
				j.sendKeys("4030000010001234");
							
				WebElement k = driver.findElement(By.xpath("//input[@name=\'trnCardCvd\']"));
				k.sendKeys("123");
				logger.log(LogStatus.PASS, "Credit card info entered.");
				report.flush();		
				
				WebElement l = driver.findElement(By.xpath("//input[@name=\'submitButton\']"));
				l.click();
				logger.log(LogStatus.PASS, "Card info submitted.");
				report.flush();
			}
		}
	}	

	//Parking plan
	@Test(priority=11, groups = {"Transaction", "TestAll", "purchaseParkingPlan", "ValidatePaymentProcessor"})
	public void purchaseParkingPlan() {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		logger = report.startTest("PARKING");		
		WebElement c = driver.findElement(By.xpath("//a[@title='Parking']"));
		c.click();		
		logger.log(LogStatus.PASS, "Parking option selected.");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_lblMessage"));
		WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_lblError"));	
		
		if((e.getText().contains("There was an error retrieving Parking data."))) {
			
			WebElement m = driver.findElement(By.id("ctl00_lgnView_cpMain_lblError"));			
			logger.log(LogStatus.INFO, m.getText());
			//report.flush();
			
		} else {			
			WebElement a = driver.findElement(By.xpath("//input[@id=\"ctl00_lgnView_cpMain_rlstPlans_0\"]"));	
			a.click();
			logger.log(LogStatus.PASS, "Parking plan selected.");
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			WebElement b = driver.findElement(By.xpath("//input[@id=\"ctl00_lgnView_cpMain_txtAdd\"]"));	
			b.sendKeys("2");
			logger.log(LogStatus.PASS, "Parking pass number entered.");
			
			WebElement d = driver.findElement(By.xpath("//img[@id=\"ctl00_lgnView_cpMain_imgCreditCard\"]"));	
			d.click();
			logger.log(LogStatus.PASS, "Parking payment method selected.");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			WebElement f = driver.findElement(By.xpath("//input[@id=\"ctl00_lgnView_cpMain_btnNext\"]"));	
			f.click();
			logger.log(LogStatus.INFO, "Proceeded to credit card information.");			
				
			WebElement h = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_ctl01_btnNext\']"));
			h.click();
			logger.log(LogStatus.PASS, "Next button clicked.");
			
			WebElement i = driver.findElement(By.xpath("//input[@id=\"cc_radio\"]"));
			i.click();
			logger.log(LogStatus.PASS, "Radio button clicked.");
			
			WebElement j = driver.findElement(By.xpath("//input[@id=\'cardholder\']"));
			j.sendKeys("Nikhil");
			//logger.log(LogStatus.INFO, "Credit card holder name entered.");			
			
			WebElement k = driver.findElement(By.xpath("//input[@id=\'pan\']"));
			k.sendKeys("4030000010001234");
			
			//logger.log(LogStatus.INFO, "Expiry date entered.");
			WebElement l = driver.findElement(By.xpath("//input[@id=\'exp_date\']"));
			l.sendKeys("0121");
			logger.log(LogStatus.INFO, "Credit card info entered.");			
			
			WebElement m = driver.findElement(By.xpath("//input[@id=\'buttonProcessCC\']"));
			m.click();
			logger.log(LogStatus.PASS, "Process transaction.");
		}	
	}
	
	//Pay Fines
	@Test(priority=9, groups = {"payFines", "Transaction", "TestAll", "ValidatePaymentProcessor"})
	public void payFines() {
		
		logger = report.startTest("PAY FINES");		
		WebElement c = driver.findElement(By.xpath("//a[@title='Pay Fines']"));
		c.click();		
		//report.flush();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_lblMessage"));
		WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_chkLstFine"));	
		
		if((e.getText().contains("You have no pending fines."))) {
			
			WebElement m = driver.findElement(By.id("ctl00_lgnView_cpMain_chkLstFine"));			
			logger.log(LogStatus.INFO, m.getText());
			//report.flush();
			
		} else {
			
			WebElement n = driver.findElement(By.xpath("//input[@id=\"ctl00_lgnView_cpMain_chkLstFine_0\"]"));
			n.click();
			logger.log(LogStatus.PASS, "Payment Selected.");
			report.flush();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {				
				e1.printStackTrace();
			}
			
			WebElement f = driver.findElement(By.xpath("//div[@id=\'ctl00_lgnView_cpMain_pnlPaymentMethod\']//label[3]"));
			f.click();
			logger.log(LogStatus.PASS, "Payment processed.");
			report.flush();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {				
				e1.printStackTrace();
			}
			
			//Fines BUY/BACK options
			WebElement g = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_ctl01_btnBuy\']"));
			g.click();
			logger.log(LogStatus.PASS, "Buy button clicked.");
			report.flush();
			
			//Accept 
			driver.switchTo().alert().accept();	
			logger.log(LogStatus.PASS, "Confirmed.");			
			//report.flush();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {				
				e1.printStackTrace();
			}
			
			WebElement h = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_ctl01_btnNext\']"));
			h.click();
			logger.log(LogStatus.PASS, "Next button clicked.");	
			//report.flush();
			
			//logger.log(LogStatus.PASS, "Next button clicked.");
			WebElement i = driver.findElement(By.xpath("//input[@id=\"cc_radio\"]"));
			i.click();
			logger.log(LogStatus.PASS, "Visa option selected.");	
			report.flush();
			
			//logger.log(LogStatus.PASS, "Credit card holder name entered.");
			WebElement j = driver.findElement(By.xpath("//input[@id=\'cardholder\']"));
			j.sendKeys("Nikhil");
			
			//logger.log(LogStatus.PASS, "Credit card number entered.");
			WebElement k = driver.findElement(By.xpath("//input[@id=\'pan\']"));
			k.sendKeys("4030000010001234");
			
			//logger.log(LogStatus.PASS, "Expiry date entered.");
			WebElement l = driver.findElement(By.xpath("//input[@id=\'exp_date\']"));
			l.sendKeys("0121");
			logger.log(LogStatus.PASS, "Card information entered.");	
			report.flush();			
			
			WebElement m = driver.findElement(By.xpath("//input[@id=\'buttonProcessCC\']"));
			m.click();	
			logger.log(LogStatus.PASS, "Process transaction.");
			//report.flush();
		}	
	}	
	
	//Balance Refund
	@Test(priority=6, groups = {"Transaction", "TestAll", "validateBalanceRefund"})
	public void validateBalanceRefund() {
		
		logger = report.startTest("BALANCE REFUND");			
		WebElement c = driver.findElement(By.xpath("//a[@title='Balance Refund']"));
		c.click();
		
		//WebElement e = driver.findElement(By.id("ctl00_lgnView_cpMain_lblMessage"));
		WebElement e = driver.findElement(By.className("confirmMsg"));	
				
		if((e.getText().contains("No account eligible for a refund."))) {
			
			WebElement m = driver.findElement(By.id("ctl00_lgnView_cpMain_lblMessage"));			
			logger.log(LogStatus.INFO, m.getText());
			report.flush();
			
		} else {
			
			Select account = new Select(driver.findElement(By.xpath("//select[@id=\'ctl00_lgnView_cpMain_ddlAccount\']")));
			account.selectByValue("1");
			logger.log(LogStatus.PASS, "Account selected.");
			
			WebElement d = driver.findElement(By.xpath("//input[@id=\'ctl00_lgnView_cpMain_btnRefund\']"));
			d.click();
			logger.log(LogStatus.PASS, "Refund button clicked");	
				
			//Accept 
			driver.switchTo().alert().accept();	
			logger.log(LogStatus.PASS, "Confirm balance refund.");
			
			WebElement ms = driver.findElement(By.xpath("//span[@id=\"ctl00_lgnView_cpMain_lblMessage\"]"));			
			logger.log(LogStatus.INFO, ms.getText());
			//report.flush();
		}
	}
	
	//Balance Transfer
	@Test(priority=7, groups = {"Transaction", "TestAll", "validateBalanceTransfer"})
	public void validateBalanceTransfer() {
		logger = report.startTest("BALANCE TRANSFER");
		
		WebElement d = driver.findElement(By.xpath("//a[@title='Balance Transfer']"));
		d.click();
		logger.log(LogStatus.PASS, "Balance Transfer link clicked.");
		report.flush();
	}
	
	//Pay Fines
	@Test(priority=5, groups = {"checkTermsAndConditions", "TestAll"})
	public void checkTermsAndConditions() {
		
		logger = report.startTest("TERMS & CONDITIONS");		
		WebElement c = driver.findElement(By.xpath("//a[@title='Terms & Conditions']"));
		c.click();	
		
		logger.log(LogStatus.PASS, "Terms & Conditions validated.");
		//report.flush();			
	}	
		
	//Methods to take screenshot
	@AfterMethod(alwaysRun = true)
	public void getScreenshots(ITestResult result) {		

		if(result.getStatus() == ITestResult.FAILURE) {
			String screenshot_path = Screenshot.captureScreenshot(driver, result.getName());
			String image = logger.addScreenCapture(screenshot_path);
			logger.log(LogStatus.FAIL, "Screenshot", image);
		}
		
		report.flush();
	}	
	
	//Check if alert is present
	public boolean isAlertPresent() 
	{ 
	    try 
	    { 
	        driver.switchTo().alert(); 
	        return true; 
	    }    
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }   
	} 
		
}