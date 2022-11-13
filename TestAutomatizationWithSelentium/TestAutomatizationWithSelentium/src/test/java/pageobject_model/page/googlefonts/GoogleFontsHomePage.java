package pageobject_model.page.googlefonts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleFontsHomePage {
    private static final String HOMEPAGE_URL = "https://fonts.google.com/";
    @FindBy(id = "mat-input-0")
    private WebElement searchInput;
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
    public GoogleFontsFontPage clickOnFontAtNumber(int numberOfFont){
        By locator = By.xpath("//gf-tile["+Integer.toString(numberOfFont)+"]/a");
        new WebDriverWait(driver,10)
                .until(ExpectedConditions.urlContains("query"));
        new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement font = driver.findElement(locator);
        font.click();
        return new GoogleFontsFontPage(driver);
    }
}
