package service;

import driver.WebDriverSingleton;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerListener implements ITestListener {
    private final Logger log = LogManager.getRootLogger();
    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        saveScreenShot();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
    private void saveScreenShot(){
        File screenCapture = ((TakesScreenshot) WebDriverSingleton
                                .getDriver())
                                .getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(screenCapture,new File(
                    ".//target/screenshots/" +
                            getCurrentDateAsString() +
                            ".png"));
        } catch (IOException e) {
            log.error(getCurrentDateAsString()+"| Failed to save screenshot: "+e.getLocalizedMessage());
        }
    }
    private String getCurrentDateAsString(){
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd-MM-uuuu_HH-mm-ss");
        return ZonedDateTime.now().format(formatter);
    }
}
