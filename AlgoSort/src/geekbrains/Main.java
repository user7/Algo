package geekbrains;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        rng = new Random(12348);
        Laptop[] array = generateRandomLaptops(10000);
        // testSort("librarySort", array);
    }

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

            // check array is actually sorted
            int broken = 0;
            for (int i = 0; i + 1 < arrayCopy.length; ++i) {
                if (arrayCopy[i].compareTo(arrayCopy[i + 1]) > 0) {
                    System.out.printf("%20s broken order [%d] > [%d]\n", i, i + 1);
                    if (++broken > 3)
                        break;
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
}
