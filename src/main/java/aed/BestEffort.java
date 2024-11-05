package aed;

import java.util.ArrayList;

import aed.heap.Heap;
import aed.nodos.*;
import aed.heap.HeapElement;
import aed.implementation.HeapIDS;
import aed.implementation.comparators.GananciaComparator;
import aed.implementation.comparators.SuperHabitComparator;
import aed.implementation.comparators.TimestampComparator;

public class BestEffort {
    private Heap<HeapElement<Traslado>> _heapRedituables;
    private Heap<HeapElement<Traslado>> _heapAntiguos;
    private Heap<HeapElement<Ciudad>> _heapSuperHabit;
    private int  _Maxganancia;
    private int _Maxperdida;
    private ArrayList _IdMaxganancia;
    private ArrayList _IdMaxperdida;

    private int _promedioGanancia;
    ArrayList<HeapElement<Ciudad>> arrayCiudad;


    public BestEffort(int cantCiudades, Traslado[] traslados){
        ArrayList<HeapElement<Traslado>> arrayTraslados = new ArrayList<HeapElement<Traslado>>();
        arrayCiudad =  new ArrayList<HeapElement<Ciudad>>();
        _IdMaxganancia = new ArrayList<>();
        _IdMaxperdida = new ArrayList<>();

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


        for(int n = 0; n < cantCiudades; n++){
            Ciudad ciudad = new Ciudad(n);
            HeapElement<Ciudad> nodoCiudad = new HeapElement<Ciudad>(ciudad, 1);
            nodoCiudad.setHandle(0, n);
            arrayCiudad.add(nodoCiudad);
        }

        _heapSuperHabit  = new Heap<HeapElement<Ciudad>>(new SuperHabitComparator(),0,arrayCiudad);

    }

    public void registrarTraslados(Traslado[] traslados){
        int i = 0;
        for(Traslado traslado : traslados) 
        {
            HeapElement<Traslado> nodoTraslado = new HeapElement<Traslado>(traslado, 2);

            _heapRedituables.add(nodoTraslado);
            _heapAntiguos.add(nodoTraslado);
            i++;
        }
    }

    public int[] despacharMasRedituables(int n){
        int[] res = new int[n];
        HeapElement<Traslado> value;
        int handle;
        for(int i = 0; i < n; i++){
           value =  _heapRedituables.extractMax();
           handle = value.getHandle(HeapIDS.HeapAntiguos.ordinal());
           _heapAntiguos.remove(handle);
           res[i] = value.getValue().getId();
           int index = value.getValue().getOrigen();
           HeapElement<Ciudad> origen = arrayCiudad.get(index);
           origen.getValue().setGanancia(origen.getValue().getGanancia()+value.getValue().getGananciaNeta());
           maxgan(origen);
           index = value.getValue().getDestino();
           HeapElement<Ciudad> destino = arrayCiudad.get(index);
           destino.getValue().setPerdida(destino.getValue().getPerdida()+value.getValue().getGananciaNeta());
           maxper(destino);
        }

        return res;
    }
    private void maxgan(HeapElement<Ciudad> value){
        int id = value.getValue().getId();
        if(_IdMaxganancia.size() == 0){
        _IdMaxganancia.add(id);
        _Maxganancia = value.getValue().getGanancia();
        }else{
            if(_Maxganancia < value.getValue().getGanancia()){
                _IdMaxganancia = new ArrayList<>();
                _IdMaxganancia.add(id);
            }else if(_Maxganancia == value.getValue().getGanancia()){
                _IdMaxganancia.add(id);
            }

        }
    }

    private void maxper(HeapElement<Ciudad> value){
        int id = value.getValue().getId();
        if(_IdMaxperdida.size() == 0){
        _IdMaxperdida.add(id);
        _Maxperdida = value.getValue().getPerdida();
        }else{
            if(_Maxperdida < value.getValue().getPerdida()){
                _IdMaxperdida = new ArrayList<>();
                _IdMaxperdida.add(id);
            }else if( _Maxperdida == value.getValue().getPerdida()){
                _IdMaxperdida.add(id);
            }

        }

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
        
        return _IdMaxganancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return _IdMaxperdida;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return 0;
    }
    
}
