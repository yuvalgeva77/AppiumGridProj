import com.google.gson.Gson;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TestThreadPool {
    private static String allTests="AppStoreTest_Android,AppStoreTest_ios,EriBankTest_Android,EriBankTest_ios,EspnTest_Android,EspnTest_ios,TapTheDotTest";
    protected static   Configuration testConfiguration;
    protected static String configurationPath="src/test/configuration file.txt";
    private static List<String> testNames;
    private static List<String> machines;
    protected long CURRENT_TIME;



    public static void main(String[] args) throws InterruptedException, UnirestException, JSONException {
        //run the all suite chosen in testNames on a selected number of devices
        // ( thread= device.  tasks in size of thread->each thread will run a task)
        resetConfigurations();
        new RestProjectAPI().getApplications();
        MobileTest.setCURRENT_TIME();
        MobileTest.resetLogger();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(testNames.size());
        for (int i=0;i<testConfiguration.getNumOfDevices();i++) {
//            List<String> testC = new LinkedList<>();
//            testC.add(testClassName);
            TestRunner test = new TestRunner(testNames);
//                System.out.println("-------Created : " + i+"---------\n");
            executor.execute(test);
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.DAYS);
        MobileTest.writeLogFile();
    }
//    public List<String> getDevices(){
//
//    }

    public static class RestProjectAPI {
        private com.mashape.unirest.http.HttpResponse<JsonNode> responseString;
        private HttpResponse<InputStream> responseInputStream;
//        private String urlBase = "https://qacloud.experitest.com/";   //TODO: modify hostname and port of your Reporter
//        private String user = "user";  //TODO: user name
//        private String password = ".....";  //TODO: user password
//        private String accessKey= ".....";  //TODO: user access key
//        String projectName = "projectName";//TODO: project name is here
//        String projectID = "projectID";//TODO: project ID is here
        public void getApplications() throws UnirestException, JSONException {
            String url = "https://qacloud.experitest.com" + "/api/v1/devices";
            responseString = Unirest.get(url).header("Authorization", "Bearer "+testConfiguration.getAccessKey()).header("content-type", "application/json").asJson();
           System.out.println(responseString);
           JSONArray arry = (JSONArray) responseString.getBody().getObject().get("data");

//            for (Object o: jsonArray) {
//                JSONObject deviceJson = (JSONObject) o;
//                if (deviceJson.getString("udid").equals(serialNumber)) {
//                    return deviceJson.getString("deviceOs");
//                }
//            }

//            responseString.getBody()
            //com.mashape.unirest.http.HttpResponse<JsonNode> responseString = Unirest.get(url).header("Authorization", "Bearer "+testConfiguration.getAccessKey()).header("content-type", "application/json").asJson();
//         //   JSONArray jsonArray = (JSONArray) responseString.getBody().getObject().get("data");
        }
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

