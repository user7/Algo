package geekbrains.sorts;

public class QuickSort {

    public static <T extends Comparable<T>> void qsort(T[] a, int left, int right) {
        if (left >= right)
            return;
        swap(a, left, (left + right) / 2);
        int last = left;
        for (int i = left + 1; i <= right; ++i) {
            if (a[i].compareTo(a[left]) < 0) {
                swap(a, ++last, i);
            }
        }
        swap(a, left, last);
        qsort(a, left, last - 1);
        qsort(a, last + 1, right);
    }

    static <T> void swap(T[] a, int i, int j) {
        if (i != j) {
            T tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
}
