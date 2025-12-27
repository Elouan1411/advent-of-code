import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    private int[] parent;
    private int[] size;
    private int numGroups;

    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        numGroups = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i; // Everyone is their own boss initially
            size[i] = 1; // Size of each group is 1
        }
    }

    public int find(int i) {
        if (parent[i] == i)
            return i;
        parent[i] = find(parent[i]);
        return parent[i];
    }

    public void union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        if (rootI != rootJ) {
            // Merge smaller group into larger group
            if (size[rootI] < size[rootJ]) {
                parent[rootI] = rootJ;
                size[rootJ] += size[rootI];
            } else {
                parent[rootJ] = rootI;
                size[rootI] += size[rootJ];
            }
            numGroups--;
        }
    }

    public boolean isRoot(int i) {
        return parent[i] == i;
    }

    public boolean isAllConnected() {
        return numGroups == 1;
    }

    // return size of each group
    public int[] getAllSize() {
        List<Integer> validSizes = new ArrayList<>();

        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == i) {
                validSizes.add(size[i]);
            }
        }

        return validSizes.stream().mapToInt(i -> i).toArray(); // convert Integer to int
    }

    public int getSize(int i) {
        return size[find(i)];
    }

}
