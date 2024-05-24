package com.versalles.emrms.utils;

import java.util.Comparator;
import com.versalles.emrms.structures.ListInterface;

/**
 *
 * @author JUANM
 */
public class Searching {

    public static <T> int search(ListInterface<T> list, T key, Comparator<T> comparator) {
        if (list.size() < 20) {
            return linearSearch(list, key, comparator);
        } else {
            return binarySearch(list, key, comparator);
        }
    }

    private static <T> int linearSearch(ListInterface<T> list, T key, Comparator<T> comparator) {
        for (int i = 0; i < list.size(); i++) {
            if (comparator.compare(list.get(i), key) == 0) {
                return i;
            }
        }
        return -1;
    }

    private static <T> int binarySearch(ListInterface<T> list, T key, Comparator<T> comparator) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = comparator.compare(list.get(mid), key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
