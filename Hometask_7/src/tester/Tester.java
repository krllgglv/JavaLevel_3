package tester;

import annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Tester {
        private static List<Method> listOfMethods;
        private static List<Annotation> listOfAnnotations;

    public static void start(Class testClass) {


        listOfMethods = findMethodsWithAnnotations(testClass.getDeclaredMethods());
        listOfAnnotations = findAllAnnotations(listOfMethods);
        checkQuantityOfBeforeSuiteAnnotations(listOfAnnotations);
        checkQuantityOfAfterSuiteAnnotations(listOfAnnotations);
        provideSortedListOfAnnotatedMethods(listOfMethods);
        doTests(listOfMethods,testClass);


    }

    public static void start(String name) {
        Class testClass = null;
        try {
            testClass = Class.forName("tests_providers." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        listOfMethods = findMethodsWithAnnotations(testClass.getDeclaredMethods());
        listOfAnnotations = findAllAnnotations(listOfMethods);
        checkQuantityOfBeforeSuiteAnnotations(listOfAnnotations);
        checkQuantityOfAfterSuiteAnnotations(listOfAnnotations);
        provideSortedListOfAnnotatedMethods(listOfMethods);
        doTests(listOfMethods,testClass);


    }

    private static void checkQuantityOfBeforeSuiteAnnotations(List<Annotation> list) {
        int countB = 0;


        for (Annotation annotation : list) {
            if (annotation.annotationType().getSimpleName().equals("BeforeSuite")) {
                countB++;
            }
        }
        if (countB > 1) {
            throw new RuntimeException("There is to many methods with annotation @BeforeSuite");
        }
    }

    private static void checkQuantityOfAfterSuiteAnnotations(List<Annotation> list) {
        int countA = 0;
        for (Annotation annotation : list) {
            if (annotation.annotationType().getSimpleName().equals("BeforeSuite")) {
                countA++;
            }
        }
        if (countA > 1) {
            throw new RuntimeException("There is to many methods with annotation @AfterSuite");
        }
    }


    private static List<Method> provideSortedListOfAnnotatedMethods(List<Method> list) {
        Method start = null;
        Method end = null;
        Iterator<Method> iterator = list.iterator();
        while (iterator.hasNext()){
            Method method = iterator.next();
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation.annotationType().getSimpleName().equals("BeforeSuite")) {
                    start = method;
                    iterator.remove();
                }
                if (annotation.annotationType().getSimpleName().equals("AfterSuite")) {
                    end = method;
                    iterator.remove();
                }
            }
        }
        list.sort((x,y)->x.getDeclaredAnnotation(Test.class).priority()- y.getDeclaredAnnotation(Test.class).priority());
        if (start != null) {
            list.add(0, start);
        }
        if (end != null) {
            list.add(end);
        }
        return list;
    }

    private static ArrayList<Method> findMethodsWithAnnotations(Method[] methods) {
        ArrayList<Method> list = new ArrayList<>();
        for (Method method : methods) {

            if (method.getDeclaredAnnotations().length != 0) {
                list.add(method);
            }
        }
        if (list.size() == 0) {
            throw new RuntimeException("Provided class does not contain any method to test");
        }
        return list;
    }
    private static void doTests(List<Method> listOfMethods, Class testClass){
        try {
            Object o = testClass.getConstructor().newInstance();
            for (Method method : listOfMethods) {
                if (Modifier.isPrivate(method.getModifiers())) {
                    invokePrivateMethod(o, method);
                } else {
                    method.invoke(o);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


    }
    private static ArrayList<Annotation> findAllAnnotations(List<Method> methods){
        ArrayList<Annotation> list = new ArrayList<>();
        for (Method method : methods) {
            list.addAll(Arrays.asList(method.getDeclaredAnnotations()));
        }
        return list;
    }
    private static void invokePrivateMethod(Object o, Method method){
        method.setAccessible(true);
        try {
            method.invoke(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        method.setAccessible(false);
    }

}
