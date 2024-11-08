package aed.implementation.comparators;

import java.util.Comparator;

import aed.heap.HeapElement;
import aed.nodos.Traslado;

public class GananciaComparator implements Comparator<HeapElement<Traslado>> {
    public GananciaComparator() {}
    
    @Override
    public int compare(HeapElement<Traslado> t1, HeapElement<Traslado> t2) 
    {
        if(Integer.compare(t1.getValue().getGananciaNeta(), t2.getValue().getGananciaNeta()) == 0){
            return Integer.compare(t2.getValue().getId(), t1.getValue().getId());
        }
        return Integer.compare(t1.getValue().getGananciaNeta(), t2.getValue().getGananciaNeta());
    }
}