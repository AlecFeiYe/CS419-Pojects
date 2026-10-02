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
    public boolean allocate(Process process) {
        int processLimit = process.getLimit();

        for (Node node : MemoryFreeList) {
            if (node.getLimit() > processLimit) {
                process.setRelocation(node.getRelocation());

                System.out.println("\nA new memory has been allocated to Process " + process.pid + ": [" + process.getRelocation() + "," + (process.getRelocation() + process.getLimit()) + "]");

                MemoryFreeList.add(new Node(node.getRelocation() + processLimit, node.getLimit() - processLimit));
                MemoryFreeList.remove(node);
                updateMemory();
                FreeMemoryTrack();
                return true;
            } else if (node.getLimit() == processLimit) {
                process.setRelocation(node.getRelocation());
                MemoryFreeList.remove(node);
                updateMemory();
                FreeMemoryTrack();
                return true;
            }
        }
        return false;
    }

    @Override
    public void release(Process process) {
        System.out.println("\nThe memory of Process " + process.pid + " has been released: [" + process.getRelocation() + "," + (process.getRelocation() + process.getLimit()) + "]");
        MemoryFreeList.add(new Node(process.getRelocation(), process.getLimit()));
        updateMemory();
        FreeMemoryTrack();
    }

    private void updateMemory() {
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

    private void FreeMemoryTrack() {
        System.out.print("Free Memory Track:");
        for (Node n : MemoryFreeList) {
            System.out.print("[" + n.getRelocation() + "," + n.getNodeEndPoint() + "] ");
        }
        System.out.println("\n");
    }

    @Override
    public String getName() {
        return "Contiguous Allocation";
    }
}
