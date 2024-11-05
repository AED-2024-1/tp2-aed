package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;


import org.junit.jupiter.api.Test;
import aed.heap.Heap;

import org.junit.jupiter.api.BeforeEach;
public class HeapTests {
    @Test
    void heap_vacio(){
        Heap heap = new Heap<>(null, 0);
        heap.add(new Traslado(2, 1, 1, 400, 0));
        
    }
}
