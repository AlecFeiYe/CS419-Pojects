
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
        int shortestDuration = readyQueue.get(0).getCurrentInstruction().duration;

        for (int i = 1; i < readyQueue.size(); i++) {
            Process p = readyQueue.get(i);
            Instruction inst = p.getCurrentInstruction();


            if (inst == null) {
                continue;
            }

            if (inst.duration < shortestDuration) {
                shortestDuration = inst.duration;
                shortestIndex = i;
            }

        }


        return readyQueue.remove(shortestIndex);
    }
}
