public class QuickUnionUF {
  private int[] id;
  private int[] treeSize;

  public QuickUnionUF(int N) {
    id = new int[N];
    treeSize = new int[N];

    for (int i = 0; i < N; i++) {
      id[i] = i;
      treeSize[i] = 1;
    }
  }

  private int root(int i) {
    if (id[i] == i) {
      return i;
    } else {
      return root(id[i]);
    }
  }

  public boolean isConnected (int p, int q) {
    return root(p) == root(q);
  }

  public void union(int p, int q) {
    int i = root(p);
    int j = root(q);

    int sizeP = treeSize[p];
    int sizeQ = treeSize[q];

    if (sizeP >= sizeQ) {
      id[j] = id[i];
      treeSize[p] += treeSize[q];
      treeSize[q] = treeSize[p];
    } else {
      id[i] = j;
      treeSize[q] += treeSize[p];
      treeSize[p] = treeSize[q];
    }
  }

  public int find(int i) {
    int iRoot = root(i);
    int max = i;
    for (int j = 0; j < id.length; j++) {
      if (iRoot == root(j)) {
        max = j;
      }
    }
    return max;
  }
}