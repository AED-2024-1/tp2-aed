package aed.heap;

import java.util.ArrayList;

import aed.heap.interfaces.HeapNode;

public class HeapElement<T> implements HeapNode {
    private T _value;
    private ArrayList<Integer> _indices; // Array que guarda en cada posición un handle, y el índice es el heapId

    public HeapElement(T value) {
        this._value = value;
        this._indices = new ArrayList<Integer>();
    }
    
    public T getValue() { // O(1)
        return _value;
    }

    @Override
    public void setHandle(int heapId, int index) {  // O(1)
        if (heapId >= 0 && heapId < _indices.size()) {
            _indices.set(heapId, index);
        } 
        else {
            _indices.add(index);
        }
    }

    @Override
    public int getHandle(int heapId) { // O(1)
        if (heapId >= 0 && heapId < _indices.size()) {
            return _indices.get(heapId);
        }
        // Error: No existe ese heapId
        // Debe manejarse correctamente!
        return -1;
    }
}