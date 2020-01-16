import java.util.ListIterator;

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E>
        implements Cloneable {
    private Node<E> head;
    private int size = 0;

    public MyDoublyLinkedList() {
        head = new Node(null);
        head.previous = head;
        head.next = head;
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
        E temp = head.next.element;
        head.next = head.next.next;
        size--;
        return temp;
    }

    @Override
    public E removeLast() {
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
        addedElement.previous = current;
        current.next = addedElement;
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
        return false;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return head.getNode(index, head, size).element;
    }

    @Override
    public int indexOf(E e) {
        return 0;
    }

    @Override
    public int lastIndexOf(E e) {
        return 0;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public Object set(int index, E e) {
        return null;
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E element) {
            this.element = element;
        }

        /*
                private Node<E> getNode(int index) {
                    Node<E> current = head;
                    for (int i=-1; i<index; i++)
                        current = current.next;
                    return current;
                }
                */
        private Node<E> getNode(int index, Node<E> head, int size) {
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
    }
}