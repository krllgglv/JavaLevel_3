package vehicles;

import java.util.function.Consumer;

public class Car extends Vehicle{

    public Car(String id, Consumer consumer) {
        super(consumer);
        this.fuelConsumption = 2.5;
        this.tankCapacity =20;
        this.id = "Car_"+ id;
    }

}