import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Driver;

//Design Patterns - Visitor Pattern
public class   MobileTest {
    public String getTEST_NAME() {
        return TEST_NAME;
    }

    public void setTEST_NAME(String TEST_NAME) {
        this.TEST_NAME = TEST_NAME;
    }

    public long getCURRENT_TIME() {
        return CURRENT_TIME;
    }

    public void setCURRENT_TIME(long CURRENT_TIME) {
        this.CURRENT_TIME = CURRENT_TIME;
    }
//
//    public AppiumDriver getDRIVER() {
//        return DRIVER;
//    }
//
//    public void setDRIVER(AppiumDriver DRIVER) {
//        this.DRIVER = DRIVER;
//    }

    public String getDEVICE_NAME() {
        return DEVICE_NAME;
    }

    public void setDEVICE_NAME(String DEVICE_NAME) {
        this.DEVICE_NAME = DEVICE_NAME;
    }

    protected String TEST_NAME;
    protected long CURRENT_TIME;
//    protected AppiumDriver DRIVER;
    protected  String DEVICE_NAME;


};

