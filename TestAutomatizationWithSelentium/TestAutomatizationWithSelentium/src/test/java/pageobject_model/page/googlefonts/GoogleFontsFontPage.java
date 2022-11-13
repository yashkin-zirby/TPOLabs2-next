package pageobject_model.page.googlefonts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wait.CustomConditions;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

public class GoogleFontsFontPage {

    private final WebDriver driver;
    @FindBy(xpath = "//a[contains(@class,'breadcrumb__action--download')]")
    private WebElement downloadButton;
    public GoogleFontsFontPage(WebDriver driver){
        this.driver = driver;
        new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@class,'breadcrumb__action--download')]")));
        PageFactory.initElements(driver,this);
    }
    public File downloadFamily() throws FileNotFoundException {
        downloadButton.click();
        File downloadsDir = new File(System.getProperty("user.home") +"/Downloads");
        new WebDriverWait(driver,20)
                .until(CustomConditions.NewFileInDownloadDirectory());
        File[] downloadedFiles = downloadsDir.listFiles();
        if(downloadedFiles == null)throw new FileNotFoundException();
        return getLastModified(downloadedFiles);
    }

    private static File getLastModified(File[] files)
    {
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file.lastModified() > lastModifiedTime)
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }
}
