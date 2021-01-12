package ru.chat.geekbrains.task1;

public class Task1 {
    private final Object lock = new Object();
    private volatile char currentLetter = 'A';



    public void printA() {
        synchronized (lock) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'A') {
                        lock.wait();
                    }
                    System.out.print(currentLetter);
                    currentLetter = 'B';
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (lock) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B') {
                        lock.wait();
                    }
                    System.out.print(currentLetter);
                    currentLetter = 'C';
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void printC() {
        synchronized (lock) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'C') {
                        lock.wait();
                    }
                    System.out.print(currentLetter);
                    currentLetter = 'A';
                    lock.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
