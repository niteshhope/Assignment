package LearningSelenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class STP_Assingment3 {
	String siteUrl;
	String username;
	String password;
	String taxID;
	String invoice_item;
	String broswer;
	public static WebDriver driver;

	@BeforeTest
	public void InputData() {
		siteUrl = "https://acme-test.uipath.com/account/login";
		username = "kumar.testleaf@gmail.com";
		password = "leaf@12";
		taxID = "FR121212";
		invoice_item = "IT Support";
		broswer = "chrome";

	}

	@BeforeMethod
	public void init() {
		if (broswer.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (broswer.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();

		}
		driver.get(siteUrl);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void AMCETest() {
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("buttonLogin")).click();

		Actions builder = new Actions(driver);
		// Mouse hover over Invoices tab
		WebElement Invoices = driver.findElement(By.xpath("(//button[@class='btn btn-default btn-lg'])[5]"));
		builder.moveToElement(Invoices).perform();
		// Click on Search for Invoices
		WebElement SearchForInvoices = driver.findElement(By.linkText("Search for Invoice"));
		builder.click(SearchForInvoices).perform();

		// enter Tax ID and search
		driver.findElement(By.id("vendorTaxID")).sendKeys(taxID);
		driver.findElement(By.id("buttonSearch")).click();
		WebElement table = driver.findElement(By.xpath("//table"));
		List<WebElement> invoiceNo = table
				.findElements(By.xpath("//td[text()='" + invoice_item + "']/preceding::td[2]"));
		for (WebElement Invoice : invoiceNo) {
			System.out.println(Invoice.getText() + "-" + invoice_item);
		}

	}

	@AfterMethod
	public void logout() {
		driver.findElement(By.linkText("Log Out")).click();
	}

	@AfterSuite
	public void closeAllBrowser() {
		driver.quit();
	}
}
