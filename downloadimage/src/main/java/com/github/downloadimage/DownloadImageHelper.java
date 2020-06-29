package com.github.downloadimage;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadImageHelper {
    /**********************************************************/
    private static DownloadImageHelper singleObj;
    private DownloadImageHelper() {
        handler=new Handler(Looper.getMainLooper());
        executorService= Executors.newCachedThreadPool();
    }
    public static DownloadImageHelper get(){
        if(singleObj==null){
            synchronized (DownloadImageHelper.class){
                if(singleObj==null){
                    singleObj=new DownloadImageHelper();
                }
            }
        }
        return singleObj;
    }
    /**********************************************************/
    private Handler handler;
    private ExecutorService executorService;

    public Handler getHandler() {
        if(handler==null){
            handler=new Handler(Looper.getMainLooper());
        }
        return handler;
    }

    public ExecutorService getExecutorService() {
        if(executorService==null){
            executorService= Executors.newCachedThreadPool();
        }
        return executorService;
    }
}
