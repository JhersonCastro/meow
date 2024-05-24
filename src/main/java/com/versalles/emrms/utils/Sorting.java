package com.versalles.emrms.utils;

import java.util.Comparator;
import com.versalles.emrms.structures.ListInterface;

/**
 *
 * @author JUANM
 */
public class Sorting {

    public static <T> void sort(ListInterface<T> list, Comparator<T> comparator) {
        if (list.size() < 20) {
            bubbleSort(list, comparator);
        } else {
            quickSort(list, 0, list.size() - 1, comparator);
        }
    }

    private static <T> void bubbleSort(ListInterface<T> list, Comparator<T> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    private static <T> void quickSort(ListInterface<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(list, low, high, comparator);
            quickSort(list, low, pi - 1, comparator);
            quickSort(list, pi + 1, high, comparator);
        }
    }

    private static <T> int partition(ListInterface<T> list, int low, int high, Comparator<T> comparator) {
        T pivot = list.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                T temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        T temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }
}
