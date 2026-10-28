package minios;

public class Node {
    private final int relocation;
    private final int limit;

    public Node(int relocation, int limit) {
        this.relocation = relocation;
        this.limit = limit;
    }

    public int getRelocation() {
        return relocation;
    }

    public int getLimit() {
        return limit;
    }

    public int getNodeEndPoint() {
        return relocation + limit;
    }
}
