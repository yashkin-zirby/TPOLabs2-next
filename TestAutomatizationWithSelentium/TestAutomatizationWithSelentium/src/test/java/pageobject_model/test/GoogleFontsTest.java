package pageobject_model.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject_model.page.googlefonts.GoogleFontsHomePage;

import java.io.File;
import java.io.FileNotFoundException;

public class GoogleFontsTest {
    WebDriver driver;
    @BeforeMethod(alwaysRun = true)
    public void initChromeDriver(){
        driver = new ChromeDriver();
    }
    @Test
    public void DownloadFontWithSansWordInName() throws FileNotFoundException, InterruptedException {
        File downloadedFontFile = new GoogleFontsHomePage(driver)
                .openPage()
                .searchByFontName("sans")
                .clickOnFontAtNumber(2)
                .downloadFamily();
        Assert.assertTrue(downloadedFontFile.getName().toLowerCase().contains("sans"),"Имя файла "+downloadedFontFile.getName()+" - не содержит слова sans");
    }
    @Test
    public void FindFontWithMinimumNineStylesTest() throws InterruptedException {
        int styleCount = new GoogleFontsHomePage(driver)
                .openPage()
                .setMinStyleCount(9)
                .clickOnFontAtNumber(1)
                .getFontStyleCount();
        Thread.sleep(5000);
        Assert.assertTrue(styleCount >= 9);
    }
    @AfterMethod(alwaysRun = true)
    public void exitDriver(){
        driver.quit();
    }
}
