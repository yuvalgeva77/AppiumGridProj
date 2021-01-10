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
import java.util.LinkedList;
import java.util.List;


public class TestRunner implements Runnable {
    private String Device;
    private String name;
    private List<MethodSelector> testSelectors;
    private List<String> testNames;
    protected long CURRENT_TIME;



    public String getName() {
        return name;
    }

    public static void main(String[] args) {
    }
//    @Test
//    void exceptionTesting() {
//        Executable closureContainingCodeToTest = () -> {throw new IllegalArgumentException("a message");};
//        Throwable throwable = assertThrows(IllegalArgumentException.class, closureContainingCodeToTest, "a message");
//        assertEquals("a message", throwable.getMessage());

    public TestRunner(List<String> testNames) {
        this.testNames = testNames;
        testSelectors=new LinkedList<org.junit.platform.engine.discovery.MethodSelector>() ;
        for (String name:testNames)
//        {testSelectors.add(selectClass(name));
            testSelectors.add(selectMethod(name));

        }





    @Override
    public void run() {
        System.out.println(  Thread.currentThread().getName());
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
    }

    public void writeSummaryFile(String value){
        String fileName = "Result Files/RUN_"+CURRENT_TIME+"/"+ "Overall Summary.txt";
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
