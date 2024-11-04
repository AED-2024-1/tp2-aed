package aed.heap;

import aed.heap.interfaces.HeapNode;

public class HeapElement<T> implements HeapNode {
    private T value;
    private int[] indices; // Array to store indices for multiple heaps

    public HeapElement(T value, int len) {
        this.value = value;
        this.indices = new int[len]; // Array for up to 4 heaps (can be adjusted)
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
        return -1;
    }
}