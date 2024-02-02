package kairos_website;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
public class DOMLocators {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
    	WebDriver driver;
    	WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:81/login");
        // Find all elements in the DOM
		 List<WebElement> allElements = driver.findElements(By.xpath("//*"));
        for (WebElement element : allElements) {
            printLocator(element);
        }
       
        driver.quit();
    }

    private static void printLocator(WebElement element) {
        String tagName = element.getTagName();
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        String className = element.getAttribute("class");

        System.out.println("Tag Name: " + tagName);
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Class Name: " + className);
        System.out.println("-----------------------------");
    }
}
