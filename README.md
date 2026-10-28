# CS419 - Project

## Development log and Blueprint:

### CS 419 Project: Address Translation for MiniOS Overview:

- [ ] Processes now store/load data to/from memory using virtual addresses, which need to be translated to physical addresses. 
- [ ] You will implement address translation for both memory allocation schemes you implemented in the previous project. 
- [x] The grading of this project is based on FCFS CPU scheduling. 

**Modification to Trace Format and Instruction OpType:**
- [ ] COMPUTE n: an instruction that only utilizes CPU and not memory (in practice, this would be an arithmetic or logic instruction) 
- [x] STORE addr value: an instruction that stores the given value at the given virtual address. This instruction is always 1 tick.
- [x] LOAD addr: an instruction that loads the value stored at the given virtual address. This instruction is always 1 tick.
- [x] In the trace, a LOAD always come after a STORE to the same virtual address. 

**Modification to Process Class:**
- We provide the updated Process class which now contains a new method currentCPUBurstLength() for returning the current CPU burst length, needed for SJF
scheduling. (However, you’re not required to retro-Rit your CPU scheduling algorithm implementation; the grading of this
project will only be based on FCFS.) 

**Modifications to Kernel Class:**
- [ ] Kernel now needs to specifically handle the STORE and LOAD instructions because they require memory access 

**Modification to MemoryManagement Interface:** 
- [x] Now needs a translate(Process p, int addr) method, that translates the given virtual address to the corresponding physical address for Process p.

**Modification to MemoryManagement Implementations:** 
- [x] Now need to represent the physical memory using an int array, where each array element represents one memory unit. Array indices represents physical addresses.
- [x] For a STORE instruction, store the value in the array. 
- [x] For a LOAD instruction, read the value from the array. (Alternatively, this could be done in the Kernel class.) 
- [ ] Need to implement the translate(Process p, int addr) method. 

Note that a given virtual address could be invalid (i.e., outside the process’ size). 
- [x] Your implementation needs to handle such cases by printing a warning message 
- [ ] and aborting the STORE or LOAD instruction. 
- [ ] An invalid instruction still consumes 1 tick. 
- [ ] After the invalid instruction is aborted, the process is immediately terminated (within the same tick). 

**Required Output:** 
- [ ] Each message should start with a timestamp [Tick X]
- [ ] Each time a virtual address is translated, print out both the virtual and physical addresses.
- [ ] Each time a LOAD instruction executes, print out the value loaded
- [ ] Each time an invalid address is detected when executing a STORE or LOAD instruction, print out a warning message including the process ID and the instruction details.

- [ ] In the file 'TraceParser': I don't know why for case COMPUTE we don't need to do anything.