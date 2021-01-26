package homework_6;

import java.util.Arrays;

public class Task_3 {
    public boolean chekElementsOfArray( int[] array){
        if (array == null){
            throw new NullPointerException();
        }
        if (array.length == 0){
            throw new IllegalArgumentException("The length of array could not be 0 ");
        }
         long countOfOne = Arrays.stream(array)
                            .filter(x->x==1)
                            .count();
        long countOfFour = Arrays.stream(array)
                            .filter(x->x==4)
                             .count();
        if (countOfFour==0 || countOfOne==0){
            return false;
        }
        return countOfFour+countOfOne == array.length;

    }
}
