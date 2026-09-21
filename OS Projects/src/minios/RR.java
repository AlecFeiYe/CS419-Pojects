package minios;

import java.util.List;

public class RR implements SchedulingAlgo {
    public static final int TIME_QUANTUM = 2;
    private int timeUsed = 0;

    @Override
    public void addProcess(List<Process> readyQueue, Process p) {
        readyQueue.add(p);
    }

    @Override
    public Process selectNextProcess(List<Process> readyQueue) {
        if (readyQueue.isEmpty()) {
            return null;
        } else {
            return readyQueue.remove(0);
        }
    }

    public void onCpuTick() {
        timeUsed++;
    }

    public boolean isQuantumExpired() {
        if (timeUsed >= TIME_QUANTUM) {
            timeUsed = 0;
            return true;
        }
        return false;
    }
}
