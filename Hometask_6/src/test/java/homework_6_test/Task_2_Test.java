package homework_6_test;

import homework_6.Task2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;


public class Task_2_Test {
    private Task2 t2 = new Task2();

    @ParameterizedTest
    @MethodSource("arraysProvider1")
    void shouldReturnAnArrayOfIntNumbersFollowingTheLastFour(int[] expected, int[] source) {
        Assertions.assertArrayEquals(expected, t2.getNumbersAfterLast4(source));
    }
    @ParameterizedTest
    @MethodSource("arraysProvider2")
    void shouldReturnNullIfDigit4IsTheLastElementOfSourceArray(int[] expected, int[] source) {
        Assertions.assertArrayEquals(expected, t2.getNumbersAfterLast4(source));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenInputIsNull(){
        Assertions.assertThrows(NullPointerException.class,()-> t2.getNumbersAfterLast4(null));
    }
    @Test
    void shouldIllegalArgumentExceptionWhenInputArrayContainsNoElements(){
        Assertions.assertThrows(IllegalArgumentException.class,()-> t2.getNumbersAfterLast4(new int[0]));
    }
    @Test
    void shouldThrowRuntimeExceptionWhenInputArrayDoesNotContainDigit4(){
        Assertions.assertThrows(RuntimeException.class,()-> t2.getNumbersAfterLast4(new int[]{1,2,3,5,6}));
    }

    private static Stream<Arguments> arraysProvider1() {
        return Stream.of(
                Arguments.arguments(new int[]{1, 7},new int[]{1,2,4,4,2,3,4,1,7}),
                Arguments.arguments(null, new int[]{1,4}),
                Arguments.arguments(null, new int[]{4}),
                Arguments.arguments(new int[]{1}, new int[]{4,1}),
                Arguments.arguments(new int[]{3,3,3,3,3,3,5}, new int[]{4,1,4,3,3,3,3,3,3,5})

        );
    }
    private static Stream<Arguments> arraysProvider2() {
        return Stream.of(
                Arguments.arguments(null, new int[]{1,4}),
                Arguments.arguments(null, new int[]{4}),
                Arguments.arguments(null, new int[]{1,2,3,3,3,3,3,3,4})

        );
    }
}
