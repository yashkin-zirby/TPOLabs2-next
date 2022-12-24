package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleFontsHomePage extends AbstractPage {
    private static final String HOMEPAGE_URL = "https://fonts.google.com/";
    @FindBy(id = "mat-input-0")
    private WebElement searchInput;
    @FindBy(xpath = "//button[@aria-label='Open language filter interactions menu']")
    private WebElement selectLanguageButton;
    @FindBy(xpath = "//button[@aria-label='Open font property filters interactions menu']")
    private WebElement fontPropertiesMenu;
    @FindBy(xpath = "//*[@aria-label='Enabled number of styles filter']")
    private WebElement fontStyleCountCheckbox;
    @FindBy(xpath = "//div[2]/div[2]/div/div/div/div/div[1]/div[2]/div/mat-slider/input")
    private WebElement fontStyleCountSlider;
    @FindBy(xpath = "//*[@id='main-content']/gf-global-toolbar/div[1]/gf-global-font-preview-toolbar/div/gf-toolbar-text-modifier/mat-form-field/div[1]/div[2]/div")
    private WebElement fontSentence;
    @FindBy(xpath = "//input[@aria-label='Is variable fonts filter active']")
    private WebElement showOnlyVariableFontsCheckbox;
    @FindBy(xpath = "//gf-google-header-nav-item[2]/a")
    private WebElement iconMenuButton;
    private String UriContain = "";
    public GoogleFontsHomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }
    public GoogleFontsHomePage selectLanguage(String language){
        selectLanguageButton.click();
        String camelCase = language.toLowerCase();
        camelCase = camelCase.substring(0,1).toUpperCase()+camelCase.substring(1);
        By locator = By.xpath("//span[contains(text(),'"+camelCase+"')]/ancestor::button");
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        driver.findElement(locator).click();
        UriContain = "subset";
        return this;
    }
    public GoogleFontsIconsPage clickToIconsMenuButton(){
        iconMenuButton.click();
        return new GoogleFontsIconsPage();
    }
    public GoogleFontsHomePage clickToVariableFontCheckbox(){
//        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
//                .until(ExpectedConditions.elementToBeClickable(showOnlyVariableFontsCheckbox));
        showOnlyVariableFontsCheckbox.click();
        UriContain = "vfonly";
        return this;
    }
    public GoogleFontsHomePage openPage(){
        driver.get(HOMEPAGE_URL);
        return this;
    }
    public GoogleFontsHomePage searchByFontName(String fontName){
        searchInput.sendKeys(fontName);
        UriContain = "query";
        return this;
    }
    public GoogleFontsHomePage setMinStyleCount(int styleCount){
        fontPropertiesMenu.click();
        if(!fontStyleCountCheckbox.getAttribute("class").contains("mdc-checkbox--selected"))
            fontStyleCountCheckbox.click();
        int width = fontStyleCountSlider.getSize().getWidth();
        double clickPossition = styleCount/Double.parseDouble(fontStyleCountSlider.getAttribute("max"));
        Actions setMinStyleCountAction= new Actions(driver);
        setMinStyleCountAction.moveToElement(fontStyleCountSlider, width/2-(int)Math.floor(width*clickPossition), 0).click();
        setMinStyleCountAction.build().perform();
        Actions closeFontPropertiesMenuAction = new Actions(driver);
        closeFontPropertiesMenuAction.moveToElement(fontPropertiesMenu, 0, 0).click();
        closeFontPropertiesMenuAction.build().perform();
        UriContain = "stylecount";
        return this;
    }
    public GoogleFontsFontPage clickOnFontAtNumber(int numberOfFont){
        By locator = By.xpath("//gf-tile["+Integer.toString(numberOfFont)+"]/a");
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.urlContains(UriContain));
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement font = driver.findElement(locator);
        font.click();
        return new GoogleFontsFontPage(driver);
    }
    public GoogleFontsFontPage clickOnFontAtNumberOrFirst(int numberOfFont){
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.urlContains(UriContain));
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//gf-tile")));
        int len = driver.findElements(By.xpath("//gf-tile")).size();
        if(numberOfFont > len || numberOfFont < 1){
            numberOfFont = 1;
            logger.info("font number is larger then fonts count, so been clicked to first font");
        }
        By locator = By.xpath("//gf-tile["+Integer.toString(numberOfFont)+"]/a");
        new WebDriverWait(driver,WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement font = driver.findElement(locator);
        font.click();
        return new GoogleFontsFontPage(driver);
    }
}
