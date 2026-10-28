package minios;

public interface MemoryManagement {
    boolean allocate(Process p,int clock);
    void release(Process p,int clock);
    String getName();
    int translate(Process p, int addr);
    void store(Instruction instruction);
    int load(Instruction instruction);
}
