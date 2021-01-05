
import com.google.gson.Gson;
import org.junit.platform.engine.discovery.ClassSelector;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestThreadPool {
    private static String allTests="AppStoreTest_Android,AppStoreTest_ios,EriBankTest_Android,EriBankTest_ios,EspnTest_Android,EspnTest_ios,TapTheDotTest";
    protected static   Configuration testConfiguration;
    protected static String configurationPath="src/test/configuration file.txt";
    private static List<String> testNames;
    private static List<String> machines;



    public static void main(String[] args) {
        //run the all suite chosen in testNames on a selected number of devices
        // ( thread= device.  tasks in size of thread->each thread will run a task)
        resetConfigurations();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(testNames.size());
        for (int i=0;i<testConfiguration.getNumOfDevices();i++) {
//            List<String> testC = new LinkedList<>();
//            testC.add(testClassName);
            TestRunner test = new TestRunner(testNames);
//                System.out.println("-------Created : " + i+"---------\n");
            executor.execute(test);
        }
        executor.shutdown();
    }



    public static void resetConfigurations(){
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(configurationPath));
            testConfiguration = new Gson().fromJson(csvReader, Configuration.class);
            MobileTest.setTestConfiguration(testConfiguration);
            System.out.println(testConfiguration.toString());
            //get testClasses
            if(testConfiguration.getTestToRun().equals("all")){
                testNames = Arrays.asList(allTests.split(","));
            }
            else
                testNames = Arrays.asList(testConfiguration.getTestToRun().split(",").clone());
        } catch (FileNotFoundException e) {
            System.out.println("-------failed to open configuration csv!--------");
            e.printStackTrace();
        }
    }
}

