package minios;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectAcceptanceTest {
    private Process makeProcess(int pid, int size) {
        List<Instruction> code = new ArrayList<>();
        code.add(new Instruction(Instruction.OpType.CPU, 2));

        return new Process(pid, 0, code, size);
    }

    @Test
    public void testContiguousAllocationAndRelease(){
        ContiguousAllocation manager = new ContiguousAllocation(100);

        Process p1 = makeProcess(1, 30);
        Process p2 = makeProcess(2, 40);

        assertTrue(manager.allocate(p1));
        assertTrue(manager.allocate(p2));

        assertEquals(0, p1.getBaseAddress());
        assertEquals(30, p2.getBaseAddress());

        manager.release(p1);
        manager.release(p2);

        List<Node> freeList = manager.getMemoryFreeList();

        // all units should be available again
        assertEquals(1, freeList.size());
        assertEquals(0, freeList.get(0).getRelocation());
        assertEquals(100, freeList.get(0).getLimit());
    }

    @Test
    public void testContiguousRejectsProcessThatIsTooLarge() {
        ContiguousAllocation manager = new ContiguousAllocation(100);

        Process p = makeProcess(1, 120);

        assertFalse(manager.allocate(p));
    }

    @Test
    public void testPagesAllocationCreatesCorrectPageTable() {
        PagedAllocation manager = new PagedAllocation(100, 4);

        Process p = makeProcess(1, 10);

        assertTrue(manager.allocate(p));

        assertNotNull(p.getPageTable());
        assertEquals(3, p.getPageTable().length);

        for (int frame : p.getPageTable()) {
            assertTrue(frame >= 0);
            assertTrue(frame < 25);
        }
    }

    @Test
    public void testPagedMemoryCanBeReleasedAndReused() {
        PagedAllocation manager = new PagedAllocation(100, 4);

        Process p1 = makeProcess(1, 100);

        assertTrue(manager.allocate(p1));

        manager.release(p1);

        Process p2 = makeProcess(2, 100);

        assertTrue(manager.allocate(p2));
    }

    @Test
    public void testPagedAllocationRejectsTooLargeProcess() {
        PagedAllocation manager = new PagedAllocation(100, 4);

        Process p = makeProcess(1, 104);

        assertFalse(manager.allocate(p));
    }

    @Test
    public void testRoundRobinUsesFIFOOrder() {
        RR rr = new RR();

        List<Process> queue = new ArrayList<>();

        Process p1 = makeProcess(1, 10);
        Process p2 = makeProcess(2, 10);
        Process p3 = makeProcess(3, 10);

        rr.addProcess(queue, p1);
        rr.addProcess(queue, p2);
        rr.addProcess(queue, p3);

        assertEquals(p1, rr.selectNextProcess(queue));

        rr.onCpuTick();
        rr.onCpuTick();

        assertTrue(rr.isQuantumExpired());

        rr.addProcess(queue, p1);

        assertEquals(p2, rr.selectNextProcess(queue));
        assertEquals(p3, rr.selectNextProcess(queue));
        assertEquals(p1, rr.selectNextProcess(queue));
    }

    @Test
    public void testSJFChoosesShortestCpuBurst() {
        SJF sjf = new SJF();

        List<Process> queue = new ArrayList<>();

        List<Instruction> longCode = new ArrayList<>();
        longCode.add(new Instruction(Instruction.OpType.CPU, 10));

        List<Instruction> shortCode = new ArrayList<>();
        shortCode.add(new Instruction(Instruction.OpType.CPU, 2));

        Process longProcess = new Process(1, 0, longCode, 10);
        Process shortProcess = new Process(2, 0, shortCode, 10);

        sjf.addProcess(queue, longProcess);
        sjf.addProcess(queue, shortProcess);

        assertEquals(shortProcess, sjf.selectNextProcess(queue));
    }
}
