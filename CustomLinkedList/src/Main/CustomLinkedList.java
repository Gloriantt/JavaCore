package Main;

public class CustomLinkedList <T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;
    public CustomLinkedList(){
        this.head=null;
        this.tail=null;
        this.size=0;
    }
    public int size(){
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void addFirst(T element){
        Node<T> newNode= new Node<>(element);
        if(isEmpty()){
            head=tail=newNode;
        }else{
            newNode.next=head;
            head=newNode;
        }
        size++;
    }
    public void addLast(T element){
        Node<T> newNode = new Node<>(element);
        if(isEmpty()){
            head=tail=newNode;
        }else{
            tail.next=newNode;
            tail=newNode;
        }
        size++;
    }

    private Node<T> getNodeAt(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }


    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index: %d, Size: %d", index, size)
            );
        }
    }


    public void add(int index,T element){
        if(index==0){
            addFirst(element);
        } else if (index == size) {
            addLast(element);
        }else{
            Node<T> newNode = new Node<>(element);
            Node<T> previous =getNodeAt(index -1);
            newNode.next = previous.next;
            previous.next = newNode;
            size++;
        }
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        return head.data;
    }
    public T getLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        return tail.data;
    }
    public T get(int index) {
        checkIndex(index);
        return getNodeAt(index).data;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        T removedData = head.data;
        head = head.next;
        size--;

        if (isEmpty()) {
            tail = null;
        }

        return removedData;
    }
    public T removeLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        T removedData = tail.data;
        if (size == 1) {
            head = tail = null;
        } else {
            Node<T> previous = getNodeAt(size - 2);
            previous.next = null;
            tail = previous;
        }
        size--;

        return removedData;
    }
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> previous = getNodeAt(index - 1);
            Node<T> nodeToRemove = previous.next;
            T removedData = nodeToRemove.data;
            previous.next = nodeToRemove.next;
            size--;
            return removedData;
        }
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
