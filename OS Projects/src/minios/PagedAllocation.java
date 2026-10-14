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
    public boolean allocate(Process p) {
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
            memoryTracker();
            return true;
        }
        memoryTracker();
        return false;
    }

    @Override
    public void release(Process p) {
        int[] tempPage = p.getPageTable();
        for (int i = 0; i < tempPage.length; i++) {
            framesArray[tempPage[i]] = false;
            p.modifyPageTable(i, -1);
        }
        memoryTracker();
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
