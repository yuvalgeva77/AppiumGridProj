import io.appium.java_client.AppiumDriver;

import java.util.Date;
import java.util.List;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
public class Device{
    public String id;
    public String udid;
    public String iosUdid;
    public String deviceName;
    public String notes;
    public String deviceOs;
    public String osVersion;
    public String model;
    public String manufacturer;
    public String currentUser;
    public String deviceCategory;
    public String uptime;
    public boolean isEmulator;
    public String profiles;
    public String agentName;
    public String agentIp;
    public String agentLocation;
    public String region;
    public String currentStatus;
    public String statusTooltip;
    public String lastUsedDateTime;
    public String previousStatus;
    public String statusAgeInMinutes;
    public String statusModifiedAt;
    public String statusModifiedAtDateTime;
    public String displayStatus;
    public boolean whitelistCleanup;
    public String defaultDeviceLanguage;

    public String getUdid() {
        return udid;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public String defaultDeviceRegion;
    public int screenWidth;
    public int screenHeight;
    public List<Object> tags;
    public List<String> imeis;

    public Device(AppiumDriver driver) {
        //TODO
//        name=driver.getCapabilities().getCapability("device.name").toString();
//        category=driver.getCapabilities().getCapability("device.category").toString();
//        version =driver.getCapabilities().getCapability("device.version").toString();
//        os =driver.getCapabilities().getCapability("device.os").toString();

    }

    public String getName() {
        return deviceName;
    }

    public String getCategory() {
        return deviceCategory;
    }

    public String getVersion() {
        return osVersion;
    }
    public String getOs() {
        return deviceOs;
    }


    @Override
    public String toString() {
        return "Device{" +
                "name='" + deviceName + '\'' +
                ", category='" + deviceCategory + '\'' +
                ", version='" + osVersion + '\'' +
                ", os='" + deviceOs + '\'' +
                '}';
    }

    public String getDisplayStatus() {
        return displayStatus;
    }
//    public Device(AppiumDriver driver) {
//        name=driver.getCapabilities().getCapability("device.name").toString();
//        category=driver.getCapabilities().getCapability("device.category").toString();
//        version =driver.getCapabilities().getCapability("device.version").toString();
//        os =driver.getCapabilities().getCapability("device.os").toString();
//
//    }

}
