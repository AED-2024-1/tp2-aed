package aed.heap;

import aed.heap.interfaces.HeapNode;

public class HeapElement<T> implements HeapNode {
    private T _value;
    private int[] _indices; // Array que guarda en cada posición un handle, y el índice es el heapId

    public HeapElement(T value, int numberOfHeaps) {
        this._value = value;
        this._indices = new int[numberOfHeaps]; 
    }
    
    public T getValue() { // O(1)
        return _value;
    }

    @Override
    public void setHandle(int heapId, int index) {  // O(1)
        if (heapId >= 0 && heapId < _indices.length) {
            _indices[heapId] = index;
        }
    }

    @Override
    public int getHandle(int heapId) { // O(1)
        if (heapId >= 0 && heapId < _indices.length) {
            return _indices[heapId];
        }
        // Error: No existe ese heapId
        // Debe manejarse correctamente!
        return -1;
    }
}