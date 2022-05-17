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
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static <T extends Comparable<T>> void insertionSort(T a[], int left, int right) {
        for (int x = left + 1; x <= right; ++x) {
            T tmp = a[x];
            for (int y = x - 1; ; y--) {
                if (y >= left && a[y].compareTo(tmp) > 0)
                    a[y + 1] = a[y];
                else {
                    a[y + 1] = tmp;
                    break;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void hybridSort(T a[], int left, int right, int threshold) {
        if (right - left + 1 < threshold) {
            insertionSort(a, left, right);
            return;
        }
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
}
