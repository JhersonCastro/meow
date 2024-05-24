package com.versalles.emrms.structures;

/**
 *
 * @author JUANM
 */
public interface ListInterface<T> {

    void add(T data);

    void add(int index, T data);

    T get(int index);

    T remove(int index);

    int size();

    boolean isEmpty();

    void set(int index, T data);
}
