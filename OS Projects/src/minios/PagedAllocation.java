package minios;

public class PagedAllocation implements MemoryManagement {
    private final int size;
    private final int frameSize;
    private final int numberOfFrames;
    private boolean[] framesArray;

    public PagedAllocation(int size, int frameSize) {
        this.size = size;
        this.frameSize = frameSize;
        this.numberOfFrames = this.size / frameSize;
        this.framesArray = new boolean[numberOfFrames];
    }

    @Override
    public boolean allocate(Process p, int clock) {
        int neededFrames = (p.getSize() - 1) / frameSize + 1;
        p.setPageTable(neededFrames);
        int countOfFreeFrames = 0;
        for (boolean i : framesArray) {
            if (!i) {
                countOfFreeFrames++;
            }
        }
        int restOfNeededFrames = neededFrames;
        if (countOfFreeFrames >= neededFrames) {
            for (int i = 0; i < numberOfFrames && restOfNeededFrames > 0; i++) {
                if (!framesArray[i]) {
                    p.modifyPageTable(neededFrames - restOfNeededFrames, i);
                    framesArray[i] = true;
                    restOfNeededFrames--;
                }
            }

            System.out.print("[Tick " + clock + "] Paged Allocation. Page Table: [");
            for (int i : p.getPageTable()) {
                System.out.print(" " + i);
            }
            System.out.println("]");

//            memoryTracker();
            return true;
        }
//        memoryTracker();
        return false;
    }

    @Override
    public void release(Process p, int clock) {
        int[] tempPage = p.getPageTable();
        for (int i = 0; i < tempPage.length; i++) {
            framesArray[tempPage[i]] = false;
            p.modifyPageTable(i, -1);
        }
        System.out.println("[Tick " + clock + "] The memory of Process " + p.pid + " has been released.");
//        memoryTracker();
    }

    @Override
    public String getName() {
        return "Paged Allocation";
    }

    public void memoryTracker() {
        for (boolean i : this.framesArray) {
            if (i) {
                System.out.print("1 ");
            } else {
                System.out.print("0 ");
            }
        }
        System.out.println();
    }
}
