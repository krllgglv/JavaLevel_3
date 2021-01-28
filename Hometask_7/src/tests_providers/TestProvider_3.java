package tests_providers;

import annotations.AfterSuite;
import annotations.Test;

public class TestProvider_3 {



    @Test(priority = 1)
    public void test1(){
        System.out.println("Test with priority 1");
    }

    public void test_noAnn(){
        System.out.println("Test without annotation");
    }
    public void test_noAnn2(){
        System.out.println("Test without annotation");
    }
    @Test()
    private void testDefault(){
        System.out.println("Private test with default priority");
    }
    @Test(priority = 10)
    public void test10(){
        System.out.println("Test with priority 10");
    }
    @Test(priority = 3)
    public void test3(){
        System.out.println("Test with priority 3");
    }
    @Test(priority = 3)
    public void test3_1(){
        System.out.println("Test with priority 3");
    }
    @AfterSuite
    public void testMethodEnd() {
        System.out.println("Test with annotation @AfterSuite.  The end of tests....");
    }


}
