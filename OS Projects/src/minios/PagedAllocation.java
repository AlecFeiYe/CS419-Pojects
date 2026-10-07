package minios;

import java.util.LinkedList;

public class PagedAllocation implements MemoryManagement {
    public PagedAllocation(int limit) {

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
