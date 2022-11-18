package pageobject_model.page.pastebin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PasteBinHomePage {
    private final String HOMEPAGE_URL = "https://pastebin.com";
    private final WebDriver driver;
    @FindBy(xpath = "//*[@id='postform-text']")
    private WebElement pasteTextBox;
    @FindBy(xpath = "//div[@class=\"form-group field-postform-expiration\"]//*[@class=\"select2-selection__arrow\"]")
    private WebElement pasteExpirationList;
    @FindBy(xpath="//li[text()='10 Minutes']")
    private WebElement tenMinutePasteExpiration;
    @FindBy(xpath = "//*[@id='postform-name']")
    private  WebElement pasteTitleTextBox;
    @FindBy(xpath="//*[@id='w0']/div[5]/div[1]/div[10]/button")
    private WebElement createPasteButton;
    @FindBy(id="select2-postform-format-container")
    private WebElement syntaxHighlighting;
    @FindBy(xpath = "//li[text()='Bash']")
    private WebElement bashSyntaxHighlighting;
    private String title;

    public PasteBinHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public PasteBinHomePage openPage(){
        driver.get(HOMEPAGE_URL);
        return this;
    }
    public PasteBinHomePage selectSyntaxHighlightingBash(){
        syntaxHighlighting.click();
        new WebDriverWait(driver,5)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='Bash']")));
        bashSyntaxHighlighting.click();
        return this;
    }
    public PasteBinHomePage typeNewPaste(String paste){
        pasteTextBox.sendKeys(paste);
        return this;
    }
    public String getPageURL(){
        return driver.getCurrentUrl();
    }
    public PasteBinHomePage selectPasteExpirationTenMinutes(){
        pasteExpirationList.click();
        new WebDriverWait(driver,5)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='10 Minutes']")));
        tenMinutePasteExpiration.click();
        return this;
    }
    public PasteBinHomePage typePasteTitle(String title) {
        new WebDriverWait(driver,15).until(ExpectedConditions.presenceOfElementLocated(By.id("postform-name")));
        pasteTitleTextBox.sendKeys(title);
        this.title = pasteTitleTextBox.getText();
        return this;
    }
    public CreatedPastePage createPaste(){
        createPasteButton.click();
        return new CreatedPastePage(driver,title);
    }
}
