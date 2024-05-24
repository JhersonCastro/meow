package com.versalles.emrms.structures;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author JUANM
 */

public class CircularList<T> implements ListInterface<T>, Serializable {

  private static final long serialVersionUID = 1L;
    private Node<T> head;
    private Node<T> current;
    private int size;

    public CircularList() {
        this.head = null;
        this.current = null;
        this.size = 0;
    }

    @Override
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            current = newNode;
            head.setNext(head);
        } else {
            Node<T> temp = head;
            while (temp.getNext() != head) {
                temp = temp.getNext();
            }
            temp.setNext(newNode);
            newNode.setNext(head);
        }
        size++;
    }

    @Override
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> newNode = new Node<>(data);
        if (index == 0) {
            if (head == null) {
                head = newNode;
                head.setNext(head);
                current = head;
            } else {
                newNode.setNext(head);
                Node<T> temp = head;
                while (temp.getNext() != head) {
                    temp = temp.getNext();
                }
                temp.setNext(newNode);
                head = newNode;
            }
        } else {
            Node<T> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        }
        size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return temp.getData();
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> removedNode;
        if (index == 0) {
            removedNode = head;
            if (size == 1) {
                head = null;
                current = null;
            } else {
                Node<T> temp = head;
                while (temp.getNext() != head) {
                    temp = temp.getNext();
                }
                head = head.getNext();
                temp.setNext(head);
            }
        } else {
            Node<T> temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            removedNode = temp.getNext();
            temp.setNext(removedNode.getNext());
        }
        size--;
        return removedNode.getData();
    }

    public boolean remove(T data) {
        if (head == null) {
            return false;
        }

        Node<T> current = head;
        Node<T> previous = null;

        do {
            if (current.getData().equals(data)) {
                if (previous != null) {
                    previous.setNext(current.getNext());
                } else {
                    
                    if (head.getNext() == head) {
                        head = null;
                    } else {
                        Node<T> last = head;
                        while (last.getNext() != head) {
                            last = last.getNext();
                        }
                        last.setNext(head.getNext());
                        head = head.getNext();
                    }
                }
                size--;
                return true;
            }
            previous = current;
            current = current.getNext();
        } while (current != head);

        return false;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        temp.setData(data);
    }

    public T getCurrent() {
        if (current == null) {
            return null;
        }
        T data = current.getData();
        current = current.getNext();
        return data;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public T next() {
                T data = current.getData();
                current = current.getNext();
                count++;
                return data;
            }
        };
    }
}