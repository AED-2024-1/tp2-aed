package aed.implementation.comparators;

import java.util.Comparator;

import aed.nodos.Ciudad;

public class OrigenComparator implements Comparator<Ciudad> {
    public OrigenComparator() {}
    
    @Override
    public int compare(Ciudad c1, Ciudad c2) 
    {
        return Integer.compare(c2.getId(), c1.getId());
    }
}