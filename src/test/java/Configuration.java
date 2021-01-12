import com.google.gson.Gson;
import com.google.gson.InstanceCreator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

public class Configuration  implements InstanceCreator {
    private String cloudUrl;
    private String username;
    private String  accessKey;
    private int numOfDevices;
    private String serialNumber;
    private String testToRun;
    private int repeat;


    @Override
    public String toString() {
        return "Configuration{" +
                "cloudUrl='" + cloudUrl + '\'' +
                ", username='" + username + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", numOfDevices=" + numOfDevices +
                ", serialNumber='" + serialNumber + '\'' +
                ", testToRun='" + testToRun + '\'' +
                ", repeat=" + repeat +
                '}';
    }

    public Configuration(String cloudUrl, String username, String accessKey, int numOfDevices, String serialNumber, String testToRun, int repeat) {
        this.cloudUrl = cloudUrl+"/wd/hub";
        this.username = username;
        this.accessKey = accessKey;
        this.numOfDevices = numOfDevices;
        this.serialNumber = serialNumber;
        this.testToRun = testToRun;
        this.repeat = repeat;
        if(testToRun.equals("all")){
//            this.testToRun=allTests;
        }

    }

    public String getCloudUrl() {
        return cloudUrl;
    }

    public void setCloudUrl(String cloudUrl) {
        this.cloudUrl = cloudUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public int getNumOfDevices() {
        return numOfDevices;
    }

    public void setNumOfDevices(int numOfDevices) {
        this.numOfDevices = numOfDevices;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTestToRun() {
        return testToRun;
    }

    public void setTestToRun(String testToRun) {
        this.testToRun = testToRun;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }


    @Override
    public Object createInstance(Type type) {
        if(testToRun.equals("all")){
//            this.testToRun=allTests;
        }
        return null;
    }
}
