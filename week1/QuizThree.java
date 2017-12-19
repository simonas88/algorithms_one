public class QuizThree {
  private int[] set;

  public QuizThree(int N) {
    this.set = new int[N];

    for (int i = 0; i < N; i++) {
      set[i] = i;
    }
  }

  public void remove(int x) {
    this.set[x] = -1;
  }

  public int getSuccessor(int x) {
    int i;
    for (i = x; i < set.length; i++) {
      if (x <= set[i]) {
        return i;
      }
    }

    return i;
  }
}