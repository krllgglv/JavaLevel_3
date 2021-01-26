package homework_6;

import java.util.Arrays;

public class Task2 {

    public int[] getNumbersAfterLast4(int[] array){

        int[] result;
        if (array == null){
            throw new NullPointerException();
        }
        if (array.length == 0){
            throw new IllegalArgumentException("The length of array could not be 0 ");
        }

        int count=-1;
        for (int i = 0; i < array.length; i++) {
            if (array[i]==4){
                count=i;
            }
        }
        if (count==-1){
            throw new RuntimeException();
        }
        if (count==array.length-1){
            return null;
        } else {
                result = new int[array.length-1 - count];
            for (int i = count+1; i < array.length; i++) {
                result[i-count-1] = array[i];
            }
        }
        System.out.println(Arrays.toString(result));
        return result;
    }

}
