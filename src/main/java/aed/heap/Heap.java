package aed.heap;

import java.util.ArrayList;
import java.util.Comparator;

import aed.heap.interfaces.HeapNode;

public class Heap<T extends HeapNode> {

    private ArrayList<T> _heap;
    private Comparator<T> _comparator;
    private int _len;
    private int _heapId;
    // ¿Por qué un heapID? Esto es debido a que mapearemos los heapsId como los indices de un array
    // en el objeto HeapElement, y cada valor determinará el handle según el heap

    // DI: inyectamos comparador según el heap que queramos
    public Heap(Comparator<T> comparator, int heapId) { // O(1)
        _heap = new ArrayList<T>();
        _len = 0;
        _comparator = comparator;
        _heapId = heapId;
    }

    // Constructor para heapificar un array
    public Heap(Comparator<T> comparator, int heapId, ArrayList<T> elements) // O(N)
    {
        _len = elements.size();
        _comparator = comparator;
        _heapId = heapId;
        _heap = new ArrayList<T>(elements); // O(N)

        heapify(); //O(N)

        // O(N + N) = O(N)
    }

    private void heapify() // O(N) como lo vimos en la teórica, laboratorio y práctica por algoritmo de Floyd
    {
        for (int i = 0; i < _len; i++) { // O(N)
            T element = _heap.get(i); // O(1)
            element.setHandle(_heapId, i); // Seteamos el handle acá, O(1)
        }
        for (int i = 0; i < _len; i++) { // O(N)
            siftDown(_len - 1 - i); // O(log N)
        }
    }

    public void sort(int index) {  // O(log N)
        siftDown(index);  // O(log N)
        siftUp(index);    // O(log N)
    }
    
    public void add(T value) {  // O(log N)
        _heap.add(value); // Como se nos indica en el tp esto vale como O(1) amortizado.
        value.setHandle(_heapId, _len);  // O(1), Utilizamos las funcionalidades de la interfaz HeapNode, que nos provee una forma de
        _len++;                         // setear el valor del handle en cada posición (representada por el heapId)
        siftUp(_len - 1); // O(log N)
    }

    public T extractMax() {  // O(log N)
        return remove(0); // Reutilización de la función remove, O(log N)
    }

    public T getMax() {     // O(1)
        return _heap.get(0);
    }

    public T remove(int handle) {  // O(log N)
        if (handle < 0 || handle >= _len) { // O(1)
            return null;
        }

        T lastElement = _heap.get(_len - 1); // O(1)
        T removedElement = _heap.get(handle); // O(1)

        _heap.set(handle, lastElement); // O(1)
        lastElement.setHandle(_heapId, handle); // O(1)

        _heap.remove(_len - 1); // Removemos último elemento, O(1)
        _len--; 

        if (handle < _len) {
            siftUp(handle); // O(log N)
            siftDown(handle); // O(log N)
        }

        return removedElement;
    }

    private void siftUp(int index) {  // O(log N)
        if (index == 0) {
            return;
        }

        T child = _heap.get(index); // O(1)

        int father_index = (int) Math.floor((double) (index - 1) / 2); // O(1)

        T father = _heap.get(father_index); // O(1)

        if (_comparator.compare(father, child) < 0) { 
            _heap.set(father_index, child); // O(1)
            _heap.set(index, father); // O(1)

            father.setHandle(_heapId, index); // O(1)
            child.setHandle(_heapId, father_index); // O(1)

            siftUp(father_index); // O(log N)
        } // O(log N)
    }

    private void siftDown(int index) {  // O(log N)
        if (index >= _len) {
            return;
        }

        int left_child_index = 2 * index + 1; // O(1)
        int right_child_index = 2 * index + 2; // O(1)
        int largest = index; // O(1)

        if (left_child_index < _len && _comparator.compare(_heap.get(left_child_index), _heap.get(largest)) > 0) {
            largest = left_child_index; // O(1)
        }

        if (right_child_index < _len && _comparator.compare(_heap.get(right_child_index), _heap.get(largest)) > 0) {
            largest = right_child_index; // O(1)
        }

        if (largest != index) {
            T temp = _heap.get(index); // O(1)
            _heap.set(index, _heap.get(largest)); // O(1)
            _heap.set(largest, temp); // O(1)

            _heap.get(index).setHandle(_heapId, index); // O(1)
            _heap.get(largest).setHandle(_heapId, largest); // O(1)

            siftDown(largest); // O(log N)
        }
    }

    public int size() { // O(1)
        return _heap.size();
    }

    @Override
    public String toString() {
        String res = "[";
        int i;
        HeapElement<T> val;
        for (i = 0; i < _heap.size() - 1; i++) {
            val = (HeapElement) _heap.get(i);

            if (_heap.get(i) != null) {
                res = res + val.getValue() + ",";
            }
        }
        val = (HeapElement) _heap.get(i);

        if (_heap.get(i) != null) {
            res = res + val.getValue() + "]";
        }
        return res;
    }
}
