package service;

import org.apache.logging.log4j.LogManager;

import java.util.Random;
import java.util.ResourceBundle;

public class TestDataReader {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(System.getProperty("environment") == null? "data" :System.getProperty("environment"));
    public static String getTestData(String key){
        return resourceBundle.getString(key);
    }
    public static String getRandomTestDataFromList(String key){
        String[] fonts = resourceBundle.getString(key).split(",");
        Random random = new Random();
        return fonts[Math.abs(random.nextInt()) % fonts.length];
    }
}
