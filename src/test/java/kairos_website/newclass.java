package kairos_website;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Enums.Website_URLs;
import Reusable.JavaUtilities;
import io.github.bonigarcia.wdm.WebDriverManager;

public class newclass {

	WebDriver driver;
	 ExtentReports extent = new ExtentReports();
	    ExtentTest test;
	JavaUtilities javaUtilitis =new JavaUtilities(); 
	String URL;
	 String Title;
	 String DateandTime;
	@BeforeSuite
	public void setupExtentReports()
	{
		 ExtentSparkReporter spark = new ExtentSparkReporter("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\target\\surefire-reports\\emailable-report.html");
	        extent.attachReporter(spark);
	}
	@BeforeMethod
	public void checkURLsConnection()
	{
//		checkURLs();
	}
	@Test
	public void Websites() throws Throwable
	{
		WebDriverManager.edgedriver().setup();
		WebDriverManager.firefoxdriver().setup();
		WebDriverManager.chromedriver().setup();
//		driver=new ChromeDriver();
	
		String[] urls = {Website_URLs.Customer_Stories.getURL(),Website_URLs.Blog.getURL(),Website_URLs.Overview.getURL(),
				Website_URLs.Life_at_Kairos.getURL(),Website_URLs.Home_page.getURL(),Website_URLs.Products_KiTAP.getURL(),Website_URLs.DQGateway.getURL()
				,Website_URLs.API_Testing.getURL(),Website_URLs.Resources.getURL(),Website_URLs.Home_page_footer.getURL(),Website_URLs.Mobile_APP_Testing.getURL(),Website_URLs.Data_Analytics_Testing.getURL(),
				Website_URLs.Salesforce_Testing.getURL(),Website_URLs.Regression_Testing_Services.getURL(),Website_URLs.Cloud_Testing.getURL(),Website_URLs.Oracle_solutions.getURL(),Website_URLs.Application_Modernization.getURL(),
				Website_URLs.Intelligent_RPA.getURL(),Website_URLs.Digital_App_Development.getURL()};

        
        List<String> shuffledUrls = new ArrayList<>(List.of(urls));
        Collections.shuffle(shuffledUrls);

        List<UrlResult> urlResults = new ArrayList<>();
        // Limit the number of URLs to 3
        List<String> selectedUrls = shuffledUrls.subList(0, Math.min(shuffledUrls.size(), 3));
        StringBuilder htmlTable = new StringBuilder("<table border='1'><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status Code</th></tr>");

        for (String url : selectedUrls) {
            // Iterate through each browser (e.g., Chrome and Firefox)
            for (String browser : Arrays.asList("chrome", "edge","firefox")) {
                // Create a new instance of WebDriver based on the current browser
                WebDriver driver = null;
            	driver.manage().window().maximize();
                if (browser.equalsIgnoreCase("chrome")) {
                    driver = new ChromeDriver();
                }else if (browser.equalsIgnoreCase("edge")) {
                    driver = new EdgeDriver();
                }  
                else if (browser.equalsIgnoreCase("firefox")) {
                    driver = new FirefoxDriver();
                } else {
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
                }

                try {
                    // Create a test with the current URL and browser
                    test = extent.createTest("Test for URL: " + url + " in " + browser);

                    // Navigate to the URL using the current browser
                    driver.get(url);
                } finally {
                    // Close the WebDriver instance after the test
                  //  driver.quit();
                }}
            Thread.sleep(2000);
            WebElement acceptall = driver.findElement(By.xpath("//button[text() ='Accept All']"));
            if(acceptall.isDisplayed())
            {
	        acceptall.click();
            }
          
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Specify the destination folder and unique file name
            File destinationFolder = new File("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ScreenShots");
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }
            String fileName = "Kairos_website_"+ javaUtilitis.getSystemdate() + ".png";
            File destinationFile = new File(destinationFolder, fileName);
  // Copy the captured screenshot to the destination file
            FileUtils.copyFile(sourceFile, destinationFile);

                       // Create a new UrlResult object and add it to the list
//         StringBuilder htmlTable = new StringBuilder("<table border='1'><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status Code</th></tr>");
         String URL = driver.getCurrentUrl();
         String Title = driver.getTitle();
         String DateandTime = javaUtilitis.getSystemdate();
         Capabilities capabilities = ((ChromeDriver) driver).getCapabilities();
         String Browser = capabilities.getBrowserName();
         String BrowserVersion = capabilities.getBrowserVersion();
         Platform deviceos=capabilities.getPlatformName();

         // Append data to the HTML table
         htmlTable.append("<tr>");
         htmlTable.append("<td>").append("Lenovo Thinkpad").append("</td>");
         htmlTable.append("<td>").append(deviceos).append("</td>");
         htmlTable.append("<td>").append(Browser).append("</td>");
         htmlTable.append("<td>").append(BrowserVersion).append("</td>");
         htmlTable.append("<td>").append(URL).append("</td>");
         htmlTable.append("<td>").append(Title).append("</td>");
         htmlTable.append("<td>").append(DateandTime).append("</td>");
         htmlTable.append("<td>").append("Working").append("</td>");
        }
         // You can add more columns as needed
        
         // Close the table row
         htmlTable.append("</tr>");
        

         
          
             
             
	   
    for (UrlResult result : urlResults) {
        System.out.println(result.toString());
    }


