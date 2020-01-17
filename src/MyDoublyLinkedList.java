import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E>
        implements Cloneable {
    private Node<E> head;
    private int size = 0;

    public MyDoublyLinkedList() {
        head = new Node(null);
        head.previous = head;
        head.next = head;
    }

  /*  @Override
    public boolean equals(Object list) {
        if (!(list instanceof MyList)) {
            return false;
        }
        Node<E> current = head;
        for (int i=-1; i<size; i++) {
            if (!(current.element.equals(list.get(i))))
                return false;
        }
    } */
    @Override
    public String toString() {
        String str = "[";
        Node<E> current = head.next;
        for (int i=0; i<size-1; i++) { //Does last element outside loop
            str += current.element.toString()+", ";
            current = current.next;
        }
        str += current.element.toString()+"]";
        return str;
    }

    @Override
    public Object clone() {
        MyDoublyLinkedList newList = new MyDoublyLinkedList();
        Node<E> current = head.next;
        for (int i=0; i<size; i++) {
            newList.addLast(current.element);
            current = current.next;
        }
        return newList;
    }

    @Override
    public E getFirst() {
        return head.next.element;
    }

    @Override
    public E getLast() {
        return head.previous.element;
    }

    @Override
    public void addFirst(E e) {
        Node<E> addedElement = new Node(e);
        addedElement.next = head.next;
        addedElement.previous = head;
        head.next = addedElement;
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> addedElement = new Node(e);
        addedElement.previous = head.previous;
        addedElement.next = head;
        head.previous = addedElement;
        size++;
    }

    @Override
    public E removeFirst() {
        if (size<=0) {
            throw new NoSuchElementException();
        }
        E temp = head.next.element;
        head.next = head.next.next;
        size--;
        return temp;
    }

    @Override
    public E removeLast() {
        if (size<=0) {
            throw new NoSuchElementException();
        }
        E temp = head.previous.element;
        head.previous = head.previous.previous;
        size--;
        return temp;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public void add(int index, E e) {
        Node<E> addedElement = new Node(e);
        Node<E> current = head;
        for (int i=0; i<index; i++) { //Advances to one before index
            current = current.next;
        }
        addedElement.next = current.next;
        current.next = addedElement;
        addedElement.previous = current;
        size++;
    }

    @Override
    public void clear() {
        head.next = head;
        head.previous = head;
        size = 0;
    }

    @Override
    public boolean contains(E e) {
        Node<E> current = head.next;
        for (int i=0; i<size; i++) {
            if (e.equals(current.element))
                return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).element;
    }

    @Override
    public int indexOf(E e) {
        Node<E> current = head.next;
        for (int i=0; i<size; i++) {
            if (e.equals(current)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        int index = -1;
        Node<E> current = head.next;
        for (int i=0; i<size; i++) {
            if (e.equals(current)) {
                index = i;
            }
            current = current.next;
        }
        return index;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E val = getNode(index).element;
        Node<E> current = head;
        for (int i=0; i<index; i++) {
            current = current.next;
        }
        current.next = current.next.next;
        return val;
    }

    @Override
    public Object set(int index, E e) {
        E val = getNode(index).element;
        Node<E> current = head.next;
        for (int i=0; i<size; i++) {
            current = current.next;
        }
        current.element = e;
        return val;
    }

    private Node<E> getNode(int index) {
        Node<E> current = head;
        if (index < size / 2) {
            for (int i = -1; i < index; i++)
                current = current.next;
        } else {
            for (int i = size; i > index; i--)
                current = current.previous;
        }
        return current;
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E element) {
            this.element = element;
        }
    }
}