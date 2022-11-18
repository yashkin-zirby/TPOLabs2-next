package pageobject_model.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject_model.page.googlefonts.GoogleFontsHomePage;
import pageobject_model.page.pastebin.CreatedPastePage;
import pageobject_model.page.pastebin.PasteBinHomePage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PasteBinTest {
    WebDriver driver;
    @BeforeMethod(alwaysRun = true)
    public void initChromeDriver(){
        driver = new ChromeDriver();
        driver.manage().timeouts().setScriptTimeout(1, TimeUnit.MINUTES);
        driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
    }
    @Test
    public void ICanWinCreatePasteTest(){
        PasteBinHomePage homePage = new PasteBinHomePage(driver);
        String homeUrl = homePage.getPageURL();
        CreatedPastePage paste = homePage.openPage()
                .typeNewPaste("Hello from WebDriver")
                .selectPasteExpirationTenMinutes()
                .typePasteTitle("helloweb")
                .createPaste();
        Assert.assertNotEquals(homeUrl, paste.getPageURL());
    }
    @Test
    public void BringItOnPageTitleContainsPasteTitleTest(){
        CreatedPastePage paste = new PasteBinHomePage(driver)
                .openPage()
                .typeNewPaste("git config --global user.name  \"New Sheriff in Town\"\n" +
                             "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
                             "git push origin master --force")
                .selectSyntaxHighlightingBash()
                .selectPasteExpirationTenMinutes()
                .typePasteTitle("how to gain dominance among developers")
                .createPaste();
        String pageTitle = paste.getPasteTitle();
        Assert.assertTrue(pageTitle.contains("how to gain dominance among developers"),"Title '"+pageTitle+"' not contains 'how to gain dominance among developers'");
    }
    @Test
    public void BringItOnCheckBashSyntaxTest(){
        CreatedPastePage paste = new PasteBinHomePage(driver)
                .openPage()
                .typeNewPaste("git config --global user.name  \"New Sheriff in Town\"\n" +
                        "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
                        "git push origin master --force")
                .selectSyntaxHighlightingBash()
                .selectPasteExpirationTenMinutes()
                .typePasteTitle("how to gain dominance among developers")
                .createPaste();
        Assert.assertEquals(paste.getPasteSourceCode(),"Bash");
    }
    @Test
    public void BringItOnPageTextIsPasteTextTest(){
        String sourcePaste = "git config --global user.name  \"New Sheriff in Town\"\n" +
                "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
                "git push origin master --force";
        CreatedPastePage paste = new PasteBinHomePage(driver)
                .openPage()
                .typeNewPaste(sourcePaste)
                .selectSyntaxHighlightingBash()
                .selectPasteExpirationTenMinutes()
                .typePasteTitle("how to gain dominance among developers")
                .createPaste();
        Assert.assertEquals(paste.getPasteText(),sourcePaste);
    }
    @AfterMethod(alwaysRun = true)
    public void exitDriver(){
        driver.quit();
    }
}
