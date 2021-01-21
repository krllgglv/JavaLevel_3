package vehicles;

import java.util.function.Consumer;

public class Bus extends Vehicle {
    public Bus (String id, Consumer consumer) {
        super(consumer);
        this.fuelConsumption = 7.5;
        this.tankCapacity =40;
        this.id = "Bus_"+id;
    }

}
