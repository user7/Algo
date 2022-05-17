package geekbrains.sorts;

import java.util.function.Function;

public class BucketSort {

    public static <T extends Comparable<T>> void sortCopy(T[] arr, T[] arr2, Function<T, Integer> mapper,
                                                          int min, int max, int step)
    {
        if (arr.length == 0)
            return;
        assert(arr.length == arr2.length);

        // counts
        int cnt[] = new int[(max - min) / step + 1];
        for (var a : arr) {
            cnt[(mapper.apply(a) - min) / step]++;
        }

        // convert array of counts to array of offsets
        int off = 0;
        for (int i = 0; i < cnt.length; ++i) {
            int c = cnt[i];
            cnt[i] = off;
            off += c;
        }

        // rearrange while copying to array2
        for (var a : arr) {
            int i = cnt[(mapper.apply(a) - min) / step]++;
            arr2[i] = a;
        }
    }
}
