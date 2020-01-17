import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E>
        implements Cloneable {
    private Node<E> head;

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
        add(0, e);
        /*Node<E> addedElement = new Node(e);
        addedElement.next = head.next;
        addedElement.previous = head;
        head.next = addedElement;
        size++; */
    }

    @Override
    public void addLast(E e) {
        add(size, e);
        /*Node<E> newNode = new Node(e);
        newNode.previous = head.previous;
        newNode.next = head;
        head.previous.next = newNode;
        head.previous = newNode;
        size++; */
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
        head.previous.next = head;
        size--;
        return temp;
    }

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> prev = getNode(index-1);
        Node<E> next = prev.next;
        Node<E> newNode = new Node(e);
        prev.next = newNode;
        next.previous = newNode;
        newNode.previous = prev;
        newNode.next = next;
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
        while (current != head) {
            if (e == null ? current.element == null : e.equals(current.element))
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
            if (e.equals(current.element)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        Node<E> current = head.previous;
        for (int i=size-1; i>=0; i--) {
            if (e.equals(current.element)) {
                return i;
            }
            current = current.previous;
        }
        return -1;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = getNode(index-1);
        E val = node.next.element;
        node.next = node.next.next;
        node.next.previous = node;
        size--;
        return val;
    }

    @Override
    public Object set(int index, E e) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node<E> node = getNode(index);
        E val = node.element;
        node.element = e;
        return val;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new DoublyLinkedListIterator();
    }

    private Node<E> getNode(int index) {
        Node<E> current = head;
        for (int i = -1; i < index; i++)
            current = current.next;
        return current;
    }

    private class DoublyLinkedListIterator implements ListIterator {
        Node<E> current = head;
        int index = -1;
        boolean canRemove = false;

        @Override
        public boolean hasNext() {
            if (current.next == head)
                return false;
            return true;
        }

        @Override
        public E next() {
            if (hasNext()) {
                canRemove = true;
                current = current.next;
                index++;
                return current.element;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            if (index > -1)
                return true;
            return false;
        }

        @Override
        public E previous() {
            if (hasPrevious()) {
                E temp = current.element;
                canRemove = true;
                current = current.previous;
                index--;
                return temp;
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return index++;
        }

        @Override
        public int previousIndex() {
            return index--;
        }

        @Override
        public void remove() {
            if (!canRemove)
                throw new IllegalStateException();
            current = current.previous;
            current.next = current.next.next;
            current.next.previous = current;
        }

        @Override
        public void set(Object o) {
            if (!canRemove)
                throw new IllegalStateException();
            current.element = (E) o;
        }

        @Override
        public void add(Object o) {
            canRemove = false;
            current = current.previous;
            Node<E> newNode = new Node((E)o);
            newNode.next = current.next;
            newNode.previous = current;
            current.next = newNode;
            newNode.next.previous = newNode;
            size++;
        }
        /*@Override
        public boolean hasNext() {
            if (current.next == head)
                return false;
            return true;
        }

        @Override
        public Object next() {
            if (index >= size)
                throw new NoSuchElementException();
            current = current.next;
            canRemove = true;
            index++;
            return current.element;
        }

        @Override
        public boolean hasPrevious() {
            if (current.previous == head)
                return false;
            return true;
        }

        @Override
        public Object previous() {
            if (index == -1)
                throw new NoSuchElementException();
            current = current.previous;
            canRemove = true;
            index--;
            return current.element;
        }

        @Override
        public int nextIndex() {
            return index+1;
        }

        @Override
        public int previousIndex() {
            return index-1;
        }

        @Override
        public void remove() {
            if (canRemove) {
                canRemove = false;
                current = current.previous;
                current.next = current.next.next;
                size--;
                index--;
            }
        }

        @Override
        public void set(Object o) {
            current.element = (E) o;
        }

        @Override
        public void add(Object o) {
            MyDoublyLinkedList.this.add(index, (E) o);
        } */
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