

import fuelstation.FuelStation;
import vehicles.Bus;
import vehicles.Car;
import vehicles.Truck;
import vehicles.Vehicle;

import java.util.function.Consumer;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        FuelStation fs = new FuelStation();

        Consumer <Vehicle> consumer = new Consumer<Vehicle>() {
            @Override
            public void accept(Vehicle vehicle) {
                fs.takeTheLine(vehicle);
            }
        };



        new Thread(()->new Car("1",consumer).move()).start();
        new Thread(()->new Car("2",consumer).move()).start();
        new Thread(()->new Car("3",consumer).move()).start();
        new Thread(()->new Truck("1",consumer).move()).start();
        new Thread(()->new Truck("2",consumer).move()).start();
        new Thread(()->new Truck("3",consumer).move()).start();
        new Thread(()->new Bus("1",consumer).move()).start();
        new Thread(()->new Bus("2",consumer).move()).start();
        new Thread(()->new Bus("3",consumer).move()).start();
        fs.launch();
    }
}
