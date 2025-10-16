package Tests.java;

import org.antonpaulavets.linkedlistcustom.CustomLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

class CustomLinkedListTest {

    private CustomLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new CustomLinkedList<Integer>();
    }

    @Nested
    @DisplayName("Size operations")
    class SizeOperations {

        @Test
        @DisplayName("New list should have size 0")
        void testInitialSize() {
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
        }

        @Test
        @DisplayName("Size should increase after adding elements")
        void testSizeAfterAdding() {
            list.addFirst(1);
            assertEquals(1, list.size());

            list.addLast(2);
            assertEquals(2, list.size());

            list.add(1, 3);
            assertEquals(3, list.size());
        }

        @Test
        @DisplayName("Size should decrease after removing elements")
        void testSizeAfterRemoving() {
            list.addFirst(1);
            list.addLast(2);
            list.addLast(3);

            list.removeFirst();
            assertEquals(2, list.size());

            list.removeLast();
            assertEquals(1, list.size());
        }
    }

    @Nested
    @DisplayName("AddFirst operation")
    class AddFirstOperation {

        @Test
        @DisplayName("Should add element to empty list")
        void testAddFirstToEmptyList() {
            list.addFirst(10);

            assertEquals(1, list.size());
            assertEquals(10, list.getFirst());
            assertEquals(10, list.getLast());
        }

        @Test
        @DisplayName("Should add multiple elements to the beginning")
        void testAddFirstMultiple() {
            list.addFirst(1);
            list.addFirst(2);
            list.addFirst(3);

            assertEquals(3, list.size());
            assertEquals(3, list.getFirst());
            assertEquals(1, list.getLast());
        }
    }

    @Nested
    @DisplayName("AddLast operation")
    class AddLastOperation {

        @Test
        @DisplayName("Should add element to empty list")
        void testAddLastToEmptyList() {
            list.addLast(10);

            assertEquals(1, list.size());
            assertEquals(10, list.getFirst());
            assertEquals(10, list.getLast());
        }

        @Test
        @DisplayName("Should add multiple elements to the end")
        void testAddLastMultiple() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            assertEquals(3, list.size());
            assertEquals(1, list.getFirst());
            assertEquals(3, list.getLast());
        }
    }

    @Nested
    @DisplayName("Add by index operation")
    class AddByIndexOperation {

        @Test
        @DisplayName("Should add element at the beginning (index 0)")
        void testAddAtBeginning() {
            list.addLast(2);
            list.addLast(3);
            list.add(0, 1);

            assertEquals(3, list.size());
            assertEquals(1, list.get(0));
            assertEquals(2, list.get(1));
            assertEquals(3, list.get(2));
        }

        @Test
        @DisplayName("Should add element in the middle")
        void testAddInMiddle() {
            list.addLast(1);
            list.addLast(3);
            list.add(1, 2);

            assertEquals(3, list.size());
            assertEquals(1, list.get(0));
            assertEquals(2, list.get(1));
            assertEquals(3, list.get(2));
        }

        @Test
        @DisplayName("Should add element at the end")
        void testAddAtEnd() {
            list.addLast(1);
            list.addLast(2);
            list.add(2, 3);

            assertEquals(3, list.size());
            assertEquals(3, list.getLast());
        }

    }

    @Nested
    @DisplayName("Get operations")
    class GetOperations {

        @BeforeEach
        void fillList() {
            list.addLast(10);
            list.addLast(20);
            list.addLast(30);
        }

        @Test
        @DisplayName("Should get first element")
        void testGetFirst() {
            assertEquals(10, list.getFirst());
        }

        @Test
        @DisplayName("Should get last element")
        void testGetLast() {
            assertEquals(30, list.getLast());
        }

        @Test
        @DisplayName("Should get element by index")
        void testGetByIndex() {
            assertEquals(10, list.get(0));
            assertEquals(20, list.get(1));
            assertEquals(30, list.get(2));
        }

        @Test
        @DisplayName("Should throw exception when getting from empty list")
        void testGetFromEmptyList() {
            CustomLinkedList<Integer> emptyList = new CustomLinkedList<Integer>();

            assertThrows(IndexOutOfBoundsException.class, emptyList::getFirst);
            assertThrows(IndexOutOfBoundsException.class, emptyList::getLast);
            assertThrows(IndexOutOfBoundsException.class, () -> emptyList.get(0));
        }

        @Test
        @DisplayName("Should throw exception for invalid index")
        void testGetInvalidIndex() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        }
    }

    @Nested
    @DisplayName("RemoveFirst operation")
    class RemoveFirstOperation {

        @Test
        @DisplayName("Should remove and return first element")
        void testRemoveFirst() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            Integer removed = list.removeFirst();

            assertEquals(1, removed);
            assertEquals(2, list.size());
            assertEquals(2, list.getFirst());
        }

        @Test
        @DisplayName("Should handle removing from single element list")
        void testRemoveFirstSingleElement() {
            list.addFirst(1);

            Integer removed = list.removeFirst();

            assertEquals(1, removed);
            assertTrue(list.isEmpty());
        }

        @Test
        @DisplayName("Should throw exception when removing from empty list")
        void testRemoveFirstEmptyList() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.removeFirst());
        }
    }

    @Nested
    @DisplayName("RemoveLast operation")
    class RemoveLastOperation {

        @Test
        @DisplayName("Should remove and return last element")
        void testRemoveLast() {
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);

            Integer removed = list.removeLast();

            assertEquals(3, removed);
            assertEquals(2, list.size());
            assertEquals(2, list.getLast());
        }

        @Test
        @DisplayName("Should handle removing from single element list")
        void testRemoveLastSingleElement() {
            list.addFirst(1);

            Integer removed = list.removeLast();

            assertEquals(1, removed);
            assertTrue(list.isEmpty());
        }

        @Test
        @DisplayName("Should throw exception when removing from empty list")
        void testRemoveLastEmptyList() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.removeLast());
        }
    }

    @Nested
    @DisplayName("Remove by index operation")
    class RemoveByIndexOperation {

        @BeforeEach
        void fillList() {
            list.addLast(10);
            list.addLast(20);
            list.addLast(30);
            list.addLast(40);
        }

        @Test
        @DisplayName("Should remove element at the beginning")
        void testRemoveAtBeginning() {
            Integer removed = list.remove(0);

            assertEquals(10, removed);
            assertEquals(3, list.size());
            assertEquals(20, list.getFirst());
        }

        @Test
        @DisplayName("Should remove element in the middle")
        void testRemoveInMiddle() {
            Integer removed = list.remove(1);

            assertEquals(20, removed);
            assertEquals(3, list.size());
            assertEquals(10, list.get(0));
            assertEquals(30, list.get(1));
            assertEquals(40, list.get(2));
        }

        @Test
        @DisplayName("Should remove element at the end")
        void testRemoveAtEnd() {
            Integer removed = list.remove(3);

            assertEquals(40, removed);
            assertEquals(3, list.size());
            assertEquals(30, list.getLast());
        }

        @Test
        @DisplayName("Should throw exception for invalid index")
        void testRemoveInvalidIndex() {
            assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4));
        }
    }

    @Nested
    @DisplayName("Complex scenarios")
    class ComplexScenarios {

        @Test
        @DisplayName("Should handle mixed operations correctly")
        void testMixedOperations() {

            list.addFirst(2);
            list.addLast(4);
            list.addFirst(1);
            list.add(2, 3);
            list.addLast(5);
            assertEquals(5, list.size());

            for (int i = 0; i < 5; i++) {
                assertEquals(i + 1, list.get(i));
            }

            assertEquals(1, list.removeFirst());
            assertEquals(5, list.removeLast());
            assertEquals(3, list.remove(1));

            assertEquals(2, list.size());
            assertEquals(2, list.getFirst());
            assertEquals(4, list.getLast());
        }

        @Test
        @DisplayName("Should work with different data types")
        void testWithStrings() {
            CustomLinkedList<String> stringList = new CustomLinkedList<String>();

            stringList.addLast("Hello");
            stringList.addLast("World");
            stringList.addFirst("Say");

            assertEquals(3, stringList.size());
            assertEquals("Say", stringList.getFirst());
            assertEquals("World", stringList.getLast());
            assertEquals("Hello", stringList.get(1));
        }
    }
}