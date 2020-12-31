//import org.junit.Before;
//import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class temp extends MobileTest {
//    @BeforeAll
//    public static void resetTimer(){
//        this.CURRENT_TIME = System.currentTimeMillis();
//
//    }
    public static void main(String[] args) {
        System.out.println("main");

    }
    @BeforeEach
    public void setUp() {
        System.out.println("in subclass before");
    }

    @Test
    public void test1() {
//        writeRunFile("cxvxvcxv");
        System.out.println("in test1 " + CURRENT_TIME);
    }

    @Test
    public void test2() {
//        writeRunFile("cxvxvcxv");
        System.out.println("in test2 + " + CURRENT_TIME);
    }
}
