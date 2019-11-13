/* 
Author: Victor Figueira
Date:  12/11/2019
Task: 3. Faça um programa concorrente para multiplicar duas matrizes.
*/
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Exercicio_3 {
    private static int M = 3;
    private static int N =  3;

    private static int[][] A = new int[M][N];
    private static int[][] B = new int[M][N];
    private static int[][] result = new int[M][N];//Matrix with multiplication result

    public static void main(String[] args) throws InterruptedException {
        fill_matrix(A,false);
        fill_matrix(B,true);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < M ; i++) {
            for (int j = 0; j < N ; j++) {
                Runnable worker = new MultiplyLines(i,j,A,B,result);//each worker calculates the result[i][j]
                executorService.execute(worker);
            }
        }
       showMatrix(A);
        System.out.println("*");
       showMatrix(B);
        System.out.println("=");
        showMatrix(result); //A * I =  A -> A * B =A


        executorService.shutdown();
        while(!executorService.isTerminated()){
            Thread.sleep(10);
        }
    }


    private static void fill_matrix(int [][] matrix,boolean isIdentity){

        if(isIdentity) {
            for (int i = 0; i < M ; i++) {
                for (int j = 0; j < N; j++) {
                    if(i==j){
                        matrix[i][j]  = 1;
                    }
                    else{
                        matrix[i][j] = 0 ;
                    }
                }
            }
        }else{
            int count = 1;
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    matrix[i][j] = count;
                    count += 1;
                }
            }

        }
    }

    private static void  showMatrix(int [][] matrix){
        String aux = "";
        for (int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.printf("%n");
        }
    }
}
