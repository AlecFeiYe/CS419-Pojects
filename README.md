# CS419-Pojects
## Development log and Blueprint:
I have a question about if a process was allocated memory. So it keeps there util it finished? Or only when it is running it has the memory space.

My answer for this question is: a memory space would be released only when the process is finished.

Maybe it is not right. 

- [x] I still have trouble to decide where and when to check the waitForMemoryQueue
- [x] Watch out! The calculating of average time isn't right now! Because there are some process wait in waitForMemoryQueue.
- [x] We still need to track who go to the waitqueue to make sure everything works well.

10/2/2026:

My previous code was written before Project 3 was officially posted on canvas. 
Now there are some new tasks.

- [x] MemoryManagement (interface): declares common methods that are used by both
  - allocation schemes, including:
  - allocate (Process p);
  - release (Process p);
  - etc.
- [x] ContiguousAllocation (class): implements MemoryManagement
- [x] PagedAllocation (class): implements MemoryManagement
- [ ] When a process arrives, before it can be admitted, the kernel must check if the
  process fits in memory. If not, the process must be placed in the memoryQueue (its
  state remains “NEW”)
- [ ] When a process terminates and its memory is released and potentially coalesced,
  the kernel must scan the memoryQueue to see if one or more processes on the
  queue can now be admitted.
- [x] A process now has three additional fields:
  - int size (this is needed by both allocation schemes)
  - int baseAddress (this is only needed by contiguous allocation)
  - int[] pageTable (this is only needed by paged allocation)
- [ ] Watch out the process state and admit.
- [ ] 1. Each message should start with a timestamp [Tick X]
- [ ] 2. Print a message whenever a process arrives (indicating its memory size), and whether the process is admitted immediately:
- [ ] If the process can be admitted, for contiguous allocation, print out the starting memory address and allocated size; for paged allocation, print out the page table.
- [ ] If the process cannot be admitted and is placed on the memory queue, print a message indicating the reason: for contiguous allocation, print out all free block sizes (should all be smaller than what the process needs) and the starting address of each free block; for paged allocation, print out the total number of free frames.
- [ ] 3. Print a message whenever a process terminates and releases its memory:
- [ ] For contiguous allocation only, if coalescing happens due to this event, print out the free blocks before and after the coalescing (starting address and size of each free block)
- [ ] For both allocation schemes, if one or more processes waiting on the memory queue can now be admitted, print a message indicating so; additionally, for contiguous allocation, print out the starting memory address and allocated size; for paged allocation, print out the page table.
- [ ] 4. Track ready queue waiting time (time a process spends waiting in the ready queue, which is influenced by the CPU scheduling algorithm) and memory queue waiting time (time a process spends waiting in the memory queue, which is influenced by the memory allocation scheme) separately, and calculate the average waiting times separately. You already did the ready queue waiting times in the previous project.
- [ ] 5. Test case (same workload trace with memory size added; total system memory: 120; page size: 10): Modify: 'OS Projects/workload.txt' is the test case professor provided.