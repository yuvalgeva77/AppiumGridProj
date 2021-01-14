import com.google.gson.Gson;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Driver;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class  MobileTest {
    protected AppiumDriver driver;
    protected String test_status;
    public static  Configuration testConfiguration;
    public static TestLogger testLogger;
    protected DriverFactory driverFactory;
    protected WebDriverWait wait;
    protected Device device;
    protected String test_name;

    public static long getCurrentTime() {
        return CURRENT_TIME;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    protected static long CURRENT_TIME;
    protected String pathToCsv = "src/test/Login data.csv";
    protected String configurationPath="src/test/configuration file.txt";

    public static TestLogger getTestLogger() {
        return testLogger;
    }

    public static void setTestLogger(TestLogger testLogger) {
        MobileTest.testLogger = testLogger;
    }

    public static void setTestConfiguration(Configuration testConfiguration) {
        MobileTest.testConfiguration = testConfiguration;
    }

    public static long setCURRENT_TIME() {
        MobileTest.CURRENT_TIME = System.currentTimeMillis();
        return CURRENT_TIME;
    }

    @BeforeAll
    public void resetTimer(){
       System.out.println("--------Test suite started-----");
        if(CURRENT_TIME==0)
            CURRENT_TIME = System.currentTimeMillis();
        if(testConfiguration==null){
            resetConfigurations();
            if (testLogger==null){
                resetLogger();
            }
        }
        driverFactory=new DriverFactory(testConfiguration.getAccessKey(),testConfiguration.getCloudUrl());
        device=((TestRunner)Thread.currentThread()).getDevice();
        driverFactory.setDevice(device);


    }

    public void writeRunFile(String value){
        String fileName = "Result Files/RUN_"+CURRENT_TIME+"/"+device.getName()+ ".txt";
        try {
            Path pathToFile = Paths.get(fileName);
            Files.createDirectories(pathToFile.getParent());
            File file = new File(String.valueOf(pathToFile));
            file.createNewFile(); // if file already exists will do nothing
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(value);
            System.out.println("file data written at: "+file);
            bw.close();
        }
        catch (IOException e){
            System.out.println("couldnt write to file");
            e.printStackTrace();
            System.exit(-1);
        }
    }  public static void writeLogFile(){
        String fileName = "Result Files/RUN_"+CURRENT_TIME+"/"+"Summery Log.txt";
        try {
            Path pathToFile = Paths.get(fileName);
            Files.createDirectories(pathToFile.getParent());
            File file = new File(String.valueOf(pathToFile));
            file.createNewFile(); // if file already exists will do nothing
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(testLogger.toString());
            bw.close();
        }
        catch (IOException e){
            System.out.println("couldnt write to file");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        System.out.println("-----"+test_name+" finished\n");

    }
    public static TestLogger resetLogger(){
        testLogger=new TestLogger();
        return testLogger;

    }

    public void resetConfigurations(){
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(configurationPath));
            testConfiguration = new Gson().fromJson(csvReader, Configuration.class);
            driverFactory=new DriverFactory(testConfiguration.getAccessKey(),testConfiguration.getCloudUrl());
            System.out.println(testConfiguration.toString());
        } catch (FileNotFoundException e) {
            System.out.println("failed to open configuration csv");
            e.printStackTrace();
        }
    }
    public void printExeption(Exception e){
        test_status="TEST "+test_name+" failed\n"+e.toString()+"\n";
        writeRunFile(test_status);
        testLogger.addDFail(device,e.toString()+"\n");
        assertTrue(test_status,false);
    }
    public void printAssertionError(AssertionError e) {
        test_status="TEST "+test_name+" failed \n"+e.toString()+"\n";
        writeRunFile(test_status);
        testLogger.addDFail(device,e.toString()+"\n");
        fail(test_status);

    }
    public void printSeccess(){
        test_status="TEST "+test_name+" passed\n";
        writeRunFile(test_status);
        System.out.println(test_status);
        testLogger.addDPassed(device);
    }


};

