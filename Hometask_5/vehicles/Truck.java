package vehicles;

import java.util.function.Consumer;

public class Truck extends Vehicle {

    public Truck(String id, Consumer consumer) {
        super(consumer);
        this.fuelConsumption = 15;
        this.tankCapacity =60;
        this.id = "Truck_"+ id;
    }


}


