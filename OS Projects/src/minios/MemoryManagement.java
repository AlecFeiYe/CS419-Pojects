package minios;

public interface MemoryManagement {
    boolean allocate(Process p);
    void release(Process p);
    String getName();
}
