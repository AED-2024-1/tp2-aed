package aed;

import java.util.ArrayList;

import aed.heap.Heap;
import aed.nodos.*;
import aed.heap.HeapElement;
import aed.implementation.HeapIDS;
import aed.implementation.comparators.GananciaComparator;
import aed.implementation.comparators.SuperavitComparator;
import aed.implementation.comparators.TimestampComparator;

public class BestEffort {

    private Heap<HeapElement<Traslado>> _heapRedituables;
    private Heap<HeapElement<Traslado>> _heapAntiguos;
    private Heap<HeapElement<Ciudad>> _heapSuperavit;
    private int _maxGanancia;
    private int _maxPerdida;
    private int _promedioGanancia;
    private int _despachados;
    ArrayList<HeapElement<Ciudad>> _arrayCiudad;
    private ArrayList<Integer> _idMaxGanancia;
    private ArrayList<Integer> _idMaxPerdida;

    public BestEffort(int cantCiudades, Traslado[] traslados) { //O(|T|+|C|)
        ArrayList<HeapElement<Traslado>> arrayTraslados = new ArrayList<HeapElement<Traslado>>();
        _arrayCiudad = new ArrayList<HeapElement<Ciudad>>();
        _idMaxGanancia = new ArrayList<>();
        _idMaxPerdida = new ArrayList<>();

        for (Traslado traslado : traslados) //O(|T|)
        {
            HeapElement<Traslado> nodoTraslado = new HeapElement<Traslado>(traslado, 2);
            arrayTraslados.add(nodoTraslado); // Como se nos indica en el tp esto vale como O(1) amortizado.
        }

        _heapRedituables = new Heap<HeapElement<Traslado>>(new GananciaComparator(), HeapIDS.HeapRedituables.ordinal(), arrayTraslados); //O(T)
        _heapAntiguos = new Heap<HeapElement<Traslado>>(new TimestampComparator(), HeapIDS.HeapAntiguos.ordinal(), arrayTraslados); //O(T)

        for (int n = 0; n < cantCiudades; n++) { //O(|C|)
            Ciudad ciudad = new Ciudad(n);
            HeapElement<Ciudad> nodoCiudad = new HeapElement<Ciudad>(ciudad, 1);
            nodoCiudad.setHandle(0, n);
            _arrayCiudad.add(nodoCiudad); // Como se nos indica en el tp esto vale como O(1) amortizado.
        }

        _heapSuperavit = new Heap<HeapElement<Ciudad>>(new SuperavitComparator(), 0, _arrayCiudad); //O(C)
    } 

    public void registrarTraslados(Traslado[] traslados) { //O(|T|(log T))
        for (Traslado traslado : traslados) //O(|T|)
        {
            HeapElement<Traslado> nodoTraslado = new HeapElement<Traslado>(traslado, 2);
            _heapRedituables.add(nodoTraslado);//O(log T)
            _heapAntiguos.add(nodoTraslado);//O(log T)
        }
    }

    public int[] despacharMasRedituables(int n) { //(N(Log |T| + log |C|))
        if (n > _heapRedituables.size()) {
            n = _heapRedituables.size();
        }
        int[] res = new int[n];
        HeapElement<Traslado> value;
        int handle;
        for (int i = 0; i < n; i++) { //O(N)
            value = _heapRedituables.extractMax(); // O(log T)
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

    public int[] despacharMasAntiguos(int n) { //(N(Log |T| + log |C|))
        if (n > _heapRedituables.size()) {
            n = _heapRedituables.size();
        }
        int[] res = new int[n];
        HeapElement<Traslado> value;
        int handle;
        for (int i = 0; i < n; i++) { //O(N)
            value = _heapAntiguos.extractMax(); // O(log T)
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

    public int ciudadConMayorSuperavit() {
        HeapElement<Ciudad> nodo = _heapSuperavit.getMax(); //O(1)
        Ciudad ciudad = nodo.getValue();
        int id = ciudad.getId();
        return id;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() { //O(1)
        return _idMaxGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() { //O(1)
        // Implementar
        return _idMaxPerdida;
    }

    public int gananciaPromedioPorTraslado() { //O(1)
        int res = (int) Math.floor(_promedioGanancia / _despachados);
        return res;
    }

    // FUNCIONES PRIVADAS PARA EL SETEO DE ESTADISTICAS
    private void promediogan(HeapElement<Traslado> nodo) { //O(1)
        Traslado traslado = nodo.getValue();
        int ganancia = traslado.getGananciaNeta();
        _promedioGanancia = _promedioGanancia + ganancia;
    }

    private void seteoGanaciaCiudad(HeapElement<Traslado> nodo) { //O(log |C|)
        Traslado traslado = nodo.getValue();
        int index = traslado.getOrigen();
        HeapElement<Ciudad> origen = _arrayCiudad.get(index);

        Ciudad ciudadOrigen = origen.getValue();
        ciudadOrigen.setGanancia(ciudadOrigen.getGanancia() + traslado.getGananciaNeta());
        ciudadOrigen.setSuperavit(ciudadOrigen.getSuperavit() + traslado.getGananciaNeta());

        _heapSuperavit.sort(origen.getHandle(0)); //O(log |C|)

        maxGanancia(origen);  //0(1) 
    }

    private void seteoPerdidaCiudad(HeapElement<Traslado> nodo) { //O(log |C|)
        Traslado traslado = nodo.getValue();
        int index = traslado.getDestino();
        HeapElement<Ciudad> destino = _arrayCiudad.get(index);
        Ciudad Ciudad_destino = destino.getValue();
        Ciudad_destino.setPerdida(Ciudad_destino.getPerdida() + traslado.getGananciaNeta());
        Ciudad_destino.setSuperavit(Ciudad_destino.getSuperavit() - traslado.getGananciaNeta());
        _heapSuperavit.sort(destino.getHandle(0)); //O(log |C|)
        maxPerdida(destino);  //0(1) 
    }

    private void maxGanancia(HeapElement<Ciudad> value) { //0(1) 
        Ciudad ciudad = value.getValue();
        int gananciaCiudad = ciudad.getGanancia();
        int id = ciudad.getId();
        if (_idMaxGanancia.size() == 0) {
            _idMaxGanancia.add(id); //0(1) amortizado
            _maxGanancia = gananciaCiudad;
        } else {
            if (_maxGanancia < gananciaCiudad) {
                _idMaxGanancia = new ArrayList<>();
                _idMaxGanancia.add(id); //0(1) amortizado
                _maxGanancia = gananciaCiudad;
            } else if (_maxGanancia == gananciaCiudad) {
                _idMaxGanancia.add(id); //0(1) amortizado
            }

        }
    }

    private void maxPerdida(HeapElement<Ciudad> value) {  //0(1) 
        Ciudad ciudad = value.getValue();
        int perdidaCiudad = ciudad.getPerdida();
        int id = ciudad.getId();
        if (_idMaxPerdida.size() == 0) {
            _idMaxPerdida.add(id); //0(1) amortizado
            _maxPerdida = perdidaCiudad;
        } else {
            if (_maxPerdida < perdidaCiudad) {
                _idMaxPerdida = new ArrayList<>();
                _idMaxPerdida.add(id); //0(1) amortizado
                _maxPerdida = perdidaCiudad;
            } else if (_maxPerdida == perdidaCiudad) {
                _idMaxPerdida.add(id); //0(1) amortizado
            }
        }
    }
}
