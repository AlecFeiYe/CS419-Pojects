package minios;

import java.util.LinkedList;

public class PagedAllocation implements MemoryManagement {
    private int size;
    private int frameSize;
    private int numberOfFrames;
    private boolean[] framesArray;

    }

    @Override
    public boolean allocate(Process p)
    {
        return true;
    }

    @Override
    public void release(Process p)
    {

    }

    @Override
    public String getName() {
        return "Paged Allocation";
    }
}
