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

    // size(limit register): this is from the trace parser
    // relocation register (base memory address): specified by kernel

    public Process(int pid, int arrivalTime, List<Instruction> code, int size) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.code = new ArrayList<>(code);
        this.size = size;
    }

    public Instruction getCurrentInstruction() {
        if (programCounter < code.size()) {
            return code.get(programCounter);
        } else {
            return null;
        }
    }

    public int getSize() {
        return size;
    }

    public void setBaseAddress(int relocation) {
        this.baseAddress = relocation;
    }

    public int getBaseAddress() {
        return baseAddress;
    }

    public int[] getPageTable() {
        return pageTable;
    }

    public void setPageTable(int neededFrames) {
        this.pageTable = new int[neededFrames];
    }

    public void modifyPageTable(int index, int value) {
        this.pageTable[index] = value;
    }

    // Total length of the current CPU burst
    // A CPU burst is a run of consecutive COMPUTE/LOAD/STORE instructions
    public int currentCPUBurstLength() {
        int total = 0;
        for (int i = programCounter; i < code.size(); i++) {
            Instruction in = code.get(i);
            // All instructions except IO are CPU instructions
            if (in.type == Instruction.OpType.IO) break;
            total += in.remainingTicks;
        }
        return total;
    }

    // Ticks left in the current CPU burst only
    // A CPU burst is a run of consecutive COMPUTE/LOAD/STORE instructions
    public int currentCPUBurstRemaining() {
        int total = 0;
        for (int i = programCounter; i < code.size(); i++) {
            Instruction in = code.get(i);
            if (in.type == Instruction.OpType.IO) break;
            total += in.remainingTicks;
        }
        return total;
    }
}