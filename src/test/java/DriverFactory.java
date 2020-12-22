import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private String accessKey="eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4";
    public AndroidDriver getAndroidDriverApp(String appPackage,String appActivity) throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        String APP="cloud:"+appPackage+"/"+appActivity;
                dc.setCapability("accessKey", "eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
                dc.setCapability("deviceQuery", "@os='android'");
//                dc.setCapability("app", "cloud:com.experitest.ExperiBank/.LoginActivity");
                dc.setCapability("app", APP);
                dc.setCapability("fullReset", true);
                dc.setCapability("appPackage", appPackage);
//                dc.setCapability("appPackage", "com.experitest.ExperiBank");
                dc.setCapability("appActivity", appActivity);
//                dc.setCapability("appActivity", ".LoginActivity");
                return new AndroidDriver(new URL("https://qacloud.experitest.com/wd/hub"), dc);}


    public IOSDriver getIOSDriverApp(String appPackage,String appActivity) throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        String APP="cloud:"+appPackage+"/"+appActivity;
        dc.setCapability("accessKey", "eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
        dc.setCapability("deviceQuery", "@os='android'");
//                dc.setCapability("app", "cloud:com.experitest.ExperiBank/.LoginActivity");
        dc.setCapability("app", APP);
        dc.setCapability("fullReset", true);
        dc.setCapability("appPackage", appPackage);
//                dc.setCapability("appPackage", "com.experitest.ExperiBank");
        dc.setCapability("appActivity", appActivity);
//                dc.setCapability("appActivity", ".LoginActivity");
        return new IOSDriver(new URL("https://qacloud.experitest.com/wd/hub"), dc);


        }


}

