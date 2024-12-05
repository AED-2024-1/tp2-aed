package aed.implementation.comparators;
import java.util.Comparator;

import aed.nodos.Ciudad;


public class SuperavitComparator implements Comparator<Ciudad>  {

    public SuperavitComparator() {}
    @Override
    public int compare(Ciudad t1, Ciudad t2) 
    {
        if(Integer.compare(t1.getSuperavit(), t2.getSuperavit()) == 0){
            return Integer.compare(t2.getId(), t1.getId());
        }
        return Integer.compare(t1.getSuperavit(), t2.getSuperavit());
    }
}
