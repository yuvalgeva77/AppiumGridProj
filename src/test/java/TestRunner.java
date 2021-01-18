import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.platform.engine.discovery.MethodSelector;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class TestRunner extends Thread {
    private Device device;
    private String name;
    private List<MethodSelector> testSelectors;
    private List<String> testNames;
    protected static long  startRunTests;
    protected static Configuration configuration;
    protected  Instant startTime,thisTime;;
    int iteration=0;
    static int minsRepeat;
    //    private static String suite_ios="AppStoreTest_ios#AppStoreDownload,AppStoreTest_ios#AppStoreTop10,EriBankTest_ios#EriBankLogin,EriBankTest_ios#EriBankPayment,EspnTest_ios#";
//    private static String suite_Andtoid ="AppStoreTest_Android#AppStoreDownload,AppStoreTest_Android#AppStoreTop10,EriBankTest_Android#EriBankLogin,EriBankTest_Android#EriBankPayment,EspnTest_Android#Espn,TapTheDotTest#TapTheDotLogin,TapTheDotTest#TapTheDotPlay";
    Map<String, String> suite_ios = new HashMap<String, String>() {{
        put("AppStoreDownload", "AppStoreTest_ios#AppStoreDownload");
        put("AppStoreTop10", "AppStoreTest_ios#AppStoreTop10");
        put("EriBankLogin", "EriBankTest_ios#EriBankLogin");
        put("EriBankPayment", "EriBankTest_ios#EriBankPayment");
        put("EspnMenu", "EspnTest_ios#EspnMenuButtons");
        put("EspnMenuButtons", "EspnTest_ios#TapTheDotLogin");
    }};
    Map<String, String> suite_Andtoid = new HashMap<String, String>() {{
        put("AppStoreDownload", "AppStoreTest_Android#AppStoreDownload");
        put("AppStoreTop10", "AppStoreTest_Android#AppStoreTop10");
        put("EriBankLogin", "EriBankTest_Android#EriBankLogin");
        put("EriBankPayment", "EriBankTest_Android#EriBankPayment");
        put("EspnMenu", "EspnTest_Android#EspnMenu");
        put("EspnMenuButtons", "EspnTest_Android#EspnMenuButtons");
        put("TapTheDotLogin", "TapTheDotTest#TapTheDotLogin");
        put("TapTheDotPlay", "TapTheDotTest#TapTheDotPlay");
    }};


    public static void main(String[] args) {
    }
//    @Test
//    void exceptionTesting() {
//        Executable closureContainingCodeToTest = () -> {throw new IllegalArgumentException("a message");};
//        Throwable throwable = assertThrows(IllegalArgumentException.class, closureContainingCodeToTest, "a message");
//        assertEquals("a message", throwable.getMessage());

    public Device getDevice() {
        return device;
    }

    public TestRunner(String testTitle, Device device,int minsRepeat) {
        testSelectors=new LinkedList<MethodSelector>();
        this.minsRepeat=minsRepeat;
        this.device = device;
        if (testTitle.equals("all")) {
            seAlltSuite();
        } else {
            getTestForOs(testTitle);
        }
    }

    @Override
    public void run() {
        while (toContinue()) {
            iteration++;
            writeRunFile("-----iteration-----: "+iteration+"\n");
            System.out.println("Thread name: " + Thread.currentThread().getName() + " Device Name: " + device.getName()+" iteration number: "+iteration);
            final LauncherDiscoveryRequest request =
                    LauncherDiscoveryRequestBuilder.request()
//                        .selectors(selectClass(EriBankTest_Android.class),selectMethod("temp#test2"))
                            .selectors(testSelectors)
//                        .selectors(selectMethod("temp#test2"))
                            .build();
            final Launcher launcher = LauncherFactory.create();
            final SummaryGeneratingListener listener = new SummaryGeneratingListener();
            launcher.registerTestExecutionListeners(listener);
            launcher.execute(request);
            TestExecutionSummary summary = listener.getSummary();
            long testFoundCount = summary.getTestsFoundCount();
            List<Failure> failures = summary.getFailures();
            System.out.println("getTestsSucceededCount() - " + summary.getTestsSucceededCount());
            failures.forEach(failure -> System.out.println("failure - " + failure.getException()));
           TestLogger.getTestLogger().finishIteration(iteration,device);
        }
    }

    //    public void writeSummaryFile(String value){
//        String fileName = "Result Files/RUN_"+CURRENT_TIME+"/"+ "Overall Summary.txt";
//        try {
//            Path pathToFile = Paths.get(fileName);
//            Files.createDirectories(pathToFile.getParent());
//            File file = new File(String.valueOf(pathToFile));
//            file.createNewFile(); // if file already exists will do nothing
//            FileWriter fw = new FileWriter(file,true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(value);
//            System.out.println("file data written at: "+file);
//            bw.close();
//        }
//        catch (IOException e){
//            System.out.println("couldnt write to file");
//            e.printStackTrace();
//            System.exit(-1);
//        }
//
//    }
    protected void seAlltSuite(){
        if(device.getOs().equals("Android")){
            for (String name:suite_Andtoid.values())
                testSelectors.add(selectMethod(name));
        }
        else
            for (String name:suite_ios.values())
                testSelectors.add(selectMethod(name));
    }
    protected void getTestForOs(String testTitle) {
        testNames = Arrays.asList(testTitle.split(",").clone());

        for (String name : testNames) {
            if (device.getOs().equals("Android")) {
                testSelectors.add(selectMethod(suite_Andtoid.get(name)));
            } else {
                testSelectors.add(selectMethod(suite_ios.get(name)));
            }


        }
    }
    public boolean toContinue(){
        Long timePassed=System.currentTimeMillis()-MobileTest.getCurrentTime();
//        Long min=  TimeUnit.MILLISECONDS.toMinutes(thisTime);
        Long minPassed=  (timePassed)/60000;
        return (minPassed<minsRepeat);
    }
    public static long getStartRunTests() {
        return startRunTests;
    }

    public static void setStartRunTests(long startRunTests) {
        TestRunner.startRunTests = startRunTests;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        TestRunner.configuration = configuration;
    }
    public static void startRunResetInfo(long startRunTests,Configuration configuration){
        setStartRunTests( startRunTests);
        setConfiguration( configuration);
    }
    public void writeRunFile(String value){
        String fileName = "Result Files/RUN_"+MobileTest.getCurrentTime()+"/"+device.getName()+ ".txt";
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
    }
}
