package minios;

import java.util.Iterator;
import java.util.List;

public class Simulator {
    private final Kernel kernel;
    private final List<Process> incomingProcesses;
    private int clock = 0;

    public Simulator(Kernel kernel, List<Process> processes) {
        this.kernel = kernel;
        this.incomingProcesses = processes;
    }

    public void run() {
        // Continue the simulation as long as:
        // 1. there are more processes to come; or
        // 2. one or more existing processes have not completed yet
        while (!incomingProcesses.isEmpty() || !kernel.isIdle()) {
            // Step 1: Check if any processes should arrive now
            Iterator<Process> it = incomingProcesses.iterator();
            while (it.hasNext()) {
                Process p = it.next();
                if (p.arrivalTime == clock) {
                    System.out.println("[Tick " + clock + "] Process " + p.pid + " arrives.");
                    kernel.admitProcess(p);
                    it.remove();
                }
            }

            // Step 2: perform tasks that need to happen during this clock tick
            kernel.onClockTick(clock);

            // Step 3: Advance the simulation clock
            clock++;
        }

    }

    public static void main(String[] args) throws Exception {
       
        String filePath = "OS Projects/workload.txt";

        // 1. FCFS 
        System.out.println("  Running FCFS  ");
        List<Process> workloadFCFS = TraceParser.parseWorkload(filePath);
        SchedulingAlgo algoFCFS = new FCFS();
        Kernel kernelFCFS = new Kernel(algoFCFS);
        Simulator simFCFS = new Simulator(kernelFCFS, workloadFCFS);
        simFCFS.run();

        // 2. SJF 
        System.out.println("\n  Running SJF  ");
        List<Process> workloadSJF = TraceParser.parseWorkload(filePath);
        SchedulingAlgo algoSJF = new SJF();
        Kernel kernelSJF = new Kernel(algoSJF);
        Simulator simSJF = new Simulator(kernelSJF, workloadSJF);
        simSJF.run();

        // 3. RR 
        System.out.println("\n  Running RR  ");
        List<Process> workloadRR = TraceParser.parseWorkload(filePath);
        SchedulingAlgo algoRR = new RR();
        Kernel kernelRR = new Kernel(algoRR);
        Simulator simRR = new Simulator(kernelRR, workloadRR);
        simRR.run();
    }
