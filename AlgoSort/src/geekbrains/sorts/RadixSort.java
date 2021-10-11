package geekbrains.sorts;

import java.util.function.Function;

public class RadixSort {
    private static final int INSERTION_SORT_THRESHOLD = 64;

    static record Entry(int value, Object obj) implements Comparable<Entry> {
        @Override
        public int compareTo(Entry o) {
            return value - o.value;
        }
    }

    public static void sortEntries(Entry[] array, int offset, int end, int shift) {
        int[] last = new int[256];
        int[] pointer = new int[256];

        for (int x = offset; x < end; ++x) {
            ++last[(array[x].value >> shift) & 0xFF];
        }

        last[0] += offset;
        pointer[0] = offset;
        for (int x = 1; x < 256; ++x) {
            pointer[x] = last[x - 1];
            last[x] += last[x - 1];
        }

        for (int x = 0; x < 256; ++x) {
            while (pointer[x] != last[x]) {
                Entry entry = array[pointer[x]];
                int y = (entry.value >> shift) & 0xff;
                while (x != y) {
                    Entry temp = array[pointer[y]];
                    array[pointer[y]++] = entry;
                    entry = temp;
                    y = (entry.value >> shift) & 0xff;
                }
                array[pointer[x]++] = entry;
            }
        }
        if (shift > 0) {
            shift -= 8;
            for (int x = 0; x < 256; ++x) {
                int size = x > 0 ? pointer[x] - pointer[x - 1] : pointer[0] - offset;
                if (size > 64) {
                    sortEntries(array, pointer[x] - size, pointer[x], shift);
                } else if (size > 1) {
                    insertionSort(array, pointer[x] - size, pointer[x]);
                }
            }
        }
    }

    private static void insertionSort(Entry array[], int offset, int end) {
        for (int x = offset; x < end; ++x) {
            for (int y = x; y > offset && array[y - 1].value > array[y].value; y--) {
                Entry temp = array[y];
                array[y] = array[y - 1];
                array[y - 1] = temp;
            }
        }
    }

    public static <T> void sort(T[] array, Function<T, Integer> radixMapper) {
        Entry[] entries = new Entry[array.length];
        for (int i = 0; i < array.length; ++i)
            entries[i] = new Entry(radixMapper.apply(array[i]), array[i]);
        sortEntries(entries, 0, array.length, 24);
        for (int i = 0; i < array.length; ++i)
            array[i] = (T) entries[i].obj;
    }
}
