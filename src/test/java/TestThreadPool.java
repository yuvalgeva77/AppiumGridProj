
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestThreadPool {



        public static void main(String[] args)
        {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

            for (int i = 1; i <= 5; i++)
            {
               TestRunner test=new TestRunner();
                System.out.println("-------Created : " + i+"---------\n");
                try {
                    Thread.sleep(10899);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executor.execute(test);
            }
            executor.shutdown();
        }
    }

