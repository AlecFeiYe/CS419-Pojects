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

- [ ] MemoryManagement (interface): declares common methods that are used by both
  - allocation schemes, including:
  - allocate (Process p);
  - release (Process p);
  - etc.
- [ ] ContiguousAllocation (class): implements MemoryManagement
- [ ] PagedAllocation (class): implements MemoryManagement