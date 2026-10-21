# CS419 - Project
## Development log and Blueprint:

- [x] MemoryManagement (interface): declares common methods that are used by both
  - allocation schemes, including:
  - allocate (Process p);
  - release (Process p);
  - etc.
- [x] ContiguousAllocation (class): implements MemoryManagement
- [x] PagedAllocation (class): implements MemoryManagement
- [x] When a process arrives, before it can be admitted, the kernel must check if the
  process fits in memory. If not, the process must be placed in the memoryQueue (its
  state remains “NEW”)
- [x] When a process terminates and its memory is released and potentially coalesced,
  the kernel must scan the memoryQueue to see if one or more processes on the
  queue can now be admitted.
- [x] A process now has three additional fields:
  - int size (this is needed by both allocation schemes)
  - int baseAddress (this is only needed by contiguous allocation)
  - int[] pageTable (this is only needed by paged allocation)
- [ ] Watch out the process state and admit.
- [x] 1. Each message should start with a timestamp [Tick X]
- [x] 2. Print a message whenever a process arrives (indicating its memory size), **and whether the process is admitted immediately:**
- [x] If the process can be admitted, for contiguous allocation, print out the **starting memory address** and **allocated size**; for paged allocation, print out the **page table**.
- [ ] If the process cannot be admitted and is placed on the memory queue, print a message indicating the reason: for contiguous allocation, **print out all free block sizes** (should all be smaller than what the process needs) and **the starting address of each free block**; for paged allocation, **print out the total number of free frames**.
- [ ] 3. Print a message whenever **a process terminates** and releases its memory:
- [ ] For contiguous allocation only, if coalescing happens due to this event, **print out the free blocks before and after the coalescing** (starting address and size of each free block)
- [ ] **For both allocation schemes, if one or more processes waiting on the memory queue can now be admitted, print a message indicating so; additionally, for contiguous allocation, print out the starting memory address and allocated size; for paged allocation, print out the page table.**
- [x] 4. Track ready queue waiting time (time a process spends waiting in the ready queue, which is influenced by the CPU scheduling algorithm) and memory queue waiting time (time a process spends waiting in the memory queue, which is influenced by the memory allocation scheme) separately, and calculate the average waiting times separately. You already did the ready queue waiting times in the previous project.
- [x] 5. Test case (same workload trace with memory size added; total system memory: 120; page size: 10): Modify: 'OS Projects/workload.txt' is the test case professor provided.

- [ ] I think there are 2 big and total issues, one of is setting the process state appropriately, one of is showing the time tick correctly.