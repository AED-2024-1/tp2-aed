package aed.implementation.comparators;

import java.util.Comparator;

import aed.nodos.Traslado;

public class GananciaComparator implements Comparator<Traslado> {
    public GananciaComparator() {}
    
    @Override
    public int compare(Traslado t1, Traslado t2) 
    {
        if(Integer.compare(t1.getGananciaNeta(), t2.getGananciaNeta()) == 0){
            return Integer.compare(t2.getId(), t1.getId());
        }
        return Integer.compare(t1.getGananciaNeta(), t2.getGananciaNeta());
    }
}