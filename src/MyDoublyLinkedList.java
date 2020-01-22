//Chris Bridewell
//MyDoublyLinkedList class
//A circular doubly linked list with a dummy head node

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E>
        implements Cloneable {
    private Node<E> head;

    public MyDoublyLinkedList() {
        head = new Node(null); //Initializes the dummy head node
        head.previous = head;
        head.next = head;
    }

    @Override
    public String toString() {
        String str = "["; //Start of strings
        Node<E> current = head.next;
        for (int i=0; i<size-1; i++) { //Concatenates elements with a comma and space, doing last element outside of loop
            if (current.element instanceof Object) //Makes sure it is an object so it has a toString method
                str += current.element.toString()+", ";
            else //If it's not an object adds null
                str += null+", ";
            current = current.next;
        }
        if (current.element instanceof Object)
            str += current.element.toString()+"]"; //Closes the string with the last element
        else
            str += null+"]"; //Closes with null if null is the last element
        return str;
    }

    public boolean equals(Object otherList) {
        if (this == otherList) //If it's literally the same list
            return true;
        else if (!(otherList instanceof MyList)) //If it's not a list it's not equal
            return false;
        MyList newList = (MyList) otherList; //Casts it to MyList
        if (newList.size() != this.size) //Different sizes won't be equal
            return false;
        else {
            ListIterator thisIterator = this.listIterator(); //Make an iterator
            Iterator newIterator = newList.iterator(); //Make another iterator
            while (thisIterator.hasNext()) { //Since they're the same size, only one loop is needed
                if (!(thisIterator.next() == newIterator.next())) //Makes sure the .next() is equal
                    return false;
            }
            return true; //If it made it through everything they're equal
        }
    }

    @Override
    public Object clone() {
        MyDoublyLinkedList newList = new MyDoublyLinkedList(); //Makes a new empty list
        Node<E> current = head.next;
        for (int i=0; i<size; i++) { //Basically just loops through the list and adds everything to it
            newList.addLast(current.element);
            current = current.next;
        }
        return newList;
    }

    @Override
    public E getFirst() { //Returns first element
        return head.next.element;
    }

    @Override
    public E getLast() { //Returns last element
        return head.previous.element;
    }

    @Override
    public void addFirst(E e) { //Adds element to front of list
        add(0, e);
    }

    @Override
    public void addLast(E e) { //Adds element to back of list
        add(size, e);
    }

    @Override
    public E removeFirst() { //Removes first element of list
        if (size<=0) { //Makes sure there is an element to be removed
            throw new NoSuchElementException();
        }
        E temp = head.next.element; //The element that is going to be removed/returned
        head.next = head.next.next; //Points head ahead twice
        head.next.previous = head; //Takes where head is now pointing and points it back to head
        size--;                    //skipping over the first element removing it from the list
        return temp;
    }

    @Override
    public E removeLast() { //The same as removeFirst but the opposite
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
    public void add(int index, E e) { //Adds an element at an index
        if (index < 0 || index > size) { //Makes sure it's valid index
            throw new IndexOutOfBoundsException();
        }
        Node<E> prev = getNode(index-1); //Goes to just before the index where element will be added
        Node<E> next = prev.next; //gets the node after where it will be added
        Node<E> newNode = new Node(e); //Creates the new node
        prev.next = newNode; //Changes all the pointers to make it work properly
        next.previous = newNode;
        newNode.previous = prev;
        newNode.next = next;
        size++;
    }

    @Override
    public void clear() { //Clears the list
        head = new Node(null); //Sets head to a blank node and points it to itself
        head.next = head;
        head.previous = head;
        size = 0;
    }

    @Override
    public boolean contains(E e) { //Checks if an element is in the list
        Node<E> current = head.next;
        while (current != head) { //Loops through the list
            if (e == null ? current.element == null : e.equals(current.element)) //Checks if each element is equal to the element, accounting for null
                return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public E get(int index) { //Gets an element at an index
        if (index < 0 || index >= size) //Makes sure it's a valid index
            throw new IndexOutOfBoundsException();
        return getNode(index).element;
    }

    @Override
    public int indexOf(E e) { //Get's first position of an element
        Node<E> current = head.next;
        //Loops through the list
        for (int i=0; i<size; i++) {
            //Checks if the element is equal to current element in list, accounting for null
            if (e == null ? current.element == null : e.equals(current.element))
                return i;
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E e) { //Basically the same as indexOf but the opposite
        Node<E> current = head.previous;
        for (int i=size-1; i>=0; i--) {
            if (e == null ? current.element == null : e.equals(current.element))
                return i;
            current = current.previous;
        }
        return -1;
    }

    @Override
    public E remove(int index) { //Removes element at a specific index
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = getNode(index-1); //Goes to just before that index, then makes the pointers skip it over
        E val = node.next.element;
        node.next = node.next.next;
        node.next.previous = node;
        size--;
        return val;
    }

    @Override
    public Object set(int index, E e) { //Sets an element at an index to the new element
        if (index < 0 || index >= size) //Makes sure it's a valid index
            throw new IndexOutOfBoundsException();
        Node<E> node = getNode(index); //Gets the node at that index
        E val = node.element;
        node.element = e; //Sets it to the new element
        return val;
    }

    @Override
    public ListIterator<E> listIterator(int index) { //Gets a DoublyLinkedListIterator
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        return new DoublyLinkedListIterator(index);
    }

    private Node<E> getNode(int index) { //Traverses list to a specific index to be used throughout other methods
        Node<E> current = head;
        for (int i = -1; i < index; i++)
            current = current.next;
        return current;
    }

    private class DoublyLinkedListIterator implements ListIterator { //Class for the iterator
        private Node<E> next;
        private int nextIndex;
        private Node<E> lastReturned = null;

        private DoublyLinkedListIterator() { //Default constructor, starts at first element
            next = head.next;
            nextIndex = 0;
        }

        private DoublyLinkedListIterator(int index) { //Allows creating of iterator mid-list
            nextIndex = index;
            next = getNode(index);
        }

        @Override
        public boolean hasNext() { //Checks if there is still a next element
            return nextIndex < size;
        }

        @Override
        public E next() { //Goes to next element
            if (!hasNext())
                throw new NoSuchElementException();
            //Returns the next element changing variables accordingly
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.element;
        }

        @Override
        public boolean hasPrevious() { //Checks if there is a previous element
            return nextIndex > 0;
        }

        @Override
        public E previous() { //Gets previous element
            if (hasPrevious()) {
                //Returns the previous element changing variables accordingly
                lastReturned = next.previous;
                next = next.previous;
                nextIndex--;
                return lastReturned.element;
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() { //Returns nextIndex
            return nextIndex;
        }

        @Override
        public int previousIndex() { //returns previous index
            return nextIndex-1;
        }

        @Override
        public void remove() { //Removes current element iterator is on
            if (lastReturned == null) //Checks the variety of ways you can't actually remove an element
                throw new IllegalStateException();
            size--;
            //Changes pointers around to skip over the current element, then sets lastReturned to null
            lastReturned.previous.next = lastReturned.next;
            lastReturned.next.previous = lastReturned.previous;
            lastReturned = null;
            nextIndex--;
        }

        @Override
        public void set(Object o) { //Changes the current element
            if (lastReturned == null)
                throw new IllegalStateException();
            lastReturned.element = (E) o;
        }

        @Override
        public void add(Object o) { //Adds an element to be the next element that would be called with next()
            Node<E> newNode = new Node((E) o);
            newNode.next = next;
            newNode.previous = next.previous;
            next.previous.next = newNode;
            next.previous = newNode;
            size++;
            lastReturned = null;
        }
    }

    private static class Node<E> { //The node class
        //Simply contains the element itself, and two more nodes, the node which is next, and node which is previous
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E element) {
            this.element = element;
        }
    }
}