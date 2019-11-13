import java.util.concurrent.Callable;

public class MultiplyLines implements Runnable {
    private int[][] A;
    private int[][] B;
    private int[][] C;
    private int row_index;
    private int col_index;
    public MultiplyLines(int row_index,int col_index,int[][] A,int[][] B, int[][] C) {
        this.row_index = row_index;
        this.col_index = col_index;
        this.A = A;
        this.B = B;
        this.C = C;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < A[this.row_index].length; i++) {
            sum+= (A[row_index][i]*B[i][col_index]);
        }
        C[row_index][col_index] = sum;
    }
}