     EmailAttachment attachment = new EmailAttachment();
	    attachment.setPath("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\test-output\\emailable-report.html");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Report of the test execution");
        attachment.setName("Reports");
   

        Email email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "oqzh ifii yklx zpgn"));
        email.setSSLOnConnect(true);
        email.setFrom("kmbb4578@gmail.com");
        email.setSubject("Kairos Websites Testing");
       
//        StringBuilder htmlTable = new StringBuilder("<table border='1'><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status Code</th></tr>");
//        StringBuilder htmlTable = new StringBuilder("<table border='1'><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status Code</th></tr>");
        StringBuilder emailContent = new StringBuilder("Hi Team.\r\nKairos Websites are Working Fine");
        emailContent.append(htmlTable);
		email.setContent("Hi Team.\r\nKairos Websites are Working Fine"+htmlTable ,"text/html");
        email.addTo("bhasha.k@kairostech.com");
        email.addTo("pranathi.g@kairostech.com");
        email.addTo("kmahaboobbhasha@gmail.com");
        email.addTo("mahaboobbhasha17@gmail.com");
       // email.addCc("sandhyarani.mandarapu@gmail.com");
        ((MultiPartEmail) email).attach(attachment);
        email.send();}
    

@AfterMethod
public void sendEmail() throws Throwable{

//	    EmailAttachment attachment = new EmailAttachment();
//	    attachment.setPath("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\test-output\\emailable-report.html");
//        attachment.setDisposition(EmailAttachment.ATTACHMENT);
//        attachment.setDescription("Report of the test execution");
//        attachment.setName("Reports");
//   
//
//        Email email = new MultiPartEmail();
//        email.setHostName("smtp.gmail.com");
//        email.setSmtpPort(587);
//        email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "oqzh ifii yklx zpgn"));
//        email.setSSLOnConnect(true);
//        email.setFrom("kmbb4578@gmail.com");
//        email.setSubject("Kairos Websites Testing");
//       
//        String emailBody = "<table border='\1\'><tr><th><b>Webpage:</b></th><td> "+Title+" </td>		  </tr>		  <tr>		    <th><b>URL:</b></th><td>"+ URL+" </td></tr>		  <tr><th><b>Date and Time:</b></th> <td>"+DateandTime+" </td></tr>		</table>";
//     
//		email.setContent("Hi Team.\r\nKairos Websites are Working Fine"+emailBody ,"text/html");
//        email.addTo("bhasha.k@kairostech.com");
//        email.addTo("pranathi.g@kairostech.com");
//        email.addTo("kmahaboobbhasha@gmail.com");
//        ((MultiPartEmail) email).attach(attachment);

//        email.send();
        System.out.println("mail sent");
        test = extent.createTest("Email Sending Test");
   //     test.log(Status.INFO, "Sending email with report attachment");
        // Example: Log a pass status for the email sending
        test.pass("Email sent successfully");

}
	
	

 public static void checkURLs() {
        String[] urls = {Website_URLs.Customer_Stories.getURL(),Website_URLs.Blog.getURL(),Website_URLs.Overview.getURL(),
				Website_URLs.Life_at_Kairos.getURL(),Website_URLs.Home_page.getURL(),Website_URLs.Products_KiTAP.getURL(),Website_URLs.DQGateway.getURL()
				,Website_URLs.API_Testing.getURL(),Website_URLs.Resources.getURL(),Website_URLs.Home_page_footer.getURL(),Website_URLs.Mobile_APP_Testing.getURL(),Website_URLs.Data_Analytics_Testing.getURL(),
				Website_URLs.Salesforce_Testing.getURL(),Website_URLs.Regression_Testing_Services.getURL(),Website_URLs.Cloud_Testing.getURL(),Website_URLs.Oracle_solutions.getURL(),Website_URLs.Application_Modernization.getURL(),
				Website_URLs.Intelligent_RPA.getURL(),Website_URLs.Digital_App_Development.getURL()
        };

        for (String url : urls) {
            try {
                URL urlObj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println(url + " is reachable.");
                } else {
                    System.out.println(url + " is not reachable. Response Code: " + responseCode);
                    // Handle the case when the URL is not reachable
                }

                connection.disconnect();
            } catch (Exception e) {
                System.out.println(url + " is not reachable. Exception: " + e.getMessage());
                // Handle the exception as needed
            }
        }
    }
@AfterSuite
public void teardownExtentreports()
{
	 driver.quit();
	extent.flush();
}
class UrlResult {
    private String device;
    private String deviceOS;
    private String browser;
    private String browserVersion;
    private String url;
    private String websitePageVerified;
    private String statusCode;
	private Date dateTime;

    public UrlResult(String device, String deviceOS, String browser, String browserVersion,
            String url, String websitePageVerified, String dateTime, String statusCode) {
this.device = device;
this.deviceOS = deviceOS;
this.browser = browser;
this.browserVersion = browserVersion;
this.url = url;
this.websitePageVerified = websitePageVerified;
//  this.dateTime = dateTime;
this.statusCode = statusCode;
}

//Override toString() for printing or logging
@Override
public String toString() {
return String.format("Device: %s, Device OS: %s, Browser: %s, Browser Version: %s, URL: %s, " +
       "Website Page Verified: %s, Date & Time: %s, Status Code: %s",
       device, deviceOS, browser, browserVersion, url, websitePageVerified, dateTime, statusCode);
}
}

}
