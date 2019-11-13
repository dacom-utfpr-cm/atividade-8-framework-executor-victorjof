/* 
Author: Victor Figueira
Date:  12/11/2019
Task: 4. Faça um programa que periodicamente monitore mudanças em um
conjunto de arquivos especificados. Se ocorreram mudanças, o programa
deve registrá-las em um arquivo de log.
*/
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Exercicio_4 {
    public static void main(String[] args) {
        String folder_path = "files/";
        List<String> files_name = new ArrayList<>();
        fill_names(files_name,folder_path);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("Watching: "+files_name);

        for (String file_name : files_name) {
            Runnable task = new FileWatcher(file_name);//one task = one file being monitored for changes
            scheduler.scheduleAtFixedRate(task,0,1, TimeUnit.SECONDS);//Every second checks for a new modification at the same object/task, therefore, same 'lastModified'
        }
    }

    private static void fill_names(List<String> files_name,String folder_path){
        File [] files = new File(folder_path).listFiles();
        for(File file : files){
            if(file.isFile()){
                files_name.add(folder_path+file.getName());
            }
        }
    }
}
