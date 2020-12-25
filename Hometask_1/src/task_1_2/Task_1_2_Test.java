package task_1_2;

import java.util.ArrayList;
import java.util.Arrays;

public class Task_1_2_Test {

    public static void main(String[] args) {
        //Task_1 test
        Integer[] arr = {3,5,7,9,10,20,-200};
        System.out.println(Arrays.toString(arr));
        Task_1.changeElements(arr,0,2);
        System.out.println(Arrays.toString(arr));

        //Task2_test
        ArrayList<Integer> arrayList= new Task_2<Integer>().makeArrayListFromArray(arr);
        System.out.println(arrayList);

    }
}
