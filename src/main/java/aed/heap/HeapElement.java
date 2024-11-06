package aed.heap;

import aed.heap.interfaces.HeapNode;

public class HeapElement<T> implements HeapNode {
    private T value;
    private int[] indices; // Array que guarda en cada posición un handle, y el índice es el heapId

    public HeapElement(T value, int numberOfHeaps) {
        this.value = value;
        this.indices = new int[numberOfHeaps]; 
    }
    
    public T getValue() {
        return value;
    }

    @Override
    public void setHandle(int heapId, int index) {
        if (heapId >= 0 && heapId < indices.length) {
            indices[heapId] = index;
        }
    }

    @Override
    public int getHandle(int heapId) {
        if (heapId >= 0 && heapId < indices.length) {
            return indices[heapId];
        }
        // Error: No existe ese heapId
        // Debe manejarse correctamente!
        return -1;
    }
}