package worldofzuul.util;

import java.util.Arrays;

public class BetterArrays {
    public static <T>T[] push(T[] elements, T element) {
        T[] array = Arrays.copyOf(elements, elements.length + 1);
        array[elements.length] = element;

        return array;
    }

    public static <T>T[] merge(T[] elements, T[] elementsToAdd) {
        T[] array = Arrays.copyOf(elements, elements.length + elementsToAdd.length);
        System.arraycopy(elementsToAdd, 0, array, elements.length, elementsToAdd.length);

        return array;
    }
}
