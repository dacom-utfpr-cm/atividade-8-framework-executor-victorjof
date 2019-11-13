/* 
Author: Victor Figueira
Date:  12/11/2019
Task: 2. Faça um programa que calcule a soma dos elementos de uma matriz
MxN. Divida o programa em tarefas que somam as linhas. O programa
deve possibilitar especificar o número de threads para resolver o problema.
*/
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Exercicio_2 {
    private static int num_threads = Runtime.getRuntime().availableProcessors();
    private static int num_tasks = num_threads;
    private static int num_elements = 2;
    private static int[][] matrix = new int[num_elements][num_elements+1];
    private static int interval_size =  (num_elements > num_tasks) ? (num_elements / num_tasks) : 1;


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Collection<Callable<Integer>> callable_tasks = new ArrayList<>();
        fill_matrix();
        for (int i = 0; i <matrix.length ; i++) {
            callable_tasks.add(
                    new SumRow(matrix,i)//Each task sums the values of a row
            );
        }

        ExecutorService executorService = Executors.newFixedThreadPool(num_threads);
        List<Future<Integer>> future_tasks_list = executorService.invokeAll(callable_tasks);//list with pending responses from  asynchronous tasks(SumRow)
        System.out.printf("Matrix sum is: %d, it should be %d:%n",sumRows(future_tasks_list),(long)(((num_elements*num_elements)*(matrix[0][0]+matrix[num_elements-1][num_elements-1])/2)));//Gauss Sum


        executorService.shutdown();

        while(!executorService.isTerminated()){
            Thread.sleep(10);
        }

    }

    private static int sumRows(List<Future<Integer>> future_tasks_list) throws ExecutionException, InterruptedException {
        int sum = 0;
        for (Future<Integer> future : future_tasks_list) {
            sum += future.get();//awaits largest element from current task/interval -> tasks tend to finish at the same time, with a lite delay from the last one(get_interval_end()).
        }
        return sum;
    }

    private static void fill_matrix(){
        int count = 1;
        for (int i = 0; i <num_elements ; i++) {
            for (int j = 0; j < num_elements ; j++) {
                matrix[i][j]= count;
                count+=1;//Total sum is a Gauss sum
            }
        }
    }


}
