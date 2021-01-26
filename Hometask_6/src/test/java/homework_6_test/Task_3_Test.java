package homework_6_test;

import homework_6.Task_3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class Task_3_Test {
    Task_3 t3 = new Task_3();

    @ParameterizedTest
    @MethodSource("resultProvider")
    void shouldCheckIfArrayContainsOnlyNumbers1and4(boolean expected, int[] source) {
        Assertions.assertEquals(expected, t3.chekElementsOfArray(source));
    }

    @Test
    void shouldThrowNullPointerExceptionWhenInputIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> t3.chekElementsOfArray(null));
    }
    @Test
    void shouldIllegalArgumentExceptionWhenInputArrayContainsNoElements(){
        Assertions.assertThrows(IllegalArgumentException.class,()-> t3.chekElementsOfArray(new int[0]));
    }

    private static Stream<Arguments> resultProvider() {
        return Stream.of(
                Arguments.arguments(true, new int[]{1, 1, 1, 4, 4, 1, 4, 4}),
                Arguments.arguments(false, new int[]{1, 1, 1, 1, 1, 1}),
                Arguments.arguments(false, new int[]{4, 4, 4, 4}),
                Arguments.arguments(false, new int[]{1, 4, 4, 1, 1, 4, 3})

        );
    }
}
