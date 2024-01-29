package kairos_website;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.*;
import org.apache.http.util.Asserts;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class CrossBrowsing {
    WebDriver driver = null;
    ExtentReports extent = new ExtentReports();
    ExtentTest test = null;

    @BeforeSuite
    public void setUpExtentReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("C:\\Users\\PranathiGoketi-Kairo\\Downloads\\CrossBrowsing\\report.html");
        extent.attachReporter(spark);
    }

  

    @Test
    public void testGoogle() throws InterruptedException, IOException {
  	  // Create a new Excel workbook
	   

	   
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
        test = extent.createTest("Test Google", "Verify Google website opens");
        driver.get("https://klabs.kairostech.com/");
       // Assert.assertEquals(url, strURL);
        test.log(Status.INFO, "Opened Google website");
        test.log(Status.PASS, "Test passed");
        Thread.sleep(3000);

        WebElement acceptall = driver.findElement(By.xpath("//button[text() ='Accept All']"));
        acceptall.click();
        Assert.assertTrue(acceptall.isEnabled(), "Accept All button should be enabled after clicking");
        test.log(Status.INFO, "Click on the Cookies");
        test.log(Status.PASS, "Test passed");


        String screenshotPath = "D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ScreenShots";
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(screenshotPath + "screenshot.png"));
    }

    @AfterMethod
    public void sendEmail() throws EmailException {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\kairos.xlsx");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Report of the test execution");
        attachment.setName("Excel");

        Email email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "oqzh ifii yklx zpgn"));
        email.setSSLOnConnect(true);
        email.setFrom("kmbb4578@gmail.com");
        email.setSubject("Selenium TestMail");
        email.setMsg("Good Evening!.\n\n This is a test mail from pranathi where I am sending to all simultaneously for testing of my code.\n Kindly ignore. \n\n With Best Regards,\n Pranathi");
        email.addTo("pranathi.g@kairostech.com");
        email.addTo("kmahaboobbhasha@gmail.com");
        ((MultiPartEmail) email).attach(attachment);

        email.send();
        System.out.println("mail sent");
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @AfterSuite
    public void teardownExtentReport() {
        extent.flush();
    }
}


