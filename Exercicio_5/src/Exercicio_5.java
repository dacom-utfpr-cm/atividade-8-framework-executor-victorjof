/* 
Author: Victor Figueira
Date:  12/11/2019
Task: 5. Faça um programa que possibilite agendar uma tarefa para ser executada
em um horário especı́fico.
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.*;

public class Exercicio_5 {
    private static Runnable task = () -> System.out.println("[TASK]time now is: " + (new Date().getHours() - 1) + ":" + new Date().getMinutes() + ":" + (new Date().getSeconds()));


    public static void main(String[] args) throws InterruptedException, ParseException, ExecutionException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter desired time. Format HH:MM:SS(12:00:00)");
        String time = scanner.nextLine();


        long seconds_to_wait = get_delay(time);


        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

        executor.schedule(task, seconds_to_wait, TimeUnit.SECONDS);
        Thread.sleep(seconds_to_wait*1000);//awaits task first time execution
        executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);//after task is completes, start doing it daily. Delay = 1 -> already did it today

        }
    private static long get_delay(String  time) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("kk:mm:ss");


        Date schedule_time = f.parse(time);
        Date current_time = f.parse((new Date().getHours()-1)+":"+(new Date().getMinutes()+":"+(new Date().getSeconds())));

        fix_date(schedule_time);
        fix_date(current_time);

        long diff = schedule_time.getTime() - current_time.getTime();
        long seconds_to_wait  = TimeUnit.MILLISECONDS.toSeconds(diff);

        if(seconds_to_wait   < 0 ){
            System.out.println("Task scheduled to next day");
            seconds_to_wait  +=(24*60);//next day
        }else{
            System.out.println("Seconds to wait: "+seconds_to_wait);
        }

        return seconds_to_wait;
    }

    private static void fix_date(Date time){
        /*prevents errors due to time changes*/
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(0,0,0);//Year month day
        time = cal.getTime();
    }
}
