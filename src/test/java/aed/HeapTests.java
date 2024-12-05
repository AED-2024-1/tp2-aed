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
        heap1.add(20);
        heap1.add(30);
        
        Comparator<Integer> comparator = Comparator.comparingInt(Integer::intValue);

        heap2 = new Heap(comparator, 1, heap1);

        HeapElement<Integer> element1 = heap1.getMax();

        heap1.remove(element1.getHandle(0));
        heap2.remove(element1.getHandle(1));

        assertNotEquals(20, heap1.getMax().getValue());
        assertNotEquals(20, heap2.getMax().getValue());

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

    /*@Test
    void testAñadirVariosElementosADosHeaps() {
        ArrayList<HeapElement<Integer>> list = new ArrayList<HeapElement<Integer>>();
        Comparator<HeapElement<Integer>> comparator = Comparator.comparingInt(HeapElement::getValue);

        element1 = new HeapElement(23, 2);
        element2 = new HeapElement(178, 2);
        element3 = new HeapElement(156, 2);

        HeapElement element4 = new HeapElement(5, 2);
        HeapElement element5 = new HeapElement(199, 2);
        HeapElement element6 = new HeapElement(42, 2);
        HeapElement element7 = new HeapElement(88, 2);
        HeapElement element8 = new HeapElement(134, 2);
        HeapElement element9 = new HeapElement(12, 2);
        HeapElement element10 = new HeapElement(67, 2);
        HeapElement element11 = new HeapElement(150, 2);
        HeapElement element12 = new HeapElement(9, 2);
        HeapElement element13 = new HeapElement(101, 2);
        HeapElement element14 = new HeapElement(58, 2);

        
        list.add(element1);
        list.add(element2);
        list.add(element3);
        list.add(element4);
        list.add(element5);
        list.add(element6);
        list.add(element7);
        list.add(element8);
        list.add(element9);
        list.add(element10);
        list.add(element11);
        list.add(element12);
        list.add(element13);
        list.add(element14);

        
        heap3 = new Heap<HeapElement<Integer>>(comparator, HeapTrasladosIDS.HeapRedituables.ordinal(), list);

        heap3.toString(); 
    }*/
}
