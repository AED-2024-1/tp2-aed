package aed.implementation.comparators;
import java.util.Comparator;

import aed.heap.HeapElement;
import aed.nodos.Ciudad;


public class SuperavitComparator implements Comparator<HeapElement<Ciudad>>  {

    public SuperavitComparator() {}
    @Override
    public int compare(HeapElement<Ciudad> t1, HeapElement<Ciudad> t2) 
    {
        if(Integer.compare(t1.getValue().getSuperavit(), t2.getValue().getSuperavit()) == 0){
            return Integer.compare(t2.getValue().getId(), t1.getValue().getId());
        }
        return Integer.compare(t1.getValue().getSuperavit(), t2.getValue().getSuperavit());
    }
}
