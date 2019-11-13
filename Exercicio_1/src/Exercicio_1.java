/* 
Author: Victor Figueira
Date:  12/11/2019
Task: 1. Faça um programa que localize o maior valor em um vetor. Divida o
programa em tarefas que localizam o maior valor em um segmento do
vetor. O programa deve possibilitar especificar o número de tarefas e o
número de threads para resolver o problema.
*/
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Exercicio_1 {
    private static int num_threads = Runtime.getRuntime().availableProcessors();
    private static int num_tasks = num_threads;
    private static int num_elements = 10;
    private static int[] array = new int[num_elements];
    private static int interval_size =  (num_elements > num_tasks) ? (num_elements / num_tasks) : 1;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        fill_array(array);

        Collection<Callable<Integer>> callable_tasks = new ArrayList<>();
        for (int start = 0,task = 0; task < num_tasks ; start+=interval_size,task++) {
            callable_tasks.add(
                   new FindLargest(start,get_interval_end(start,task),array)//Each task returns the largest element from the specified interval
            );

        }

        ExecutorService executorService = Executors.newFixedThreadPool(num_threads);
        List<Future<Integer>> future_tasks_list = executorService.invokeAll(callable_tasks);//list with pending responses from  asynchronous tasks(findLargest)
        System.out.printf("Max value is %d from array:%n%s%n",get_max_value(future_tasks_list),arrayToString());
        executorService.shutdown();
        while(!executorService.isTerminated()){
            Thread.sleep(10);
        }

    }

    private static int get_max_value(List<Future<Integer>> future_tasks_list) throws ExecutionException, InterruptedException {
        double largest  = Double.NEGATIVE_INFINITY;
        for (Future<Integer> future : future_tasks_list) {
            int value = future.get();//awaits largest element from current task/interval -> tasks tend to finish at the same time, with a lite delay from the last one(get_interval_end()).
            if(value > largest){
                largest = value;
            }
        }
        return (int) largest;
    }

    private static int get_interval_end(int start,int task) {
        if (task == num_tasks - 1) {//if last task/thread gets what is left -> rest of the  num_elements/num_tasks division
            return array.length - 1;  //task iterate until last element to be transformed
        } else {
            return start + interval_size;
        }
    }

    private static void fill_array(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random()*100);
        }
    }

    private static  String arrayToString(){
        String aux = "";
        aux += "[ ";

        for (Integer value : array) {
            aux += (String.valueOf(Math.round(value+0.001)));//rounding bc the combination of: numeric method + IEE754, may take a long time to converge to the precise value.
            aux += " ";
        }
        aux += "]";
        return aux;
    }
}
