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
    public Heap(Comparator<T> comparator, int heapId) { //O(1)
        _heap = new ArrayList<T>();
        _len = 0;
        _comparator = comparator;
        _heapId = heapId;
    }

    // Constructor para heapificar un array
    public Heap(Comparator<T> comparator, int heapId, ArrayList<T> elements) //O(N)
    {
        _len = elements.size();
        _comparator = comparator;
        _heapId = heapId;
        _heap = new ArrayList<T>(elements);

        heapify(); //O(N)
    }

    private void heapify() //O(N) Como se vio en la teorica practica y laboratorio esta funcion tiene complejidad O(N)
    {
        for (int i = 0; i < _len; i++) { // O(N)
            T element = _heap.get(i);
            element.setHandle(_heapId, i); // Seteamos el handle acá 
        }
        for (int i = 0; i < _len; i++) { // O(N)
            siftDown(_len - 1 - i);
        }
    }

    public void sort(int index) {  // O(log N)
        siftDown(index);  // O(log N)
        siftUp(index);    // O(log N)
    }

    public void add(T value) {  // O(log N)
        if (_len != _heap.size()) {
            _heap.set(_len, value);
        } else {
            _heap.add(value); // Como se nos indica en el tp esto vale como O(1) amortizado.
        }

        value.setHandle(_heapId, _len);  // Utilizamos las funcionalidades de la interfaz HeapNode, que nos provee una forma de
        _len++;                         // setear el valor del handle en cada posición (representada por el heapId)
        siftUp(_len - 1);
    }

    public T extractMax() {  // O(log N)
        return remove(0); // Reutilización de la función remove
    }

    public T getMax() {     //O(1)
        return _heap.get(0);
    }

    public T remove(int handle) {  // O(log N)
        if (handle < 0 || handle >= _len) {
            return null;
        }

        T lastElement = _heap.get(_len - 1);
        T removedElement = _heap.get(handle);

        _heap.set(handle, lastElement);
        lastElement.setHandle(_heapId, handle);

        _heap.set(_len - 1, null);
        _len--;

        // _Len es un contador que va desde 0 hasta lo que nosotros representemos como la longitud del heap
        // es trivial ver que si eliminamos el ultimo elemento, para nosotros nada mas es necesario decir _len--
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

        T child = _heap.get(index);

        int father_index = (int) Math.floor((double) (index - 1) / 2);

        T father = _heap.get(father_index);

        if (_comparator.compare(father, child) < 0) {
            _heap.set(father_index, child);
            _heap.set(index, father);

            father.setHandle(_heapId, index);
            child.setHandle(_heapId, father_index);

            siftUp(father_index);
        }
    }

    private void siftDown(int index) {  // O(log N)
        if (index >= _len) {
            return;
        }

        int left_child_index = 2 * index + 1;
        int right_child_index = 2 * index + 2;
        int largest = index;

        if (left_child_index < _len && _comparator.compare(_heap.get(left_child_index), _heap.get(largest)) > 0) {
            largest = left_child_index;
        }

        if (right_child_index < _len && _comparator.compare(_heap.get(right_child_index), _heap.get(largest)) > 0) {
            largest = right_child_index;
        }

        if (largest != index) {
            T temp = _heap.get(index);
            _heap.set(index, _heap.get(largest));
            _heap.set(largest, temp);

            _heap.get(index).setHandle(_heapId, index);
            _heap.get(largest).setHandle(_heapId, largest);

            siftDown(largest);
        }
    }

    public int size() { //O(1)
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
