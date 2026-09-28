package minios;

public class MemoryManager {
    private final int memorySpace;
    private final boolean memory[];

    public MemoryManager(int memorySpace) {
        this.memorySpace = memorySpace;
        memory = new boolean[memorySpace];
    }

    public boolean isEnoughMemorySpace(Process process) {
        int requiredMemorySpace = process.getRequiredMemorySpace();
        for (int i = 0; i < this.memorySpace; i++) {
            int freeMemoryFragment = 0;
            if (!this.memory[i]) {
                for (int j = i + 1; j < this.memorySpace; j++) {
                    if (!this.memory[j]) {
                        freeMemoryFragment++;
                        if (freeMemoryFragment >= requiredMemorySpace) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


}
