package task_1_2;

import java.util.ArrayList;
import java.util.Arrays;

public class Task_1{

    //task_1
    public static <T> boolean changeElements(T [] array, int index1, int index2){
        T element;
        if (index1> array.length-1 && index1> array.length-1){
            System.out.println("Provided indexes of elements are out of bounds of array");
            return false;
        }
        element = array[index1];
        array[index1]=array[index2];
        array[index2] = element;
        return  true;
    }

}
