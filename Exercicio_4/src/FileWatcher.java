import java.text.SimpleDateFormat;
import java.io.*;

public class FileWatcher implements Runnable {
    private long last_modified;
    private File file;

    public FileWatcher(String file_name) {
        this.file = new File(file_name);
        this.last_modified = file.lastModified();
    }

    public final void run() {
        long new_last_modified = file.lastModified();

        if(new_last_modified != last_modified){
            last_modified = new_last_modified;
            register();
        }

    }

    private void register(){
        /* Registers in log.txt new changes (file,time)*/
        long GMT_br = this.last_modified - (3600*1000);//dirty fix -> adjust -1 hour
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String status_msg = "File " +  file.getName()+ " was modified at "+sdf.format(GMT_br);
        System.out.println(status_msg);

        //buffered writer from: https://stackoverflow.com/a/1625263
        try(FileWriter fw = new FileWriter("log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(status_msg);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}