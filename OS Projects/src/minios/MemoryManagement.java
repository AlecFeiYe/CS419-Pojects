package minios;

public interface MemoryManagement {
    boolean allocate(Process p,int clock);
    void release(Process p,int clock);
    String getName();
}
