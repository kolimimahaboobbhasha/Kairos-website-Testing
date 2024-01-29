package kairos_website;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Enums.Website_URLs;
import Reusable.JavaUtilities;
import io.github.bonigarcia.wdm.WebDriverManager;

public class reports {
	
	WebDriver driver;
    ExtentReports extent = new ExtentReports();
    ExtentTest test = null;
    JavaUtilities javaUtilities=new  JavaUtilities();

    @BeforeSuite
    public void setUpExtentReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ExtentReports");
        extent.attachReporter(spark);
    }

  

   @Test
    public void testGoogle() throws InterruptedException, IOException {
    	
    	 
	   checkURLs();
    	    // Create a new Excel workbook
    	    Workbook workbook = new XSSFWorkbook();

    	    // Create a new sheet in the workbook
    	    Sheet sheet = workbook.createSheet("Websites");

    	    // Create the header row
    	    Row headerRow = sheet.createRow(0);
    	    headerRow.createCell(0).setCellValue("URL");
    	    headerRow.createCell(1).setCellValue("Title");
    	    headerRow.createCell(2).setCellValue("Date and Time");

    	    int rowNum = 1; // Start from the second row

    		WebDriverManager.chromedriver().setup();
    		driver=new ChromeDriver();
    		driver.manage().window().maximize();
    		String[] urls = {Website_URLs.Customer_Stories.getURL(),Website_URLs.Blog.getURL(),Website_URLs.Overview.getURL(),
    				Website_URLs.Life_at_Kairos.getURL(),Website_URLs.Home_page.getURL(),Website_URLs.Products_KiTAP.getURL(),Website_URLs.DQGateway.getURL()
    				,Website_URLs.API_Testing.getURL(),Website_URLs.Resources.getURL(),Website_URLs.Home_page_footer.getURL(),Website_URLs.Mobile_APP_Testing.getURL(),Website_URLs.Data_Analytics_Testing.getURL(),
    				Website_URLs.Salesforce_Testing.getURL(),Website_URLs.Regression_Testing_Services.getURL(),Website_URLs.Cloud_Testing.getURL(),Website_URLs.Oracle_solutions.getURL(),Website_URLs.Application_Modernization.getURL(),
    				Website_URLs.Intelligent_RPA.getURL(),Website_URLs.Digital_App_Development.getURL()};

    	    // Shuffle the array of URLs randomly
    	    List<String> shuffledUrls = new ArrayList<>(List.of(urls));
    	    Collections.shuffle(shuffledUrls);

    	    // Limit the number of URLs to 3
    	    List<String> selectedUrls = shuffledUrls.subList(0, Math.min(shuffledUrls.size(), 3));

    	    for (String url : selectedUrls) {
    	    	   test = extent.createTest("Test for URL: " + url);
    	        driver.get(url);
    	        Thread.sleep(2000);

    	        WebElement acceptall = driver.findElement(By.xpath("//button[text() ='Accept All']"));
    	        if (acceptall.isDisplayed()) {
    	            acceptall.click();
    	        }

    	        TakesScreenshot screenshot = (TakesScreenshot) driver;
    	        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

    	        File destinationFolder = new File("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ScreenShots");
    	        if (!destinationFolder.exists()) {
    	            destinationFolder.mkdirs();
    	        }
    	        String fileName = "Kairos_website_" + javaUtilities.getSystemdate() + ".png";
    	        File destinationFile = new File(destinationFolder, fileName);
    	        FileUtils.copyFile(sourceFile, destinationFile);

    	        String URL = driver.getCurrentUrl();
    	        String Title = driver.getTitle();
    	        String DateandTime = javaUtilities.getSystemdate();

    	        System.out.println(driver.getTitle() + "    URL :" + driver.getCurrentUrl());

    	        // Write data to Excel
    	        Row dataRow = sheet.createRow(rowNum++);
    	        dataRow.createCell(0).setCellValue(URL);
    	        dataRow.createCell(1).setCellValue(Title);
    	        dataRow.createCell(2).setCellValue(DateandTime);
    	    }

    	    // Save the workbook to a file
    	    try (FileOutputStream fileOut = new FileOutputStream("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\kairos.xlsx")) {
    	        workbook.write(fileOut);
    	    }
    	    
      
    }

    @AfterMethod
    public void sendEmail() throws EmailException {
    	 EmailAttachment excelAttachment = new EmailAttachment();
         excelAttachment.setPath("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\kairos.xlsx");
         excelAttachment.setDisposition(EmailAttachment.ATTACHMENT);
         excelAttachment.setDescription("Excel Report");
         excelAttachment.setName("kairos.xlsx");

         // Create an EmailAttachment for the folder (zip it for simplicity)
         String folderPath = "D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ExtentReports";
         String zipFilePath = "D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ScreenShots";
         zipFolder(folderPath, zipFilePath);

         EmailAttachment folderAttachment = new EmailAttachment();
         folderAttachment.setPath(zipFilePath);
         folderAttachment.setDisposition(EmailAttachment.ATTACHMENT);
         folderAttachment.setDescription("Test Output Folder");
         folderAttachment.setName("test-output.zip");
         
         Email email = new MultiPartEmail();
	        email.setHostName("smtp.gmail.com");
	        email.setSmtpPort(465);
	        email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "oqzh ifii yklx zpgn"));
	        email.setSSLOnConnect(true);
	        email.setFrom("kmbb4578@gmail.com");
	        email.setSubject("Kairos Websites Testing");
        
	    	email.setContent("Hi Team.\r\nKairos Websites are Working Fine", zipFilePath);
	        email.addTo("bhasha.k@kairostech.com");
	        email.addTo("mahaboobbhasha17@gmail.com");
	        email.addTo("pranathi.g@kairostech.com");
	        email.addTo("kmahaboobbhasha@gmail.com");
	        
        ((MultiPartEmail) email).attach(excelAttachment);
       // ((MultiPartEmail) email).attach(excelAttachment);
        ((MultiPartEmail) email).attach(folderAttachment);

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
    
    private static void zipFolder(String sourceFolderPath, String zipFilePath) {
        // Implementation to zip the folder (not included for brevity)
        // You can use a library like Apache Commons Compress or java.util.zip.ZipOutputStream
        // to create a zip file from the contents of the folder.
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
}
