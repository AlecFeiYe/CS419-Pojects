package minios;

import java.util.ArrayList;
import java.util.List;

public class Process {
    public enum State {NEW, READY, RUNNING, BLOCKED, TERMINATED}

    public final int pid;
    public final int arrivalTime;
    private int baseAddress = -1;
    private final int size;
    private int[] pageTable;

    public State state = State.NEW;
    public final List<Instruction> code;
    public int programCounter = 0;

    public Process(int pid, int arrivalTime, List<Instruction> code, int limit) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.code = new ArrayList<>(code);
        this.limit = limit;
    }

    public Instruction getCurrentInstruction() {
        if (programCounter < code.size()) {
            return code.get(programCounter);
        } else {
            return null;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setRelocation(int relocation) {
        this.relocation = relocation;
    }

    public int getRelocation() {
        return relocation;
    }

    public int[] getPageTable() {
        return pageTable;
    }

    public void setPageTable(int[] pageTable) {
        this.pageTable = pageTable;
    }

    public void modifyPageTable(int index, int value) {
        this.pageTable[index] = value;
    }
}