package aed.implementation.comparators;

import java.util.Comparator;

import aed.Traslado;
import aed.heap.HeapElement;

public class TimestampComparator implements Comparator<HeapElement<Traslado>> {
    public TimestampComparator() {}

    public int compare(HeapElement<Traslado> t1, HeapElement<Traslado> t2) 
    {
        return Integer.compare(t2.getValue().getTimestamp(), t1.getValue().getTimestamp());
    }
}
