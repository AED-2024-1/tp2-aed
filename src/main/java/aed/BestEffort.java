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
    private int _promedioGanancia;
    private int _despachados;
    ArrayList<HeapElement<Ciudad>> arrayCiudad;
    private ArrayList<Integer> _IdMaxganancia;
    private ArrayList<Integer> _IdMaxperdida;


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
           //////// Hasta aca remuevo de ambos heaps el Traslado y sumo el id en la res
           seteoGanaciaCiudad(value);
           seteoPerdidaCiudad(value);
           promediogan(value);
           _despachados++;
        }
        return res;
    }




    public int[] despacharMasAntiguos(int n){
        int[] res = new int[n];
        HeapElement<Traslado> value;
        int handle;
        for(int i = 0; i < n; i++){
           value =  _heapAntiguos.extractMax();
           handle = value.getHandle(HeapIDS.HeapRedituables.ordinal());
           _heapRedituables.remove(handle);
           res[i] = value.getValue().getId();
           //////// Hasta aca remuevo de ambos heaps el Traslado y sumo el id en la res
           seteoGanaciaCiudad(value);
           seteoPerdidaCiudad(value);
           promediogan(value);
           _despachados++;
        }
        return res;
    }

    public int ciudadConMayorSuperavit(){
        HeapElement<Ciudad> nodo = _heapSuperHabit.getMax();
        Ciudad ciudad = nodo.getValue();
        int id = ciudad.getId();
        return id;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        
        return _IdMaxganancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return _IdMaxperdida;
    }

    public int gananciaPromedioPorTraslado(){
       int res  = (int) Math.floor(_promedioGanancia/ _despachados);
        return res;
    }

    private void promediogan(HeapElement<Traslado> nodo){
        Traslado traslado = nodo.getValue();
        int ganancia = traslado.getGananciaNeta();
        _promedioGanancia  = _promedioGanancia + ganancia;
    }

    private void seteoGanaciaCiudad(HeapElement<Traslado> nodo){
        Traslado traslado = nodo.getValue();
        int index = traslado.getOrigen();
        HeapElement<Ciudad> origen = arrayCiudad.get(index);
        Ciudad Ciudad_origen = origen.getValue();
        Ciudad_origen.setGanancia(Ciudad_origen.getGanancia() + traslado.getGananciaNeta());
        Ciudad_origen.setSuperhabit(Ciudad_origen.getSuperhabit() + traslado.getGananciaNeta());
        _heapSuperHabit.ordenar(origen.getHandle(0));
        maxgan(origen);
    }

     private void seteoPerdidaCiudad(HeapElement<Traslado> nodo){
        Traslado traslado = nodo.getValue();
        int index = traslado.getDestino();
        HeapElement<Ciudad> destino = arrayCiudad.get(index);
        Ciudad Ciudad_destino = destino.getValue();
        Ciudad_destino.setPerdida(Ciudad_destino.getPerdida() + traslado.getGananciaNeta());
        Ciudad_destino.setSuperhabit(Ciudad_destino.getSuperhabit() - traslado.getGananciaNeta());
        _heapSuperHabit.ordenar(destino.getHandle(0));
        maxper(destino);
    }

    private void maxgan(HeapElement<Ciudad> value){
        Ciudad ciudad = value.getValue();
        int ganancia_ciudad = ciudad.getGanancia();
        int id = ciudad.getId();
        if(_IdMaxganancia.size() == 0){
            _IdMaxganancia.add(id);
            _Maxganancia = ganancia_ciudad;
        }else{
            if(_Maxganancia < ganancia_ciudad){
                _IdMaxganancia = new ArrayList<>();
                _IdMaxganancia.add(id);
                _Maxganancia = ganancia_ciudad;
            }else if(_Maxganancia == ganancia_ciudad){
                _IdMaxganancia.add(id);
            }

        }
    }

    private void maxper(HeapElement<Ciudad> value){
        Ciudad ciudad = value.getValue();
        int perdida_ciudad = ciudad.getPerdida();
        int id = ciudad.getId();
        if(_IdMaxperdida.size() == 0){
            _IdMaxperdida.add(id);
            _Maxperdida = perdida_ciudad;
        }else{
            if(_Maxperdida < perdida_ciudad ){
                _IdMaxperdida = new ArrayList<>();
                _IdMaxperdida.add(id);
                _Maxperdida = perdida_ciudad;
            }else if( _Maxperdida == perdida_ciudad ){
                _IdMaxperdida.add(id);
            }

        }

    }
    
}
