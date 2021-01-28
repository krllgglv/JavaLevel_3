import tester.Tester;
import tests_providers.TestProvider_2;

public class Launcher {
    public static void main(String[] args) {
        Tester.start(TestProvider_2.class);

        System.out.println("-------------------------------");
        Tester.start("TestProvider_3");
    }
}
