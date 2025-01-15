package kairos_website;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.testng.Assert;
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

public class Kairos_Websites_Testing_ {static WebDriver driver;
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    static JavaUtilities javaUtilitis =new JavaUtilities();
    String URL;
    String Title;
    String DateandTime;
    static String[] urls= {
            "https://kairostech.com/",
            "https://kairostech.com/quality-assurance/",
            "https://kairostech.com/salesforce-testing/",
            "https://kairostech.com/regression-testing/",
            "https://kairostech.com/automation-testing/",
            "https://kairostech.com/performance-testing/",
            "https://kairostech.com/ai-model-testing/",
            "https://kairostech.com/mobile-app-testing/",
            "https://kairostech.com/microservices-testing/",
            "https://kairostech.com/chaos-engineering/",
            "https://kairostech.com/data-testing/",
            "https://kairostech.com/oracle-solutions/",
            "https://kairostech.com/application-modernization/",
            "https://kairostech.com/robotic-process-automation/",
            "https://kairostech.com/digital-application-development/",
            "https://kairostech.com/about-us/",
            "https://kairostech.com/leadership/",
            "https://kairostech.com/customer-stories/",
            "https://kairostech.com/whitepaper/",
            "https://kairostech.com/blog/",
            "https://kairostech.com/newsletter/",
            "https://kairostech.com/careers/",
            "https://kairostech.com/current-openings/",
            "https://kairostech.com/contact-us/",
            "https://kairostech.com/data-analytics-testing/",
            "https://kairostech.com/contact-form/",
            "https://kairostech.com/privacy-policy/",
            "https://kairostech.com/blog-list/how-ai-is-revolutionizing-automation-testing-in-quality-assurance-2023-in-review/",
            "https://kairostech.com/blog-list/what-is-smart-regression-testing/",
            "https://kairostech.com/blog-list/revolutionizing-api-testing-with-no-code-tools/",
            "https://kairostech.com/customer-story/achieving-excellence-through-total-quality-assurance/",
            "https://kairostech.com/customer-story/rental-car-business-gains-a-competitive-edgewith-new-age/",
            "https://kairostech.com/customer-story/how-an-automobile-manufacturer-streamlinedtheir-processes-with-rpa/",
            "https://kairostech.com/customer-story/a-century-old-healthcare-organizations-journeyto-establishing-a-quality-engineering-center-ofexcellence/",
            "https://kairostech.com/customer-story/how-an-american-banking-firm-reduced-mobile-app-testing-costs-by-20x/",
            "https://kairostech.com/whitepapers/exploring-the-future-of-generative-ai/",
            "https://kairostech.com/blog-list/the-future-of-software-quality-engineering-trends-driving-innovation-in-2025/",
            "https://kairostech.com/blog-list/no-code-dqm-vs-traditional-dqm-methods-make-the-right-choice/",
            "https://kairostech.com/blog-list/why-no-code-data-quality-management-is-the-future-of-business-analytics/",
            "https://kairostech.com/wp-content/uploads/2024/08/Newsletter-Aug-2024-1.pdf",
            "https://kairostech.com/jobs/junior-motion-graphics-designer/",
            "https://kairostech.com/jobs/network-engineer/",
            "https://kairostech.com/jobs/senior-test-manager/",
            "https://kairostech.com/jobs/senior-infrastructure-engineer-2/",
            "https://kairostech.com/jobs/senior-infrastructure-engineer/",
            "https://kairostech.com/jobs/unlock-your-potential-multiple-roles-offered/",
            "https://kairostech.com/jobs/multiple-positions/",
            "https://kairostech.com/jobs/consultant-senior-infrastructure-engineer/",
            "https://kairostech.com/jobs/trainee-software-engineer/",
            "https://kairostech.com/jobs/java-technical-lead/",
            "https://kairostech.com/jobs/senior-software-engineer/",
            "https://kairostech.com/jobs/senior-software-engineer-sdet/",
            "https://kairostech.com/jobs/marketing-manager/",
            "https://kairostech.com/jobs/react-developer/",
            "https://kairostech.com/jobs/practice-head/"
    };
    @BeforeSuite
    public void setupExtentReports()
    {
        ExtentSparkReporter spark = new ExtentSparkReporter("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\target\\surefire-reports\\emailable-report.html");
        extent.attachReporter(spark);
    }
    @BeforeMethod
    public void checkURLsConnection()
    {
        checkURLs();
    }
    @Test
    public void Websites() throws Throwable {
        int rowNum = 1; // Start from the second row
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        List<String> shuffledUrls = new ArrayList<>(List.of(urls));
        Collections.shuffle(shuffledUrls);
        List<String> selectedUrls = shuffledUrls.subList(0, Math.min(shuffledUrls.size(), 54));

        StringBuilder htmlTable = new StringBuilder("<table border='2' style='border-collapse: collapse; border: 2px solid black;'>");
        htmlTable.append("<tr style='background:#068fc9;'>")
                .append("<th style='font-weight: bold;'>Device</th>")
                .append("<th style='font-weight: bold;'>Device OS</th>")
                .append("<th style='font-weight: bold;'>Browser</th>")
                .append("<th style='font-weight: bold;'>Browser Version</th>")
                .append("<th style='font-weight: bold;'>URL</th>")
                .append("<th style='font-weight: bold;'>Website Page Verified</th>")
                .append("<th style='font-weight: bold;'>Date & Time</th>")
                .append("<th style='font-weight: bold;'>Status</th>")
                .append("</tr>");
        System.out.println("Testing Urls");
        for (String url : selectedUrls) {
            test = extent.createTest("Test for URL: " + url);
            driver.get(url);
            Thread.sleep(2000);
            String ActualURL = driver.getCurrentUrl();
            Assert.assertEquals(url, ActualURL);
            test.pass("Page loaded successfully");

            // WebElement acceptall = driver.findElement(By.xpath("//button[text() ='Accept All']"));
            // if (acceptall.isDisplayed()) {
            //     acceptall.click();
            // }

            // Check if warning text is present on the page
            String warningText = "Warning:"; // Adjust the warning text as needed
            List<WebElement> warningElements = driver.findElements(By.xpath("//*[contains(text(), '" + warningText + "')]"));
            if (!warningElements.isEmpty()) {
                sendErrorEmail(url, warningText); // Send email if warning text is found

                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
                File destinationFolder = new File("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ScreenShots");
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs();
                }
                String fileName = "Kairos_website_Failed" + javaUtilitis.getSystemdate() + ".png";
                File destinationFile = new File(destinationFolder, fileName);
                FileUtils.copyFile(sourceFile, destinationFile);
            }
            else{
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();


            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destinationFolder = new File("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ScreenShots");
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }
            String fileName = "Kairos_website_Passed" + javaUtilitis.getSystemdate() + ".png";
            File destinationFile = new File(destinationFolder, fileName);
            FileUtils.copyFile(sourceFile, destinationFile);
            String URL = driver.getCurrentUrl();
            String Title = driver.getTitle();
            String DateandTime = javaUtilitis.IST();
            Capabilities capabilities = ((ChromeDriver) driver).getCapabilities();
            String Browser = capabilities.getBrowserName();
            String BrowserVersion = capabilities.getBrowserVersion();
            Platform deviceos = capabilities.getPlatformName();
            String osName = System.getProperty("os.name");
            String computerName = System.getenv("COMPUTERNAME");

            // status code
            String status = urls.equals(responseCode) ? "Not Working" : " Working";
            htmlTable.append("<tr>");
            htmlTable.append("<td>").append("Lenovo Thinkpad").append("</td>");
            htmlTable.append("<td>").append(osName).append("</td>");
            htmlTable.append("<td>").append(Browser).append("</td>");
            htmlTable.append("<td>").append(BrowserVersion).append("</td>");
            htmlTable.append("<td>").append(URL).append("</td>");
            htmlTable.append("<td style='color:#fa8405'>").append(Title).append("</td>");
            htmlTable.append("<td style='color:#0925db;'>").append(DateandTime).append("</td>");
            htmlTable.append("<td style='color: " + (urls.equals(responseCode) ? "red" : "green") + ";'>").append(status).append("</td>");
            System.out.println(driver.getTitle() + ":" + driver.getCurrentUrl() + "    Status Code :" + responseCode);

            htmlTable.append("</tr>");

        }
        }

        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com"); // Set your SMTP server
        email.setSmtpPort(587); // Set your SMTP port
        email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "mhch lpyv zjei snqg")); // Set your email credentials
        email.setSSLOnConnect(true); // Enable SSL (if required)

        email.setFrom("kmbb4578@gmail.com");
        email.setSubject("Kairos Website Testing Report");
        StringBuilder emailContent = new StringBuilder("Hi Team.\r\nKairos Website is Working Fine");
        emailContent.append(htmlTable);
        email.setContent("Hi Team.\r\nKairos Website is Working Fine" + htmlTable, "text/html");

        email.addCc("bhasha.k@kairostech.com");
        email.addTo("durgaprasad.b@kairostech.com");
        email.addCc("mounika.t@kairostech.com");
        // email.addTo("manzoore.m@kairostech.com");
        // email.addTo("ravikumar.p@kairostech.com");
        email.send();
    }

    private void sendErrorEmail(String url, String warningText) throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "mhch lpyv zjei snqg"));
        email.setSSLOnConnect(true);

        email.setFrom("kmbb4578@gmail.com");
        email.setSubject("Kairos Website Warning Alert");
        String errorContent = "Hi Team,\n\nA warning Message was detected on the following page:\n\n" +
                "URL: " + url + "\n\nWarning Text: " + warningText;
        email.setContent(errorContent, "text/plain");

        email.addCc("bhasha.k@kairostech.com");
        email.addTo("durgaprasad.b@kairostech.com");
        email.addCc("mounika.t@kairostech.com");
        email.addTo("manzoore.m@kairostech.com");
        email.addTo("ravikumar.p@kairostech.com");
        email.send();
    }

    @AfterMethod
    public void closeBrowser() {
        if (driver != null) {
            driver.quit(); // Close the browser instance
        }
    }


    public static void checkURLs() {
        for (String url : urls) {
            try {
                URL urlObj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
//	                    System.out.println(url + " is reachable.");
                    System.out.println(url+"  Status Code :"+responseCode + " is reachable.");

                } else {
                    System.out.println(url + " is not reachable. Response Code: " + responseCode);
                    HtmlEmail email = new HtmlEmail();
                    email.setHostName("smtp.gmail.com");
                    email.setSmtpPort(587);
                    email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "mhch lpyv zjei snqg"));
                    email.setSSLOnConnect(true);

                    email.setFrom("kmbb4578@gmail.com");
                    email.setSubject("Kairos Website Testing Report Error");
                    email.setContent("Hi Team.\r\nKairos Website is not working:<br>" +url + " is not reachable. Response Code: " + responseCode, "text/html");

                    email.addCc("bhasha.k@kairostech.com");
                    email.addTo("durgaprasad.b@kairostech.com");
                    email.addCc("mounika.t@kairostech.com");
                    email.addTo("manzoore.m@kairostech.com");
                    email.addTo("ravikumar.p@kairostech.com");

                }

                connection.disconnect();
            } catch (Exception e) {
                System.out.println(url + " is not reachable. Exception: " + e.getMessage());
            }
        }
        System.out.println("Total Urls:"+urls.length);
    }
    @AfterSuite
    public void teardownExtentreports()
    {

        extent.flush();
    }


}
