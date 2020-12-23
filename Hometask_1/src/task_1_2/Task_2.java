package task_1_2;

import java.util.ArrayList;
import java.util.Arrays;

public class Task_2 <T> {
    //task_2
    public ArrayList<T> makeArrayListFromArray(T [] array){

        return new ArrayList<>(Arrays.asList(array));

    }
}
