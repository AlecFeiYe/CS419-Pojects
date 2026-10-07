package minios;

import java.util.LinkedList;

public class PagedAllocation implements MemoryManagement {
    private int size;
    private int frameSize;
    private int numberOfFrames;
    private boolean[] framesArray;

    public PagedAllocation(int size, int frameSize) {
        this.size = size;
        this.frameSize = frameSize;
        this.numberOfFrames = size / frameSize;
        this.framesArray = new boolean[frameSize];
    }

    @Override
    public boolean allocate(Process p)
    {
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
