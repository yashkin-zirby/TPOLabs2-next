package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.CustomConditions;
import util.ZipReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class GoogleFontsFontPage extends AbstractPage {
    @FindBy(xpath = "//a[contains(@class,'breadcrumb__action--download')]")
    private WebElement downloadButton;
    @FindAll({@FindBy(xpath = "//*[@id='styles']/gf-specimen-variants-preview/gf-variants-preview/ol/li")})
    public List<WebElement> styleBlocks;
    @FindBy(xpath = "//button[@aria-label='Open continent filter interactions menu']")
    private  WebElement selectContinentButton;
    @FindBy(xpath = "//button[@aria-label='Open language filter interactions menu']")
    private  WebElement selectLanguageButton;
    protected final int WAIT_DOWNLOAD_TIMEOUT_SECONDS = 30;
    @Override
    protected GoogleFontsHomePage openPage() {
        return new GoogleFontsHomePage(driver).openPage();
    }
    public GoogleFontsFontPage(WebDriver driver){
        super(driver);
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class,'breadcrumb__action--download')]")));
        PageFactory.initElements(driver,this);
    }
    public GoogleFontsFontPage selectContinent(String continent){
        selectContinentButton.click();
        String camelCase = continent.toLowerCase();
        camelCase = camelCase.substring(0,1).toUpperCase()+camelCase.substring(1);
        By locator = By.xpath("//span[contains(text(),'"+camelCase+"')]/ancestor::button");
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        driver.findElement(locator).click();
        return this;
    }
    public GoogleFontsFontPage selectLanguage(String language){
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[@aria-label='Open language filter interactions menu'][not(@disabled)]")));
        selectLanguageButton.click();
        String camelCase = language.toLowerCase();
        camelCase = camelCase.substring(0,1).toUpperCase()+camelCase.substring(1);
        By locator = By.xpath("//span[contains(text(),'"+camelCase+"')]/ancestor::button");
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement langButton = driver.findElement(locator);
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(langButton));
        langButton.click();

        return this;
    }
    public File downloadFamily() throws FileNotFoundException {
        downloadButton.click();
        File downloadsDir = new File(System.getProperty("user.home") +"/Downloads");
        new WebDriverWait(driver,WAIT_DOWNLOAD_TIMEOUT_SECONDS)
                .until(CustomConditions.NewFileInDownloadDirectory());
        File[] downloadedFiles = downloadsDir.listFiles();
        if(downloadedFiles == null)throw new FileNotFoundException();
        return ZipReader.getLastModified(downloadedFiles);
    }
    public int getFontStyleCount(){
        return styleBlocks == null? 0 : styleBlocks.size();
    }
    public String getFontFirstStyleText(){
        By locator = By.xpath("//*[@id='styles']/gf-specimen-variants-preview/gf-variants-preview/ol/li[1]/div/div");
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        styleBlocks = driver.findElements(locator);
        return styleBlocks == null? "" : styleBlocks.get(0).getText();
    }
}
