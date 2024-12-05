package aed.implementation.comparators;

import java.util.Comparator;

import aed.nodos.Traslado;

public class TimestampComparator implements Comparator<Traslado> {
    public TimestampComparator() {}

    @Override
    public int compare(Traslado t1, Traslado t2) 
    {
        return Integer.compare(t2.getTimestamp(), t1.getTimestamp());
    }
}
