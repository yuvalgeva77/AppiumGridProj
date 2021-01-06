import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private String accessKey;
    private String cloudUrl;
    private String serialNumber;

    public DriverFactory(String accessKey, String cloudUrl, String serialNumber) {
        this.accessKey = accessKey;
        this.cloudUrl = cloudUrl;
        this.serialNumber = serialNumber;
    }


    public AndroidDriver getAndroidDriverApp(String appPackage, String appActivity, Boolean createAppATribute) throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("accessKey", accessKey);
        dc.setCapability("deviceQuery", "@os='android'");
        if (!serialNumber.equals(""))
            dc.setCapability("deviceQuery", "@os='android' and @serialNumber=\'"+serialNumber+"\'");
//                dc.setCapability("app", "cloud:com.experitest.ExperiBank/.LoginActivity");
//        dc.setCapability("fullReset", true);
        dc.setCapability("appPackage", appPackage);
//                dc.setCapability("appPackage", "com.experitest.ExperiBank");
        dc.setCapability("appActivity", appActivity);
//                dc.setCapability("appActivity", ".LoginActivity");
        if(createAppATribute){
            String APP="cloud:"+appPackage+"/"+appActivity;
            dc.setCapability("app", APP);
        }
        return new AndroidDriver(new URL(cloudUrl), dc);}

    public AndroidDriver getAndroidDriverChrome(String testName,Boolean fullReset) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("accessKey",accessKey);
        capabilities.setCapability("deviceQuery", "@os='android'");
        if (!serialNumber.equals(""))
            capabilities.setCapability("deviceQuery", "@os='android' and @serialNumber=\'"+serialNumber+"\'");
        capabilities.setCapability("testName", "EspnTest Android");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,true);
        capabilities.setBrowserName(MobileBrowserType.CHROMIUM);
        return new AndroidDriver<>(new URL(cloudUrl), capabilities);


//        if(createAppATribute){
//            String APP="cloud:"+appPackage+"/"+appActivity;
//            dc.setCapability("app", APP);
//    }
    }

    //        test_name="App Store ios";
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("accessKey", "eyJhbGciOiJIUzI1NiJ9.eyJ4cC51Ijo0MDY4NjAyLCJ4cC5wIjozOTQ5MDQ1LCJ4cC5tIjoxNjA3NTA3MTQyNzMxLCJleHAiOjE5MjI4NjcxNDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0CmfSM3ZeEOlm8wXW1CAzg_JzZcUBu5ujz1vfgD73t4");
//        capabilities.setCapability("deviceQuery", "@os='ios'");
////        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
//
////        capabilities.setCapability(MobileCapabilityType.APP, "cloud:com.apple.AppStore");
//        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.AppStore");
    public IOSDriver getIOSDriverApp(String bundle_id) throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        String APP="cloud:"+bundle_id;
        dc.setCapability("accessKey", accessKey);
        dc.setCapability("deviceQuery", "@os='ios'");
        if (!serialNumber.equals(""))
            dc.setCapability("deviceQuery", "@os='ios' and @serialNumber=\'"+serialNumber+"\'");
//        dc.setCapability(MobileCapabilityType.APP, APP);
        dc.setCapability(MobileCapabilityType.FULL_RESET, true);
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, bundle_id);
//        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.AppStore");
        return new IOSDriver<>(new URL(cloudUrl), dc);


    }
    public IOSDriver getIOSDriverSAFARI(Boolean fullReset) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("accessKey", accessKey);
        capabilities.setCapability("deviceQuery", "@os='ios'");
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,fullReset);
        capabilities.setBrowserName(MobileBrowserType.SAFARI);
        return new IOSDriver(new URL(cloudUrl), capabilities);


    }


}

