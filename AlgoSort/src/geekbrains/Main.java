package geekbrains;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import geekbrains.sorts.BubbleSort;

public class Main {

    public static void main(String[] args) {
        rng = new Random(12348);
        Laptop[] array = generateRandomLaptops(10000);
        counts = countElements(array);
        testSort("librarySort", array);
        testSort("bubbleSort", array);
    }

    static HashMap<Laptop, Integer> countElements(Laptop[] array) {
        var m = new HashMap<Laptop, Integer>();
        for (var a : array) {
            m.compute(a, (k, v) -> v == null ? 1 : v + 1);
        }
        return m;
    }

    static HashMap<Laptop, Integer> counts;

    static void testSort(String name, Laptop[] array) {
        try {
            // prepare, copy data
            Method method = Main.class.getDeclaredMethod(name, Laptop[].class);
            Laptop[] arrayCopy = new Laptop[array.length];
            System.arraycopy(array, 0, arrayCopy, 0, array.length);

            // mark time, sort
            long time = System.currentTimeMillis();
            Object result = method.invoke(null, new Object[]{arrayCopy});
            long delta = System.currentTimeMillis() - time;

            // check it's actually sorted
            int broken = 0;
            for (int i = 0; i + 1 < arrayCopy.length; ++i) {
                if (arrayCopy[i].compareTo(arrayCopy[i + 1]) > 0) {
                    System.out.printf("%20s broken order [%d] > [%d]\n", name, i, i + 1);
                    if (++broken > 3)
                        break;
                }
            }

            // check sorted array has same elements
            var counts2 = countElements(arrayCopy);
            for (var k : counts.keySet()) {
                int c1 = counts.getOrDefault(k, 0);
                int c2 = counts2.getOrDefault(k, 0);
                if (c1 != c2) {
                    System.out.printf("%20s different counts %d and %d for %s\n", name, c1, c2, k.toString());
                }
            }

            // print result
            System.out.printf("%20s %8.3fs\n", name, delta / 1000.);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("failed to test " + name);
            e.printStackTrace();
        }
    }

    static Laptop[] generateRandomLaptops(int n) {
        Laptop array[] = new Laptop[n];
        for (int i = 0; i < n; ++i) {
            array[i] = new Laptop(
                    randomFromRange(500, 2000, 50),
                    randomFromRange(4, 24, 4),
                    Brand.values()[randomFromRange(0, Brand.values().length - 1, 1)]);
        }
        return array;
    }

    static Random rng;

    static int randomFromRange(int from, int to, int step) {
        return rng.nextInt((to - from) / step + 1) * step + from;
    }

    static void librarySort(Laptop[] array) {
        Arrays.sort(array);
    }

    static void bubbleSort(Laptop[] array) {
        BubbleSort.sort(array);
    }
}