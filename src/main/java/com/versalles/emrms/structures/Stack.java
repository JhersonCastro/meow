package com.versalles.emrms.structures;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author JUANM
 */
public class Stack<T> implements ListInterface<T>, Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    private Node<T> top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    public T pop() {
        if (top == null) {
            throw new NoSuchElementException("Stack is empty");
        }
        T data = top.getData();
        top = top.getNext();
        size--;
        return data;
    }

    public void clear() {
        top = null;
        size = 0;
    }

    @Override
    public void add(T data) {
        push(data);
    }

    @Override
    public void add(int index, T data) {
        throw new UnsupportedOperationException("Add at index is not supported in Stack");
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = top;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Remove at index is not supported in Stack");
    }

    public T peek() {
        if (top == null) {
            throw new NoSuchElementException("Stack is empty");
        }
        return top.getData();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void set(int index, T data) {
        throw new UnsupportedOperationException("Set at index is not supported in Stack");
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.getData();
                current = current.getNext();
                return data;
            }
        };
    }
}
