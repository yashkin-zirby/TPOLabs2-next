package test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomConditions {
    public static ExpectedCondition<Boolean> NewFileInDownloadDirectory(){
        return new ExpectedCondition<Boolean>(){
            private final File[] downloadDirFiles = new File(System.getProperty("user.home") +"/Downloads").listFiles();
            public Boolean apply(WebDriver driver){
                File[] downloadDirCurrentFiles = new File(System.getProperty("user.home") +"/Downloads").listFiles();
                if(downloadDirCurrentFiles == null)return false;
                if(downloadDirFiles == null)return false;
                if(filesContainsNotDownloaded(ExceptFileArray(downloadDirCurrentFiles,downloadDirFiles)))return false;
                return downloadDirCurrentFiles.length > downloadDirFiles.length;
            }
            private boolean filesContainsNotDownloaded(File[] files){
                for (File file: files) {
                    if(file.getName().endsWith(".crdownload"))return true;
                    if(file.getName().endsWith(".tmp"))return true;
                }
                return false;
            }
            private File[] ExceptFileArray(File[] firstFileSet, File[] secondFileSet){
                List<File> resultFileSet = new ArrayList<File>();
                for (File file : firstFileSet) {
                    boolean fileIsContains = false;
                    for (File file2 : secondFileSet) {
                        if (file.getName().equals(file2.getName())) fileIsContains = true;
                    }
                    if(!fileIsContains)resultFileSet.add(file);
                }
                File[] fileArray = new File[resultFileSet.size()];
                for(int i = 0; i < fileArray.length; i++){
                    fileArray[i] = resultFileSet.get(i);
                }
                return fileArray;
            }
        };
    }
}
