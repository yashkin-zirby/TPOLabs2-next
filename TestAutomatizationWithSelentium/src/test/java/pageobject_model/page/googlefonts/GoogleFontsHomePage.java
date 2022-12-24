package pageobject_model.page.googlefonts;

import bsh.This;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleFontsHomePage {
    private static final String HOMEPAGE_URL = "https://fonts.google.com/";
    @FindBy(id = "mat-input-0")
    private WebElement searchInput;
    @FindBy(xpath = "//button[@aria-label='Open font property filters interactions menu']")
    private WebElement fontPropertiesMenu;
    @FindBy(xpath = "//input[@id='mat-mdc-checkbox-15-input']")
    private WebElement fontStyleCountCheckbox;
    @FindBy(xpath = "//div[2]/div[2]/div/div/div/div/div[1]/div[2]/div/mat-slider/input")
    private WebElement fontStyleCountSlider;
    @FindBy(xpath = "//*[@id='main-content']/gf-global-toolbar/div[1]/gf-global-font-preview-toolbar/div/gf-toolbar-text-modifier/mat-form-field/div[1]/div[2]/div")
    private WebElement fontSentence;

    private final WebDriver driver;
    public GoogleFontsHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public GoogleFontsHomePage openPage(){
        driver.get(HOMEPAGE_URL);
        return this;
    }
    public GoogleFontsHomePage searchByFontName(String fontName){
        searchInput.sendKeys(fontName);
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
        return this;
    }
    public GoogleFontsFontPage clickOnFontAtNumber(int numberOfFont){
        By locator = By.xpath("//gf-tile["+Integer.toString(numberOfFont)+"]/a");
        new WebDriverWait(driver,10).until(ExpectedConditions.or(
                ExpectedConditions.urlContains("query"),
                ExpectedConditions.urlContains("stylecount")));
        new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement font = driver.findElement(locator);
        font.click();
        return new GoogleFontsFontPage(driver);
    }
}
