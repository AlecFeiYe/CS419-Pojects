package minios;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ContiguousAllocation implements MemoryManagement {
    private final List<Node> MemoryFreeList;
    private int physicalMemory[];

    public ContiguousAllocation(int size) {
        this.MemoryFreeList = new LinkedList<Node>();
        this.MemoryFreeList.add(new Node(0, size));
        this.physicalMemory = new int[size];
    }

    @Override
    public boolean allocate(Process process, int clock) {
        int processLimit = process.getSize();

        for (int i = 0; i < MemoryFreeList.size(); i++) {
            Node node = MemoryFreeList.get(i);
            if (node.getLimit() >= processLimit) {

                process.setBaseAddress(node.getRelocation());

                System.out.println("[Tick " + clock + "] Process " + process.pid
                        + " is admitted. Contiguous Allocation. Starting memory address: "
                        + process.getBaseAddress() + " Allocated size : " + process.getSize());

                if (node.getLimit() > processLimit) {

                    MemoryFreeList.set(i, new Node(
                            node.getRelocation() + processLimit,
                            node.getLimit() - processLimit));
                } else {

                    MemoryFreeList.remove(i);
                }
                coalesceMemory();
                FreeMemoryTrack(clock);
                return true;
            }
        }

        System.out.println("[Tick " + clock + "] Process " + process.pid
                + " cannot be admitted. Requested size: " + processLimit
                + ". All free blocks (starting address, size):");
        for (Node node : MemoryFreeList) {
            System.out.println("[Tick " + clock + "]     start=" + node.getRelocation()
                    + ", size=" + node.getLimit());
        }
        return false;
    }

    @Override
    public void release(Process process, int clock) {
        int base = process.getBaseAddress();
        int size = process.getSize();
        int end = base + size;

        System.out.println("[Tick " + clock + "] The memory of Process " + process.pid
                + " has been released: [" + base + "," + end + "]");

        boolean willCoalesce = false;
        for (Node node : MemoryFreeList) {
            if (node.getNodeEndPoint() == base || node.getRelocation() == end) {
                willCoalesce = true;
                break;
            }
        }
        MemoryFreeList.add(new Node(base, size));
        MemoryFreeList.sort(Comparator.comparingInt(Node::getRelocation));

        if (willCoalesce) {

            System.out.print("[Tick " + clock + "] Free blocks BEFORE coalescing:");
            printFreeBlocksWithSize();

            coalesceMemory();

            System.out.print("[Tick " + clock + "] Free blocks AFTER coalescing:");
            printFreeBlocksWithSize();
        } else {

            FreeMemoryTrack(clock);
        }
    }

    private void coalesceMemory() {
        MemoryFreeList.sort(Comparator.comparingInt(Node::getRelocation));

        for (int i = 0; i < MemoryFreeList.size() - 1; ) {
            Node a = MemoryFreeList.get(i);
            Node b = MemoryFreeList.get(i + 1);
            if (a.getNodeEndPoint() == b.getRelocation()) {
                Node merge = new Node(a.getRelocation(), a.getLimit() + b.getLimit());
                MemoryFreeList.remove(i + 1);
                MemoryFreeList.set(i, merge);
            } else {
                i++;
            }
        }
    }

    //only for test;
    public List<Node> getMemoryFreeList() {
        return MemoryFreeList;
    }

    private void FreeMemoryTrack(int clock) {
        System.out.print("[Tick " + clock + "] Free Memory Track:");
        for (Node n : MemoryFreeList) {
            System.out.print("[" + n.getRelocation() + "," + n.getNodeEndPoint() + "] ");
        }
        System.out.println();
    }

    private void printFreeBlocksWithSize() {
        for (Node n : MemoryFreeList) {
            System.out.print(" [start=" + n.getRelocation() + ", size=" + n.getLimit() + "]");
        }
        System.out.println();
    }

    @Override
    public String getName() {
        return "Contiguous Allocation";
    }

    @Override
    public void store(Instruction instruction) {
        this.physicalMemory[instruction.address] = instruction.value;
    }

    @Override
    public int load(Instruction instruction) {
        return this.physicalMemory[instruction.address];
    }

    @Override
    public int translate(Process p, int addr) {
        if (addr > p.getSize()) {
            System.out.println("Invalid address: Memory Out Of Bounds");
            return -1;
        }
        else {
            return this.physicalMemory[addr];
        }
    }
}
