import com.experitest.appium.SeeTestClient;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
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
    private Device device;
    private String NV_profile;

    public DriverFactory(String accessKey, String cloudUrl) {
        this.accessKey = accessKey;
        this.cloudUrl = cloudUrl+"/wd/hub";
    }

    public void setNV_profile(String NV_profile) {
        this.NV_profile = NV_profile;
    }

    public void setDevice(Device device) {
        this.device = device;
        NV_profile=device.getNV_profile();
    }

    public AndroidDriver getAndroidDriverApp(String appPackage, String appActivity, Boolean createAppATribute,Boolean instrumented,String testName) throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.UDID, device.getUdid());
        dc.setCapability("accessKey", accessKey);
        dc.setCapability("appPackage", appPackage);
        dc.setCapability("appActivity", appActivity);
        dc.setCapability("autoGrantPermissions", true);
        dc.setCapability("testName", testName);

        if(createAppATribute){
            String APP="cloud:"+appPackage+"/"+appActivity;
            dc.setCapability("app", APP);
        }
        if (NV_profile!="default"){
            dc.setCapability("nvProfile", NV_profile);
        }
        if (instrumented){
            dc.setCapability("instrumentApp", instrumented);
        }
        return new AndroidDriver(new URL(cloudUrl), dc);}

//    public AndroidDriver getAndroidDriver() throws MalformedURLException {
//        DesiredCapabilities dc = new DesiredCapabilities();
//        dc.setCapability(MobileCapabilityType.UDID, device.getUdid());
//        dc.setCapability("accessKey", accessKey);
//        dc.setCapability("autoGrantPermissions", true);
//        if (NV_profile!="defult"){
//            dc.setCapability("nvProfile", NV_profile);
//        }
//        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.uicatalog/.MainActivity");
//        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.uicatalog");
//        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
//        dc.setCapability("instrumentApp", true);
//        return new AndroidDriver(new URL(cloudUrl), dc);}

    public Client getClient() throws MalformedURLException {
        GridClient grid = new GridClient(accessKey,cloudUrl);
        Client client = grid.lockDeviceForExecution("Camera API2 test", "@serialNumber='" + device.getUdid() + "'", 10, 50000);
        client.setReporter("xml", "", "Camera API2 test");
        return client;}

    public AndroidDriver getAndroidDriverChrome(String testName,Boolean fullReset) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.UDID, device.getUdid());
        capabilities.setCapability("accessKey",accessKey);
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,fullReset);
        capabilities.setBrowserName(MobileBrowserType.CHROMIUM);
        if (NV_profile!="default"){
            capabilities.setCapability("nvProfile", NV_profile);
        }
        capabilities.setCapability("testName", testName);

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
    public IOSDriver getIOSDriverApp(String bundle_id,String testName) throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        String APP="cloud:";
        dc.setCapability("accessKey", accessKey);
//        dc.setCapability("deviceQuery", "@os='ios'");
//        if (!serialNumber.equals(""))
//            dc.setCapability("deviceQuery", "@os='ios' and @serialNumber=\'"+serialNumber+"\'");
////        dc.setCapability(MobileCapabilityType.APP, APP);
        dc.setCapability(MobileCapabilityType.UDID, device.getUdid());
        dc.setCapability(MobileCapabilityType.FULL_RESET, true);
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, bundle_id);
        dc.setCapability("testName", testName);

        if (NV_profile!="default"){
            dc.setCapability("nvProfile", NV_profile);
        }
//        capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.AppStore");
        return new IOSDriver<>(new URL(cloudUrl), dc);


    }
    public IOSDriver getIOSDriverSAFARI(Boolean fullReset,String testName) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("accessKey", accessKey);
//        capabilities.setCapability("deviceQuery", "@os='ios'");
        capabilities.setCapability(MobileCapabilityType.UDID, device.getUdid());
        capabilities.setCapability(MobileCapabilityType.FULL_RESET,fullReset);
        capabilities.setBrowserName(MobileBrowserType.SAFARI);
        if (NV_profile!="default"){
            capabilities.setCapability("nvProfile", NV_profile);
        }
        capabilities.setCapability("testName", testName);

        return new IOSDriver(new URL(cloudUrl), capabilities);


    }


}

