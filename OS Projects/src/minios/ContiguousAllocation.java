package minios;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ContiguousAllocation implements MemoryManagement {
    private final List<Node> MemoryFreeList;

    public ContiguousAllocation(int limit) {
        MemoryFreeList = new LinkedList<Node>();
        MemoryFreeList.add(new Node(0, limit));
    }

    @Override
    public boolean allocate(Process process, int clock) {
        int processLimit = process.getSize();

        for (Node node : MemoryFreeList) {
            if (node.getLimit() > processLimit) {
                process.setBaseAddress(node.getRelocation());

                System.out.println("[Tick " + clock + "] Process " + process.pid + " is admitted. Contiguous Allocation. Starting memory address: " + process.getBaseAddress() + " Allocated size : " + process.getSize());

                MemoryFreeList.add(new Node(node.getRelocation() + processLimit, node.getLimit() - processLimit));
                MemoryFreeList.remove(node);
                coalesceMemory();
                FreeMemoryTrack(clock);
                return true;
            } else if (node.getLimit() == processLimit) {
                process.setBaseAddress(node.getRelocation());

                System.out.println("[Tick " + clock + "] Process " +
                        process.pid + " is admitted. Contiguous Allocation. " +
                        "Starting memory address: " + process.getBaseAddress() +
                        " Allocated size: " + process.getSize());

                MemoryFreeList.remove(node);
                coalesceMemory();
                FreeMemoryTrack(clock);
                return true;
            }
        }
        System.out.print("[Tick " + clock + "] Process " + process.pid +
                " cannot be admitted. Needs " + process.getSize() +
                " units. Free blocks: ");

        for (Node node : MemoryFreeList) {
            System.out.print(
                    "[start=" + node.getRelocation() +
                    ", size=" + node.getLimit() + "] "
            );
        }

        System.out.println();
        return false;
    }

    @Override
    public void release(Process process, int clock) {
        System.out.println("[Tick " + clock + "] Process " +
                process.pid + " releases memory. Starting address: " +
                process.getBaseAddress() + ", size: " +
                process.getSize());
        MemoryFreeList.add(new Node(process.getBaseAddress(), process.getSize()));

        System.out.print("[Tick " + clock +
                "] Free blocks before coalescing: ");
        printFreeBlocks();

        int beforeSize = MemoryFreeList.size();
        coalesceMemory();

        if (MemoryFreeList.size() < beforeSize) {
            System.out.print("[Tick " + clock +
                    "] Free blocks after coalescing: ");
            printFreeBlocks();
        }
        FreeMemoryTrack(clock);
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

    @Override
    public String getName() {
        return "Contiguous Allocation";
    }

    private void printFreeBlocks() {
        for (Node node: MemoryFreeList) {
            System.out.print(
                    "[start= " + node.getRelocation() +
                    ", size = " + node.getLimit() + "] "
            );
        }
        System.out.println();
    }
}
