import com.experitest.appium.SeeTestClient;
import com.google.gson.Gson;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Driver;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.experitest.client.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//set test timeout(5 mins for reboot)
@Timeout(value = 10, unit = TimeUnit.MINUTES)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class  MobileTest implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    protected AppiumDriver driver;
    protected String test_status;
    public static  Configuration testConfiguration;
    public static TestLogger testLogger;
    protected DriverFactory driverFactory;
    protected WebDriverWait wait;
    protected Device device;
    protected String test_name;
    protected SeeTestClient seeTestClient ;
    protected int failures;
    public static long getCurrentTime() {
        return CURRENT_TIME;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    protected static long CURRENT_TIME;
    protected String pathToCsv = "src/test/Login data.csv";
    protected String configurationPath="src/test/configuration file.txt";
    protected Long testStartTime;
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
        if(CURRENT_TIME==0)
            CURRENT_TIME = System.currentTimeMillis();
        if(testConfiguration==null){
            resetConfigurations();
        }
        driverFactory=new DriverFactory(testConfiguration.getAccessKey(),testConfiguration.getCloudUrl());
        device=((TestRunner)Thread.currentThread()).getDevice();
        driverFactory.setDevice(device);
        testLogger=TestLogger.getTestLogger();

    }
    @BeforeEach
    public void reset(){
        testStartTime=System.currentTimeMillis();
        if(failures>=3)
            failures=0;
    }

    public void writeSupportData(){
        String fileName = "Result Files/RUN_"+CURRENT_TIME+"/"+device.getNV_profile()+"/"+device.getName()+"/"+test_name+ failures+" supportData.zip";
        Path pathToFile = Paths.get(fileName);
        try {
            Files.createDirectories(pathToFile.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(String.valueOf(pathToFile));
        try {
            file.createNewFile(); // if file already exists will do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            seeTestClient.collectSupportData(fileName, null, null, null, null, null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void writeRunFile(String value){
        String fileName = "Result Files/RUN_"+MobileTest.getCurrentTime()+"/"+device.getNV_profile()+"/"+device.getName()+"/"+device.getName()+ ".txt";
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
            bw.write(testLogger.printIterationLog());
            bw.close();
        }
        catch (IOException e){
            System.out.println("couldnt write to file");
            e.printStackTrace();
            System.exit(-1);
        }
    }public static void writeNVprofileLogFile(){
        String fileName = "Result Files/RUN_"+CURRENT_TIME+"/"+"NV Profile Summery Log.txt";
        try {
            Path pathToFile = Paths.get(fileName);
            Files.createDirectories(pathToFile.getParent());
            File file = new File(String.valueOf(pathToFile));
            file.createNewFile(); // if file already exists will do nothing
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(testLogger.printProfileLog());
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
        long duration = System.currentTimeMillis() - testStartTime;
        testLogger.addTestTime(device.getNV_profile(),test_name,duration);
        System.out.println("-----"+test_name+" finished in "+duration+ " miliseconds----\n");

    }
    //    public static TestLogger resetLogger(){
    //        testLogger=TestLogger.getTestLogger();
    //        return testLogger;
    //
    //    }

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
        failures++;
        test_status="-- TEST "+test_name+" failed "+failures+" times: "+e.toString()+"\n";
        writeRunFile(test_status);
        testLogger.addDFail(device,e.toString()+"\n");
        writeSupportData();
        if(failures==2){
            // reboot and wait 5 minutes for the device to reload
            // unlock the device after reboot
            if(seeTestClient.reboot(300000)){
                System.out.println(device.getName()+" "+test_name+ " reboot");
                seeTestClient.deviceAction("Unlock");
            }
        }
        fail(test_status);
    }

    public void printAssertionError(AssertionError e) {
        failures++;
        test_status="-- TEST "+test_name+" failed "+failures+" times: "+e.toString()+"\n";
        writeRunFile(test_status);
        testLogger.addDFail(device,e.toString()+"\n");
        writeSupportData();
//        fail(test_status);
        if(failures==2){
            // reboot and wait 3 minutes for the device to reload
            // unlock the device after reboot
            if(seeTestClient.reboot(300000)){
                System.out.println(test_name+ "reboot");
                seeTestClient.deviceAction("Unlock");

            }
        }
        fail(test_status);

    }
    public void printSeccess(){
        test_status="-- TEST "+test_name+" passed\n";
        writeRunFile(test_status);
        System.out.println(test_status);
        testLogger.addDPassed(device);
    }
    //-------------------test timer
    private static final Logger logger = Logger.getLogger(MobileTest.class.getName());
    private static final String START_TIME = "start time";

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        getStore(context).put(START_TIME, System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        long startTime = getStore(context).remove(START_TIME, long.class);
        long duration = System.currentTimeMillis() - startTime;
        testLogger.addTestTime(device.getNV_profile(),testMethod.getName(),duration);

        logger.info(() ->
                String.format("Method [%s] took %s ms.", testMethod.getName(), duration));
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }


};

