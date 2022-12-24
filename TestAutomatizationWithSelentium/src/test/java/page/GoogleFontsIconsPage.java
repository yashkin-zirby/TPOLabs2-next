package page;

import driver.WebDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.CustomConditions;
import util.ZipReader;

import java.io.File;
import java.io.FileNotFoundException;

public class GoogleFontsIconsPage extends AbstractPage {

    @FindBy(xpath = "//button[@aria-label='Open Category menu']")
    private WebElement categoryButton;
    @FindBy(xpath = "//input[@aria-label='Search Material Symbols']")
    private WebElement inputSearchMaterial;
    private final int WAIT_DOWNLOAD_TIMEOUT_SECONDS = 30;
    private String UriContain = "";
    public GoogleFontsIconsPage(){
        super(WebDriverSingleton.getDriver());
    }
    public GoogleFontsIconsPage selectIconsCategory(String category){
        categoryButton.click();
        String camelCase = category.toLowerCase();
        camelCase = camelCase.substring(0,1).toUpperCase()+camelCase.substring(1);
        By locator = By.xpath("//span[contains(text(),'"+camelCase+"')]/ancestor::button");
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement categoryItem = driver.findElement(locator);
        categoryItem.click();
        UriContain = "category";
        return this;
    }
    public GoogleFontsIconsPage searchMaterialSymbols(String material){
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.urlContains(UriContain));
        inputSearchMaterial.sendKeys(material);
        UriContain = "query";
        return this;
    }
    public GoogleFontsIconsPage clickToIconAtNumber(int number){
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.urlContains(UriContain));
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/button")));
        int len = driver.findElements(By.xpath("//div/button")).size();
        if(number > len || number < 1){
            number = 1;
            logger.info("font number is larger then fonts count, so been clicked to first font");
        }
        By locator = By.xpath("//div/button["+Integer.toString(number)+"]");
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement icon = driver.findElement(locator);
        icon.click();
        return this;
    }
    public File downloadIcon() throws FileNotFoundException {
        By locator = By.xpath("//a[@aria-label='Download asset in SVG format for this icon']");
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement downloadButton = driver.findElement(locator);
        downloadButton.click();
        File downloadsDir = new File(System.getProperty("user.home") +"/Downloads");
        new WebDriverWait(driver,WAIT_DOWNLOAD_TIMEOUT_SECONDS)
                .until(CustomConditions.NewFileInDownloadDirectory());
        File[] downloadedFiles = downloadsDir.listFiles();
        if(downloadedFiles == null)throw new FileNotFoundException();
        return ZipReader.getLastModified(downloadedFiles);
    }
    @Override
    protected GoogleFontsHomePage openPage() {
        return new GoogleFontsHomePage(WebDriverSingleton.getDriver());
    }

}
