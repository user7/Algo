package geekbrains.sorts;

public class BubbleSort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        int n = a.length;
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n - i - 1; ++j)
                if (a[j].compareTo(a[j + 1]) > 0)
                    swap(a, j, j + 1);
    }

    static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
