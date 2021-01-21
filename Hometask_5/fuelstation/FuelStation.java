package fuelstation;

import vehicles.Vehicle;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FuelStation {
    private BlockingQueue<Vehicle> queue = new ArrayBlockingQueue<>(3);
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    public void launch() {
        while (true) {
            executorService.submit(() -> this.refuelVehicle());
        }
    }

    private void refuelVehicle() {
        Vehicle v1 = null;
        try {
            v1 = queue.take();
            System.out.println(v1.getId() + " started refueling...");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(v1.getId() + " left the gas station..");
    }


    public void takeTheLine(Vehicle v) {
        try {
            queue.put(v);
            System.out.println(v.getId() + " came to gas station...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}