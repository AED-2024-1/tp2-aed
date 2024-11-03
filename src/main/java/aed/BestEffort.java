package aed;

import java.util.ArrayList;

import aed.Nodos.NodoCiudad;
import aed.Nodos.NodoTraslados;
import aed.heap.Heap;

public class BestEffort {
    ArrayList<NodoCiudad> listaciudades;
    Heap<NodoTraslados>  heap_antiguedades;
    Heap<NodoTraslados>  heap_redituables;
    Heap<NodoCiudad>    heap_superhabit;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        for(int i = 0; i < cantCiudades;i++){

        }
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
