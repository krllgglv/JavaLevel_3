package tests_providers;

import annotations.*;


public class TestProvider_2 {
    @BeforeSuite
    public void testMethodStart() {
        System.out.println("Test with annotation @BeforeSuite.  Starting tests...");
    }


    @Test(priority = 1)
    public void test1(){
        System.out.println("Test with priority 1");
    }
    @Test()
    public void testDefault(){
        System.out.println("Test with default priority");
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
