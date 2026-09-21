
package minios;



import java.util.List;

public class SJF implements SchedulingAlgo {

    @Override
    public void addProcess(List<Process> readyQueue, Process p) {

        readyQueue.add(p);
    }

    @Override
    public Process selectNextProcess(List<Process> readyQueue) {
        if (readyQueue.isEmpty()) {
            return null;
        }

        int shortestIndex = 0;
        int minDuration = Integer.MAX_VALUE;


        for (int i = 0; i < readyQueue.size(); i++) {
            Process p = readyQueue.get(i);
            Instruction inst = p.getCurrentInstruction();

            if (inst != null) {
                int remaining = inst.remainingTicks;
                if (remaining < minDuration) {
                    minDuration = remaining;
                    shortestIndex = i;
                }
            }
        }


        return readyQueue.remove(shortestIndex);
    }
}
