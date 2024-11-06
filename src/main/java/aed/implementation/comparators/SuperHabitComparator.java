package aed.implementation.comparators;
import java.util.Comparator;

import aed.heap.HeapElement;
import aed.nodos.Ciudad;


public class SuperHabitComparator implements Comparator<HeapElement<Ciudad>>  {

    public SuperHabitComparator() {}
    @Override
    public int compare(HeapElement<Ciudad> t1, HeapElement<Ciudad> t2) 
    {
        return Integer.compare(t1.getValue().getSuperhabit(), t2.getValue().getSuperhabit());
    }
}
