package pageobject_model.page.pastebin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreatedPastePage {
    private final WebDriver driver;
    @FindBy(xpath = "//a[@class='btn -small h_800' and contains(@href,'archive')]")
    private WebElement pasteSourceCodeType;
    @FindBy(xpath = "//div[1]/div[2]/div[1]/div[1]/div[4]/div[2]/ol")
    private WebElement pasteText;
    private String pasteName;
    public CreatedPastePage(WebDriver driver,String title){
        this.driver = driver;
        pasteName = title;
        PageFactory.initElements(driver,this);
    }
    public String getPageURL(){
        return driver.getCurrentUrl();
    }
    public String getPasteTitle(){
        new WebDriverWait(driver,10).until(ExpectedConditions.titleContains(pasteName));
        return driver.getTitle();
    }
    public String getPasteSourceCode(){
        new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[1]/div[2]/div[1]/div[1]/div[4]/div[2]/ol")));
        return pasteSourceCodeType.getText();
    }
    public String getPasteText(){
        new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='btn -small h_800' and contains(@href,'archive')]")));
        return pasteText.getText();
    }
}
