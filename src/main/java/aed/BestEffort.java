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


    public BestEffort(int cantCiudades, Traslado[] traslados){ //O(|T|+|C|)
         ArrayList<HeapElement<Traslado>> arrayTraslados = new ArrayList<HeapElement<Traslado>>();
         arrayCiudad =  new ArrayList<HeapElement<Ciudad>>();
        _IdMaxganancia = new ArrayList<>();
        _IdMaxperdida = new ArrayList<>();

        int i = 0;
        for(Traslado traslado : traslados) //O(T)
        {
            HeapElement<Traslado> nodoTraslado = new HeapElement<Traslado>(traslado, 2);
            nodoTraslado.setHandle(HeapIDS.HeapRedituables.ordinal(),i);
            nodoTraslado.setHandle(HeapIDS.HeapAntiguos.ordinal(),i);
            arrayTraslados.add(nodoTraslado); // Como se nos indica en el tp esto vale como O(1) amortizado.
            i++;
        }

        _heapRedituables = new Heap<HeapElement<Traslado>>(new GananciaComparator(), HeapIDS.HeapRedituables.ordinal(), arrayTraslados); //O(T)
        _heapAntiguos = new Heap<HeapElement<Traslado>>(new TimestampComparator(), HeapIDS.HeapAntiguos.ordinal(), arrayTraslados); //O(T)


        for(int n = 0; n < cantCiudades; n++){ //O(C)
            Ciudad ciudad = new Ciudad(n);
            HeapElement<Ciudad> nodoCiudad = new HeapElement<Ciudad>(ciudad, 1);
            nodoCiudad.setHandle(0, n);
            arrayCiudad.add(nodoCiudad); // Como se nos indica en el tp esto vale como O(1) amortizado.
        }

        _heapSuperHabit  = new Heap<HeapElement<Ciudad>>(new SuperHabitComparator(),0,arrayCiudad); //O(C)

    }

    public void registrarTraslados(Traslado[] traslados){ //O(T(log T))
        for(Traslado traslado : traslados) //O(T)
        {
            HeapElement<Traslado> nodoTraslado = new HeapElement<Traslado>(traslado, 2);
            _heapRedituables.add(nodoTraslado);//O(log T)
            _heapAntiguos.add(nodoTraslado);//O(log T)
        }
    }

    public int[] despacharMasRedituables(int n){ //(N(Log |T| + log |C|))
        if(n > _heapRedituables.size()){
            n = _heapRedituables.size();
        }
        int[] res = new int[n];
        HeapElement<Traslado> value;
        int handle;
        for(int i = 0; i < n; i++){ //O(N)
           value =  _heapRedituables.extractMax(); // O(log T)
           handle = value.getHandle(HeapIDS.HeapAntiguos.ordinal());
           _heapAntiguos.remove(handle); // O(log T)
           res[i] = value.getValue().getId();
           //////// Hasta aca remuevo de ambos heaps el Traslado y sumo el id en la res
           seteoGanaciaCiudad(value);
           seteoPerdidaCiudad(value);
           promediogan(value); //O(1)
           _despachados++;
        }
        return res;
    }




    public int[] despacharMasAntiguos(int n){ //(N(Log |T| + log |C|))
        if(n > _heapRedituables.size()){
            n = _heapRedituables.size();
        }
        int[] res = new int[n];
        HeapElement<Traslado> value;
        int handle;
        for(int i = 0; i < n; i++){ //O(N)
           value =  _heapAntiguos.extractMax(); // O(log T)
           handle = value.getHandle(HeapIDS.HeapRedituables.ordinal());
           _heapRedituables.remove(handle); // O(log T)
           res[i] = value.getValue().getId();
           //////// Hasta aca remuevo de ambos heaps el Traslado y sumo el id en la res
           seteoGanaciaCiudad(value); //O(log |C|)
           seteoPerdidaCiudad(value); //O(log |C|)
           promediogan(value);  //O(1)
           _despachados++;
        }
        return res;
    }

    public int ciudadConMayorSuperavit(){
        HeapElement<Ciudad> nodo = _heapSuperHabit.getMax(); //O(1)
        Ciudad ciudad = nodo.getValue();
        int id = ciudad.getId();
        return id;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){ //O(1)
        
        return _IdMaxganancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){ //O(1)
        // Implementar
        return _IdMaxperdida;
    }

    public int gananciaPromedioPorTraslado(){ //O(1)
       int res  = (int) Math.floor(_promedioGanancia/ _despachados);
        return res;
    }

    // FUNCIONES PRIVADAS PARA EL SETEO DE ESTADISTICAS

    private void promediogan(HeapElement<Traslado> nodo){ //O(1)
        Traslado traslado = nodo.getValue();
        int ganancia = traslado.getGananciaNeta();
        _promedioGanancia  = _promedioGanancia + ganancia;
    }

    private void seteoGanaciaCiudad(HeapElement<Traslado> nodo){ //O(log |C|)
        Traslado traslado = nodo.getValue();
        int index = traslado.getOrigen();
        HeapElement<Ciudad> origen = arrayCiudad.get(index);
        Ciudad Ciudad_origen = origen.getValue();
        Ciudad_origen.setGanancia(Ciudad_origen.getGanancia() + traslado.getGananciaNeta());
        Ciudad_origen.setSuperhabit(Ciudad_origen.getSuperhabit() + traslado.getGananciaNeta());
        _heapSuperHabit.ordenar(origen.getHandle(0)); //O(log |C|)
        maxgan(origen);  //0(1) 
    }

     private void seteoPerdidaCiudad(HeapElement<Traslado> nodo){ //O(log |C|)
        Traslado traslado = nodo.getValue();
        int index = traslado.getDestino();
        HeapElement<Ciudad> destino = arrayCiudad.get(index);
        Ciudad Ciudad_destino = destino.getValue();
        Ciudad_destino.setPerdida(Ciudad_destino.getPerdida() + traslado.getGananciaNeta());
        Ciudad_destino.setSuperhabit(Ciudad_destino.getSuperhabit() - traslado.getGananciaNeta());
        _heapSuperHabit.ordenar(destino.getHandle(0)); //O(log |C|)
        maxper(destino);  //0(1) 
    } 

    private void maxgan(HeapElement<Ciudad> value){ //0(1) 
        Ciudad ciudad = value.getValue();
        int ganancia_ciudad = ciudad.getGanancia();
        int id = ciudad.getId();
        if(_IdMaxganancia.size() == 0){
            _IdMaxganancia.add(id); //0(1) amortizado
            _Maxganancia = ganancia_ciudad;
        }else{
            if(_Maxganancia < ganancia_ciudad){
                _IdMaxganancia = new ArrayList<>();
                _IdMaxganancia.add(id); //0(1) amortizado
                _Maxganancia = ganancia_ciudad;
            }else if(_Maxganancia == ganancia_ciudad){
                _IdMaxganancia.add(id); //0(1) amortizado
            }

        }
    }

    private void maxper(HeapElement<Ciudad> value){  //0(1) 
        Ciudad ciudad = value.getValue();
        int perdida_ciudad = ciudad.getPerdida();
        int id = ciudad.getId();
        if(_IdMaxperdida.size() == 0){
            _IdMaxperdida.add(id); //0(1) amortizado
            _Maxperdida = perdida_ciudad;
        }else{
            if(_Maxperdida < perdida_ciudad ){
                _IdMaxperdida = new ArrayList<>();
                _IdMaxperdida.add(id); //0(1) amortizado
                _Maxperdida = perdida_ciudad;
            }else if( _Maxperdida == perdida_ciudad ){
                _IdMaxperdida.add(id); //0(1) amortizado
            }

        }

    }
    
}
