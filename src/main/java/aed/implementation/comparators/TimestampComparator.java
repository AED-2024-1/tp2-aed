package aed.implementation.comparators;

import java.util.Comparator;

import aed.heap.HeapElement;
import aed.nodos.Traslado;

public class TimestampComparator implements Comparator<HeapElement<Traslado>> {
    public TimestampComparator() {}

    @Override
    public int compare(HeapElement<Traslado> t1, HeapElement<Traslado> t2) 
    {
        return Integer.compare(t2.getValue().getTimestamp(), t1.getValue().getTimestamp());
    }
}
