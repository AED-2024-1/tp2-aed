package aed;

import java.util.ArrayList;

import aed.heap.Heap;
import aed.nodos.*;
import aed.heap.HeapElement;
import aed.implementation.HeapIDS;
import aed.implementation.comparators.GananciaComparator;
import aed.implementation.comparators.TimestampComparator;

public class BestEffort {
    private Heap<HeapElement<Traslado>> _heapRedituables;
    private Heap<HeapElement<Traslado>> _heapAntiguos;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        ArrayList<HeapElement<Traslado>> arrayTraslados = new ArrayList<HeapElement<Traslado>>();
        
        int i = 0;
        for(Traslado traslado : traslados) 
        {
            HeapElement<Traslado> nodoTraslado = new HeapElement<Traslado>(traslado, 2);
            nodoTraslado.setHandle(HeapIDS.HeapRedituables.ordinal(),i);
            nodoTraslado.setHandle(HeapIDS.HeapAntiguos.ordinal(),i);
            arrayTraslados.add(nodoTraslado);
            i++;
        }

        _heapRedituables = new Heap<HeapElement<Traslado>>(new GananciaComparator(), HeapIDS.HeapRedituables.ordinal(), arrayTraslados);
        _heapAntiguos = new Heap<HeapElement<Traslado>>(new TimestampComparator(), HeapIDS.HeapAntiguos.ordinal(), arrayTraslados);
    }

    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        // Implementar
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // Implementar
        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return null;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return 0;
    }
    
}
