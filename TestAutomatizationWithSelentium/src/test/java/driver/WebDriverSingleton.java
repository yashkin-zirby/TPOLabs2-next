package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebDriverSingleton {
    private static WebDriver driver = null;
    private static final String RESOURCES_PATH = "src\\test\\resources\\";
    public static WebDriver getDriver(){
        if(driver == null){
            String browser = System.getProperty("browser");
            if(browser == null)browser = "chrome";
            switch (browser) {
                case "edge" -> {
                    System.setProperty("webdriver.edge.driver",RESOURCES_PATH+"msedgedriver.exe");
                    driver = new EdgeDriver();
                }
                default -> {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                }
            }
            driver.manage().window().maximize();
        }
        return driver;
    }
    public static void CloseDriver(){
        if(driver != null)driver.quit();
        driver = null;
    }

}
