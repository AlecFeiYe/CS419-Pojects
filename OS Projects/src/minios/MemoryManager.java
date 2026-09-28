package minios;

public class MemoryManager {
    private final int memorySpace;
    private final boolean memory[];

    public MemoryManager(int limit) {
        MemoryFreeList = new LinkedList<Node>();
        MemoryFreeList.add(new Node(0, limit));
    }

    public boolean allocateMemory(Process process) {
        int processLimit = process.getLimit();

        for (Node node : MemoryFreeList) {
            if (node.getLimit() > processLimit) {
                process.setRelocation(node.getRelocation());
                MemoryFreeList.remove(node);
                MemoryFreeList.add(new Node(node.getRelocation() + processLimit, node.getNodeEndPoint()));
                return true;
            } else if (node.getRelocation() == processLimit) {
                process.setRelocation(node.getRelocation());
                MemoryFreeList.remove(node);
                return true;
            }
        }
        return false;
    }


}
