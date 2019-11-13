/* 
Author: Victor Figueira
Date:  12/11/2019
Task: 6. Faça um programa que execute três algoritmos de ordenação para um
conjunto de valores e exiba o resultado apenas do algoritmo que finalizar
primeiro (use invokeAny ).
*/

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Exercicio_6 {
    private static int size = 100000;
    private static int [] arr = new int[size];

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        fill_array();
        Collection<Callable<String>> callable_tasks = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        callable_tasks.add(new PigeonHoleSort(arr.clone()));
        callable_tasks.add(new CombSort(arr.clone()));
        callable_tasks.add(new CycleSort(arr.clone()));
        String fastestSort = executorService.invokeAny(callable_tasks);
        System.out.printf("Fastest sorting algorithm is: %s",fastestSort);
        executorService.shutdown();
        while(!executorService.isTerminated()){
            Thread.sleep(10);
        }
    }

    private static void fill_array(){
        for (int i = 0; i < size; i++) {
            arr[i] = (int)(Math.random()*size/100);
        }
    }
}
