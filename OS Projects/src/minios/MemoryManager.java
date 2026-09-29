package minios;
import java.util.LinkedList;
import java.util.List;

public class MemoryManager {
    private final List<Node> MemoryFreeList;

    public MemoryManager(int limit) {
        MemoryFreeList = new LinkedList<Node>();
        MemoryFreeList.add(new Node(0, limit));
    }

    public boolean allocateMemory(Process process) {
        int processLimit = process.getLimit();

        for (Node node : MemoryFreeList) {
            if (node.getLimit() > processLimit) {
                process.setRelocation(node.getRelocation());
                MemoryFreeList.add(new Node(node.getRelocation() + processLimit, node.getLimit() -processLimit));
                MemoryFreeList.remove(node);
                MemoryFreeList.add(new Node(node.getRelocation() + processLimit, node.getNodeEndPoint()));
                return true;
            } else if (node.getLimit() == processLimit) {
                process.setRelocation(node.getRelocation());
                MemoryFreeList.remove(node);
                return true;
            }
        }
        return false;
    }

    public void freeMemory(Process process) {
        MemoryFreeList.add(new Node(process.getRelocation(), process.getRelocation() + process.getLimit()));
        updateMemory();
    }

    private void updateMemory() {
        MemoryFreeList.sort((a, b) -> a.getRelocation() - b.getRelocation());

        for(int i=0;i<MemoryFreeList.size()-1;){
            Node a = MemoryFreeList.get(i);
            Node b = MemoryFreeList.get(i+1);
            if(a.getNodeEndPoint() == b.getRelocation()){
                Node merge = new Node(a.getRelocation(), b.getNodeEndPoint());
                MemoryFreeList.remove(i+1);
                MemoryFreeList.set(i,merge);
            }
            else {
                i++;
            }

        }
    }
}
