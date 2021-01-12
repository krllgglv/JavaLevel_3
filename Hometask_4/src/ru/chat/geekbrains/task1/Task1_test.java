package ru.chat.geekbrains.task1;

public class Task1_test {
    public static void main(String[] args) {
        Task1 task1 = new Task1();
        Thread t1 = new Thread(() -> {
            task1.printA();
        });
        Thread t2 = new Thread(() -> {
            task1.printB();
        });
        Thread t3 = new Thread(() -> {
            task1.printC();
        });
        t1.start();
        t2.start();
        t3.start();

    }
}
