package aed.heap;

import java.util.ArrayList;
import java.util.Comparator;

import aed.Nodos.NodoTraslados;

public class Heap<T> {

    private ArrayList<T> _heap;
    private Comparator<T> _comparator;
    private int _len;
    private boolean Anti;

    public Heap(Comparator<T> comparator,boolean anti) {
        _heap = new ArrayList<>();
        _len = 0;
        _comparator = comparator;
        Anti = anti;
        }

    public int add(T value) {
        _heap.add(value);
        _len++;
        siftUp(_len - 1);
        return _len - 1;
    }

    public T extractMax() {
        return _heap.get(0);
    }

    public void remove(int handle) {
        if (handle < 0 || handle >= _len) {
            return;
        }

        T removed_value = _heap.get(handle);
        T lastElement = _heap.get(_len - 1);

        _heap.set(handle, lastElement);
        _heap.set(_len - 1, null);
        _len--;

        // _Len es un contador que va desde 0 hasta lo que nosotros representemos como la longitud del heap
        // es trivial ver que si eliminamos el ultimo elemento, para nosotros nada mas es necesario decir _len--

        if (handle < _len) {
            siftUp(handle); // O(logn)
            siftDown(handle); // O(logn)
        }

        // O(logn)
        
    }

    private void siftUp(int index) {
        if (index == 0) {
            return;
        }

        T child = _heap.get(index);

        int father_index = (int) Math.floor((double) (index - 1) / 2);

        T father = _heap.get(father_index);

        if (_comparator.compare(father, child) < 0) {
            _heap.set(father_index, child);
            handlenodo(child, father_index);
            _heap.set(index, father);
            handlenodo(father, index);
            siftUp(father_index);
        }
    }

    private void siftDown(int index) {
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
            handlenodo(_heap.get(largest), index);
            _heap.set(largest, temp);
            handlenodo(temp, largest);
            siftDown(largest);
        }
    }

    private void handlenodo(T tipo, int handle){
        if(tipo.getClass() ==  NodoTraslados.class){
            NodoTraslados tipoN = (NodoTraslados) tipo;
            if(Anti){
               tipoN.setAntiguedad(handle);            
            }else{
               tipoN.setRedituable(handle);
            }
        }

    }
}
