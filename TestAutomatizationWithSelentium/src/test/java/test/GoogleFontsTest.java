package test;

import driver.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.GoogleFontsHomePage;
import util.AlphabetCheck;
import service.TestDataReader;
import util.ZipReader;

import java.io.File;
import java.io.FileNotFoundException;

public class GoogleFontsTest {
    WebDriver driver;
    private final String FONT_NAMES_PROPERTY = "data.font-names";
    private final String ALPHABET_PROPERTY = "data.alphabet";
    @BeforeMethod(alwaysRun = true)
    public void initChromeDriver(){
        driver = WebDriverSingleton.getDriver();
    }
    @Test
    public void DownloadFontWithSansWordInNameTest() throws FileNotFoundException, InterruptedException {
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
    @Test
    public void FindCyrillicFontTest() throws InterruptedException{
        String text = new GoogleFontsHomePage(driver)
                .openPage()
                .searchByFontName(TestDataReader.getRandomTestDataFromList(FONT_NAMES_PROPERTY))
                .selectLanguage("Cyrillic")
                .clickOnFontAtNumber(1)
                .selectContinent("Europe")
                .selectLanguage("Russian")
                .getFontFirstStyleText();
        Assert.assertTrue(AlphabetCheck.isRussian(text),"Алфавит содержит символы не входящие в русский язык");
    }
    @Test
    public void DownloadVariableFontTest() throws FileNotFoundException {
        File archive = new GoogleFontsHomePage(driver)
                .openPage()
                .searchByFontName(TestDataReader.getRandomTestDataFromList(FONT_NAMES_PROPERTY))
                .clickToVariableFontCheckbox()
                .clickOnFontAtNumberOrFirst(2)
                .downloadFamily();
        Assert.assertTrue(ZipReader.archiveContainFileWithNameLike(
                archive.getAbsolutePath(),
                "Variable"));
    }
    @Test
    public void DownloadIconTest() throws FileNotFoundException {
        File icon = new GoogleFontsHomePage(driver)
                .openPage()
                .clickToIconsMenuButton()
                .selectIconsCategory("UI actions")
                .searchMaterialSymbols("download")
                .clickToIconAtNumber(1)
                .downloadIcon();
        Assert.assertTrue(icon.getName().toLowerCase().contains("download"));
    }
    @AfterMethod(alwaysRun = true)
    public void exitDriver(){
        WebDriverSingleton.CloseDriver();
    }
}
