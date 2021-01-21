package vehicles;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public abstract class Vehicle {
    private CyclicBarrier barrier;
    private Lock lock;
    protected double fuelConsumption;
    protected double tankCapacity;
    protected String id;
    protected Consumer consumer;

    public String getId() {
        return id;
    }

    public Vehicle(Consumer consumer) {

        this.consumer = consumer;
        lock = new ReentrantLock();
    }

    public void move() {
        System.out.println(id + " is moving...");
        while (checkFuel()) {
            System.out.println(id + " is moving...");
        }
        consumer.accept(this);
    }

    private boolean checkFuel() {
        while (true) {
            try {
                Thread.sleep(3000);
                tankCapacity -= fuelConsumption * 3;
                if (tankCapacity <= 0) {
                    System.out.println("Tank of " + id + " is empty");
                    return false;
                } else {
                    return true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

