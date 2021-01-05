import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import java.util.LinkedList;
import java.util.List;


public class TestRunner implements Runnable {
    private String Device;
    private String name;
    private List<ClassSelector> testSelectors;
    private List<String> testNames;


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
        testSelectors=new LinkedList<ClassSelector>() ;
        for (String name:testNames)
        {testSelectors.add(selectClass(name));
        }
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

}