package aed.implementation.comparators;

import java.util.Comparator;

import aed.Traslado;
import aed.heap.HeapElement;

public class GananciaComparator implements Comparator<HeapElement<Traslado>> {
    public GananciaComparator() {}
    
    public int compare(HeapElement<Traslado> t1, HeapElement<Traslado> t2) 
    {
        return Integer.compare(t1.getValue().getGanancia(), t2.getValue().getGanancia());
    }
}