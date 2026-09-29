package minios;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryManagerTest {

    @Test
    public void testAllocationWithEnoughMemory() {

        MemoryManager manager = new MemoryManager(100);
        List<Instruction> list = new ArrayList<>();
        List<Node> nodes = manager.getMemoryFreeList();

        Process p = new Process(1, 0, list, 10);
        assertEquals(1, nodes.size());
        assertEquals(0, nodes.get(0).getRelocation());
        assertEquals(100, nodes.get(0).getNodeEndPoint());

        assertTrue(manager.allocateMemory(p));

        assertEquals(1, nodes.size());
        assertEquals(10, nodes.get(0).getRelocation());
        assertEquals(100, nodes.get(0).getNodeEndPoint());

        Process p2 = new Process(1, 0, list, 50);

        assertTrue(manager.allocateMemory(p2));
        assertEquals(1, nodes.size());
        assertEquals(60, nodes.get(0).getRelocation());
        assertEquals(100, nodes.get(0).getNodeEndPoint());
    }

    @Test
    public void testAllocationWithoutEnoughMemory() {

        MemoryManager manager = new MemoryManager(100);
        List<Instruction> list = new ArrayList<>();
        List<Node> nodes = manager.getMemoryFreeList();

        Process p = new Process(1, 0, list, 110);

        assertFalse(manager.allocateMemory(p));

    }

    @Test
    public void testFreeMemoryWithWantedMemory() {

        MemoryManager manager = new MemoryManager(100);
        List<Instruction> list = new ArrayList<>();
        List<Node> nodes = manager.getMemoryFreeList();

        Process p = new Process(1, 0, list, 10);

        assertTrue(manager.allocateMemory(p));

        manager.freeMemory(p);

        assertEquals(1, nodes.size());
        assertEquals(0, nodes.get(0).getRelocation());
        assertEquals(100, nodes.get(0).getNodeEndPoint());

    }

/**
 * To be honest we don't want this happen and we shouldn't free a memory fragment which is not being used.
 */
//    @Test
//    public void testFreeMemoryWithoutWantedMemory() {
//
//        MemoryManager manager = new MemoryManager(100);
//        List<Instruction> list = new ArrayList<>();
//        List<Node> nodes = manager.getMemoryFreeList();
//
//        Process p = new Process(1, 0, list, 10);
//        Process p2 = new Process(1, 0, list, 20);
//        assertTrue(manager.allocateMemory(p));
//
//        manager.freeMemory(p2);
//
//        assertEquals(1, nodes.size());
//        assertEquals(10, nodes.get(0).getRelocation());
//        assertEquals(100, nodes.get(0).getNodeEndPoint());
//    }
}