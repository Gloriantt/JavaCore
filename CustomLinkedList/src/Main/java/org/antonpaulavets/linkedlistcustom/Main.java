package org.antonpaulavets.linkedlistcustom;

public class Main {
    public static void main(String[] args) {
        CustomLinkedList<Integer> list = new CustomLinkedList<Integer>();
        list.addFirst(1);
        list.add(1,2);
        list.add(2,3);
        list.removeFirst();
        System.out.println(list.getLast().toString());
        System.out.println("111111");
        System.out.println(list.toString());
    }
}