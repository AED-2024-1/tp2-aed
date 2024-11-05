package aed;

import java.util.Comparator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aed.heap.Heap;
import aed.heap.HeapElement;
import aed.implementation.HeapIDS;
import aed.implementation.comparators.GananciaComparator;
import aed.nodos.Traslado;

public class HeapTests {

    private Heap<HeapElement<Integer>> heap1;
    private Heap<HeapElement<Integer>> heap2;
    private Heap<HeapElement<Integer>> heap3;
    private HeapElement<Integer> element1;
    private HeapElement<Integer> element2;
    private HeapElement<Integer> element3;

    @BeforeEach
    void setUp() {
        Comparator<HeapElement<Integer>> comparator = Comparator.comparingInt(HeapElement::getValue);

        heap1 = new Heap<>(comparator, 0);
        heap2 = new Heap<>(comparator, 1);

        element1 = new HeapElement(20, 2);
        element2 = new HeapElement(20, 2);
        element3 = new HeapElement(30, 2);
    }

    @Test
    void testAddAndExtractMax() {
        heap1.add(element1);
        heap1.add(element2);
        heap1.add(element3);

        assertEquals(30, heap1.getMax().getValue());
        assertEquals("[30,20,20]", heap1.toString());

        heap2.add(element2);
        heap2.add(element1);
        heap2.add(element3);

        assertEquals(30, heap2.getMax().getValue());
    }

    @Test
    void testRemoveElement() {
        heap1.add(element1);
        heap1.add(element2);
        heap1.add(element3);

        heap2.add(element1);
        heap2.add(element2);
        heap2.add(element3);

        int indexInHeap1 = element2.getHandle(0);
        int indexInHeap2 = element2.getHandle(1);

        heap1.remove(indexInHeap1);
        heap2.remove(indexInHeap2);

        assertNotEquals(20, heap1.getMax().getValue(), "Max should no longer be 20 in heap1 after removal");
        assertNotEquals(20, heap2.getMax().getValue(), "Max should no longer be 20 in heap2 after removal");

        assertEquals(30, heap1.getMax().getValue(), "Max should be 30 in heap1 after removal of 20");
        assertEquals(30, heap2.getMax().getValue(), "Max should be 30 in heap2 after removal of 20");
    }

    @Test
    void testAddAndRemoveMultipleElements() {
        HeapElement element4 = new HeapElement(40, 2);
        HeapElement element5 = new HeapElement(50, 2);

        heap1.add(element5);
        heap1.add(element4);
        heap1.add(element3);
        heap1.add(element2);
        heap1.add(element1);

        assertEquals(50, heap1.getMax().getValue(), "Max should be 50 in heap1 after adding multiple elements");

        heap1.remove(element5.getHandle(0));
        assertEquals(40, heap1.getMax().getValue(), "Max should be 40 in heap1 after removing 50");

        heap1.remove(element4.getHandle(0));
        assertEquals(30, heap1.getMax().getValue(), "Max should be 30 in heap1 after removing 40");
    }

    @Test
    void testRemoveInvalidHandle() {
        heap1.add(element1);
        heap1.add(element2);

        int invalidHandle = -1;
        heap1.remove(invalidHandle);

        assertEquals(20, heap1.getMax().getValue(), "Max should remain 20 after attempting to remove with an invalid handle");
    }

    @Test
    void testHeapPropertyAfterMultipleAddAndRemove() {
        heap1.add(element1);
        heap1.add(element2);
        heap1.add(element3);

        heap2.add(element1);
        heap2.add(element2);
        heap2.add(element3);

        heap1.remove(element3.getHandle(0));
        assertEquals(20, heap1.getMax().getValue(), "Max should be 20 in heap1 after removing 30");

        heap2.remove(element3.getHandle(1));
        assertEquals(20, heap2.getMax().getValue(), "Max should be 20 in heap2 after removing 30");
    }
    @Test
    void prueba() {
        ArrayList<HeapElement<Integer>> list = new ArrayList<HeapElement<Integer>>();
        Comparator<HeapElement<Integer>> comparator = Comparator.comparingInt(HeapElement::getValue);

        element1 = new HeapElement(23, 2);
        element2 = new HeapElement(178, 2);
        element3 = new HeapElement(156, 2);

        
        list.add(element1);
        list.add(element2);
        list.add(element3);

        
        heap3 =  new Heap<HeapElement<Integer>>(comparator, HeapIDS.HeapRedituables.ordinal(), list);

        heap3.toString();
    }
}
