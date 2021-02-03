import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestThreadPool {
    private static String allTests="AppStoreTest_Android,AppStoreTest_ios,EriBankTest_Android,EriBankTest_ios,EspnTest_Android,EspnTest_ios,TapTheDotTest";
    protected static   Configuration testConfiguration;
    protected static String configurationPath="src/test/configuration file.txt";
    private static List<String> machines;
    private static List<Device> freeDevs;
    protected static long CURRENT_TIME;
    protected static Instant startTime,thisTime;;
    protected static TestLogger testLogger;
    int iteration=1;

    public static void main(String[] args) throws InterruptedException, UnirestException, JSONException {
        resetConfigurations();
        freeDevs = new RestProjectAPI().getFreeDevices();
        long startTestTims=MobileTest.setCURRENT_TIME();
        testLogger=TestLogger.getTestLogger();
        TestRunner.startRunResetInfo(startTestTims,testConfiguration);
        testLogger.setDevrequested(testConfiguration.getNumOfDevices());
        testLogger.setNumDevices(freeDevs.size());
        List<TestRunner> testRunners = new ArrayList<>();
        for (Device dev : freeDevs) {
            TestRunner test = new TestRunner(testConfiguration.getTestToRun(), dev,testConfiguration.getRepeat(),testConfiguration.getNG_profile());
            testRunners.add(test);
            test.start();
        }
        for (TestRunner test : testRunners) {
            test.join();
        }
        MobileTest.writeLogFile();
        MobileTest.writeNVprofileLogFile();
    }

    public static class RestProjectAPI {
        List<Device> devArray;
        List<Device> mydevLst;
        private com.mashape.unirest.http.HttpResponse<JsonNode> responseString;
        private HttpResponse<InputStream> responseInputStream;
        public List<Device> getDevices() throws UnirestException, JSONException {
            String url = testConfiguration.getCloudUrl() + "/api/v1/devices";
            responseString = Unirest.get(url).header("Authorization", "Bearer " + testConfiguration.getAccessKey()).header("content-type", "application/json").asJson();
            System.out.println(responseString);
            JSONArray jsonArray = (JSONArray) responseString.getBody().getObject().get("data");
            devArray = new ArrayList<>(Arrays.asList(new Gson().fromJson(String.valueOf(jsonArray), Device[].class)));
            //get deviced with conditions
            if(!testConfiguration.getSerialNumber().equals("")){
                devArray.removeIf(dev -> (!dev.getUdid().equals(testConfiguration.getSerialNumber())));
            } if(testConfiguration.getTestToRun().equals("TapTheDotPlay")||testConfiguration.getTestToRun().equals("TapTheDotLogin")||testConfiguration.getTestToRun().equals("SimulateCapture")||testConfiguration.getTestToRun().equals("MockAuthentication")||testConfiguration.getTestToRun().equals("EspressoTest")){
                devArray.removeIf(dev -> (!dev.getOs().equals("Android")));
            }
            return devArray;
        }
        public List<Device> getFreeDevices() throws UnirestException {
            int num=testConfiguration.getNumOfDevices();
            getDevices();
            mydevLst=new ArrayList<Device>();
            if(num<=0)
                return null;
            for (Device dev:devArray) {
                if(dev.getCurrentUser().equals(testConfiguration.getUsername())){
                    mydevLst.add(dev);
                    num--;
                    if(num<=0)
                        return mydevLst;}
            }
            for (Device dev:devArray) {
                if(dev.getDisplayStatus().equals("Available")){
                    mydevLst.add(dev);
                    num--;
                    if(num<=0)
                        return mydevLst;}
            }
            return mydevLst;
        }
    }

    public static void resetConfigurations(){
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(configurationPath));
            testConfiguration = new Gson().fromJson(csvReader, Configuration.class);
            MobileTest.setTestConfiguration(testConfiguration);
            System.out.println(testConfiguration.toString());
        } catch (FileNotFoundException e) {
            System.out.println("-------failed to open configuration csv!--------");
            e.printStackTrace();
        }
    }
}

