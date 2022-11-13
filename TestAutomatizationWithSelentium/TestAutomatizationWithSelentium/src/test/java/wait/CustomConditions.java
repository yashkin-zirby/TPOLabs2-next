package wait;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.File;

public class CustomConditions {
    public static ExpectedCondition<Boolean> NewFileInDownloadDirectory(){
        return new ExpectedCondition<Boolean>(){
            private final File[] downloadDirFiles = new File(System.getProperty("user.home") +"/Downloads").listFiles();
            public Boolean apply(WebDriver driver){
                File[] downloadDirCurrentFiles = new File(System.getProperty("user.home") +"/Downloads").listFiles();
                if(downloadDirCurrentFiles == null)return false;
                if(downloadDirFiles == null)return false;
                if(filesContainsNotDownloaded(downloadDirCurrentFiles))return false;
                return downloadDirCurrentFiles.length > downloadDirFiles.length;
            }
            private boolean filesContainsNotDownloaded(File[] files){
                for (File file: files) {
                    if(file.getName().endsWith(".crdownload"))return true;
                }
                return false;
            }
        };
    }
}
