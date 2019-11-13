import java.util.concurrent.Callable;

public class SumRow implements Callable<Integer> {
    private int [][]matrix;
    private int row_index;
    private int sum;
    public SumRow(int [][] matrix,int row_index) {
        this.matrix = matrix;
        this.row_index = row_index;
        sum = 0;
    }

    @Override
    public Integer call() {
        for (Integer value: matrix[row_index]) {
            sum+=value;
        }
        return  sum;
    }
}
