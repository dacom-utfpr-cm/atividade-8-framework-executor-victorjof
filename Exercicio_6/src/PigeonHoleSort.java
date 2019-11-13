import java.util.Arrays;
import java.util.concurrent.Callable;
/*From https://www.geeksforgeeks.org/*/


public class PigeonHoleSort implements Callable<String> {
    private int[] arr;

    public PigeonHoleSort(int[] array) {
        this.arr = array;
    }

    @Override
    public String call() throws Exception {
        int n = arr.length;
        int min = arr[0];
        int max = arr[0];
        int range, i, j, index;

        for(int a=0; a<n; a++)
        {
            if(arr[a] > max)
                max = arr[a];
            if(arr[a] < min)
                min = arr[a];
        }

        range = max - min + 1;
        int[] phole = new int[range];
        Arrays.fill(phole, 0);

        for(i = 0; i<n; i++)
            phole[arr[i] - min]++;


        index = 0;

        for(j = 0; j<range; j++)
            while(phole[j]-->0)
                arr[index++]=j+min;
        return "PigeonHoleSort";
    }
}
