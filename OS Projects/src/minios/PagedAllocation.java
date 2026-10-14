package minios;

public class PagedAllocation implements MemoryManagement {
    private int size;
    private int frameSize;
    private int numberOfFrames;
    private boolean[] framesArray;

    public PagedAllocation(int size, int frameSize) {
        this.size = size;
        this.frameSize = frameSize;
        this.numberOfFrames = size / frameSize;
        this.framesArray = new boolean[numberOfFrames];
    }

    @Override
    public boolean allocate(Process p) {
        int neededFrames = (p.getSize() + frameSize - 1) / frameSize;

        int freeFrames = 0;

        for (boolean used : framesArray) {
            if (!used) {
                freeFrames++;
            }
        }

        if (freeFrames < neededFrames) {
            return false;
        }

        p.setPageTable(neededFrames);

        int pageIndex = 0;

        for (int i = 0; i < framesArray.length && pageIndex < neededFrames; i++) {
            if (!framesArray[i]) {
                framesArray[i] = true;
                p.modifyPageTable(pageIndex, i);
                pageIndex++;
            }
        }

        return true;
    }

    @Override
    public void release(Process p) {
        int[] tempPage = p.getPageTable();
        for (int i = 0; i < tempPage.length; i++) {
            framesArray[tempPage[i]] = false;
            p.modifyPageTable(i, -1);
        }

    }

    @Override
    public String getName() {
        return "Paged Allocation";
    }
}
