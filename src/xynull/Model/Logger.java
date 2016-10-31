package xynull.Model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by XyNull on 2016/10/11.
 */
public class Logger {
    private int maxLogSize = 100000;

    private Lock lock = new ReentrantLock();

    private String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    private List<String> Logs = new ArrayList<>();

    public Logger() {

    }

    private void print(){

    }

    public void debug(String messge, Exception e){
        lock.lock();
        if(e != null)
            Logs.add("DEBUG " + messge + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public void info(String messge, Exception e){
        if(e != null)
            Logs.add("INFO " + messge +  " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public void error(String messge, Exception e){
        if(e != null)
            Logs.add("ERROR " + messge +  " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
