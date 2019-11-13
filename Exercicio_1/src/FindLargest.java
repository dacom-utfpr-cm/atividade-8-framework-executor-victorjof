import java.util.concurrent.Callable;

public class FindLargest implements Callable<Integer> {
    private int[] list;
    private int start_index;
    private int end_index;
    public FindLargest(int start_index, int end_index, int[] list) {
        this.list = list;
        this.start_index = start_index;
        this.end_index = end_index;

    }

    @Override
    public Integer call() throws Exception {
        int  largest = this.list[ this.start_index];

        for (int i = this.start_index+1; i <  this.end_index; i++) {
                if(this.list[i] > largest){
                    largest = this.list[i];
                }
        }

        return largest;
    }



}
