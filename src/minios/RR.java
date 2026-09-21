package minios;
import java.util.List;

public class RR implements SchedulingAlgo {

    @Override
    public void addProcess(List<Process> readyQueue, Process p) {
        // In RR new processes are added to the end of the ready queue
        readyQueue.add(p);
    }

    @Override
    public Process selectNextProcess(List<Process> readyQueue) {
        if (readyQueue.isEmpty()) {
            return null;
        }

        //Get the process at the front of the ready queue
        return readyQueue.remove(0);
    }
}
