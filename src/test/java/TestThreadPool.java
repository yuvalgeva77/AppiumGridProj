import com.google.gson.Gson;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TestThreadPool {
    private static String allTests="AppStoreTest_Android,AppStoreTest_ios,EriBankTest_Android,EriBankTest_ios,EspnTest_Android,EspnTest_ios,TapTheDotTest";
    protected static   Configuration testConfiguration;
    protected static String configurationPath="src/test/configuration file.txt";
//    private static List<String> testNames;
    private static List<String> machines;
    private static List<Device> freeDevs;
    protected static long CURRENT_TIME;

    public static void main(String[] args) throws InterruptedException, UnirestException, JSONException {
        //run the all suite chosen in testNames on a selected number of devices
        // ( thread= device.  tasks in size of thread->each thread will run a task)
        resetConfigurations();
        while (!hasTimePassed()) {
            freeDevs = new RestProjectAPI().getFreeDevices();
            MobileTest.setCURRENT_TIME();
            MobileTest.resetLogger();
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(freeDevs.size());
//        for (int i=0;i<freeDevs.size();i++) {
            List<TestRunner> testRunners = new ArrayList<>();
            for (Device dev : freeDevs) {
//            List<String> testC = new LinkedList<>();
//            testC.add(testClassName);
                TestRunner test = new TestRunner(testConfiguration.getTestToRun(), dev);
                testRunners.add(test);
                test.start();
//                System.out.println("-------Created : " + i+"---------\n");
//            executor.execute(test);
            }
//        executor.shutdown();
//        executor.awaitTermination(60, TimeUnit.DAYS);
            for (TestRunner test : testRunners) {
                test.join();
            }
            MobileTest.writeLogFile();
        }
//    public List<String> getDevices(){
//
//    }
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
//                Gson gson = ;
//            List<Device> devLst = new ArrayList<Device>();
//
//            int i=0;
//            for (Object jdev:jsonArray) {
//                Device dev=gson.fromJson(String.valueOf(jsonArray.get(i)), Device.class);
//                devLst.add(dev);
//                i++;
//
//            }
            devArray = new ArrayList<>(Arrays.asList(new Gson().fromJson(String.valueOf(jsonArray), Device[].class)));

            if(!testConfiguration.getSerialNumber().equals("")){
                devArray.removeIf(dev -> (!dev.getUdid().equals(testConfiguration.getSerialNumber())));
//                for (Device dev:devArray) {
//                    if(!dev.getUdid().equals(testConfiguration.getSerialNumber())){
//                        devArray.remove(dev);
//                    }
                }
//                Device dev=gson.fromJson(String.valueOf(jsonArray.get(i)), Device.class);
//                devLst.add(dev);
//                i++;


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
            //get testClasses

        } catch (FileNotFoundException e) {
            System.out.println("-------failed to open configuration csv!--------");
            e.printStackTrace();
        }
    }
    public static boolean hasTimePassed(){
        Long thisTime= System.currentTimeMillis();
        Long timePassed=System.currentTimeMillis()-CURRENT_TIME;
        Long min=  TimeUnit.MILLISECONDS.toMinutes(thisTime);
        Long minPassed=  TimeUnit.MILLISECONDS.toMinutes(timePassed);
        return (minPassed<testConfiguration.getRepeat());
    }

}

