import io.appium.java_client.AppiumDriver;

public class Device {

    private String name;
    private String category;
    private String version;
    private String os;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getVersion() {
        return version;
    }


    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", version='" + version + '\'' +
                ", os='" + os + '\'' +
                '}';
    }

    public String getOs() {
        return os;
    }

    public Device(AppiumDriver driver) {
        name=driver.getCapabilities().getCapability("device.name").toString();
        category=driver.getCapabilities().getCapability("device.category").toString();
        version =driver.getCapabilities().getCapability("device.version").toString();
        os=        version =driver.getCapabilities().getCapability("device.os").toString();

    }

}
