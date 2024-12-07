package aed;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aed.heap.Heap;
import aed.heap.HeapElement;
import aed.implementation.HeapTrasladosIDS;

public class HeapTests {
    private Heap<Integer> heap1;
    private Heap<Integer> heap2;
    private Heap<Integer> heap3;

    @BeforeEach
    void init() {
        Comparator<Integer> comparator = Comparator.comparingInt(Integer::intValue);

        heap1 = new Heap<Integer>(comparator, 0);
        heap2 = new Heap<Integer>(comparator, 1);
    }

    @Test
    void testAñadirYExtraerMaximo() {
        heap1.add(20);
        heap1.add(20);
        heap1.add(30);

        assertEquals(30, heap1.getMaxValue());
        assertEquals("[30,20,20]", heap1.toString());

        heap2.add(20);
        heap2.add(20);
        heap2.add(30);

        assertEquals(30, heap2.getMaxValue());
    }

    @Test
    void testRemoverElementos() {
        heap1.add(20);
        heap1.add(40);
        heap1.add(30);
        
        Comparator<Integer> comparator = Comparator.comparingInt(Integer::intValue);

        heap2 = new Heap(comparator, 1, heap1);

        HeapElement<Integer> element1 = heap1.getMax();

        heap1.remove(element1.getHandle(0));
        heap2.remove(element1.getHandle(1));

        assertNotEquals(40, heap1.getMax().getValue());
        assertNotEquals(40, heap2.getMax().getValue());

        assertEquals(30, heap1.getMax().getValue());
        assertEquals(30, heap2.getMax().getValue());
    }

    @Test
    void testAñadirYRemover() {

        HeapElement<Integer> element1 = heap1.add(30);
        HeapElement<Integer> element2 = heap1.add(20);
        HeapElement<Integer> element3 = heap1.add(20);
        HeapElement<Integer> element4 = heap1.add(40);
        HeapElement<Integer> element5 = heap1.add(50);

        assertEquals(50, heap1.getMax().getValue());

        heap1.remove(element5.getHandle(0));
        assertEquals(40, heap1.getMax().getValue());

        heap1.remove(element4.getHandle(0));
        assertEquals(30, heap1.getMax().getValue());
    }

    @Test
    void testRemoverHandlesInvalidos() {
        HeapElement<Integer> element1 = heap1.add(20);

        int invalidHandle = -1;
        heap1.remove(invalidHandle);

        assertEquals(20, heap1.getMax().getValue());
    }

    @Test
    void testHeapRemoverYAñadirMultiplesVeces() {
        HeapElement<Integer> element1 = heap1.add(20);
        HeapElement<Integer> element2 = heap1.add(30);
        HeapElement<Integer> element3 = heap1.add(40);

        heap2.add(element1);
        heap2.add(element2);
        heap2.add(element3);

        heap1.remove(element3.getHandle(0));
        assertEquals(30, heap1.getMax().getValue());

        heap2.remove(element3.getHandle(1));
        assertEquals(30, heap2.getMax().getValue());
    }
}
