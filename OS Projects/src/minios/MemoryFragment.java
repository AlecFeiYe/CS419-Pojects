package minios;

public class MemoryFragment {
    private final int relocation;
    private final int limit;
    private final Process userProcess;

    public MemoryFragment(int relocation, int memorySpace, Process userProcess) {
        this.relocation = relocation;
        this.limit = memorySpace;
        this.userProcess = userProcess;
    }

    public boolean isValid() {
        return userProcess.getState() != Process.State.BLOCKED;
    }
}
