import java.util.concurrent.Callable;
/*From https://www.geeksforgeeks.org/*/
public class CombSort implements Callable<String> {

    private int []arr;
    public CombSort(int [] array) {
        this.arr = array;
        }
    private int getNextGap(int gap){
            // Shrink gap by Shrink factor
            gap = (gap*10)/13;

            if (gap < 1)
                return 1;
            return gap;
    }
    @Override
    public String call() throws Exception {
        int n = arr.length;

        // initialize gap
        int gap = n;

        // Initialize swapped as true to make sure that
        // loop runs
        boolean swapped = true;

        // Keep running while gap is more than 1 and last
        // iteration caused a swap
        while (gap != 1 || swapped == true)
        {
            // Find next gap
            gap = getNextGap(gap);

            // Initialize swapped as false so that we can
            // check if swap happened or not
            swapped = false;

            // Compare all elements with current gap
            for (int i=0; i<n-gap; i++)
            {
                if (arr[i] > arr[i+gap])
                {
                    // Swap arr[i] and arr[i+gap]
                    int temp = arr[i];
                    arr[i] = arr[i+gap];
                    arr[i+gap] = temp;

                    // Set swapped
                    swapped = true;
                }
            }
        }
        return "CombSort";
    }


    }