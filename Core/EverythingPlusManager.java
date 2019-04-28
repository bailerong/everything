package com.lele.everything.Core;

import com.lele.everything.Core.FileInterceptor.impl.FileIndexInterceptor;
import com.lele.everything.Core.FileInterceptor.impl.FilePrintInterceptor;
import com.lele.everything.Core.dao.DataSourceFactory;
import com.lele.everything.Core.dao.FileIndexDao;
import com.lele.everything.Core.dao.FileIndexDaoImpl.FileIndexDaoImpl;
import com.lele.everything.Core.index.Fileindex;
import com.lele.everything.Core.index.impl.FileIndexImpl;
import com.lele.everything.Core.search.FileSearch;
import com.lele.everything.Core.search.impl.FileSearchimpl;
import com.lele.everything.config.EverythingplusConfig;

import javax.sql.DataSource;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
//各种接口的调用，如何实现
public final class EverythingPlusManager {
    private FileSearch fileSearch;
    private Fileindex  fileindex;
    //先给定一个声明
    //线程池
    private ExecutorService executorService;
    private static volatile EverythingPlusManager manager;
    private EverythingPlusManager(){
        this.initComponent();
    }
    //检查数据库
   private void checkDatabase(){
        String workdir=System.getProperty("user.dir");
        String fileName=workdir+ File.separator+".mv.db";
        File dbfile=new File(fileName);
        if(dbfile.isFile()&&!dbfile.exists()){
            DataSourceFactory.initDatabase();
        }
   }
    private void initComponent(){
        //数据源对象
        DataSource dataSource=DataSourceFactory.dataSource();
        //初始化数据库，

        //业务层的对象
        FileIndexDao fileIndexDao=new FileIndexDaoImpl(dataSource);
        this.fileSearch=new FileSearchimpl(fileIndexDao);
        this.fileindex=new FileIndexImpl();
        this.fileindex.interceptor(new FilePrintInterceptor());
        this.fileindex.interceptor(new FileIndexInterceptor(fileIndexDao));
    }
    public EverythingPlusManager (FileSearch fileSearch,Fileindex fileindex){
        this.fileSearch=fileSearch;
        this.fileindex=fileindex;
    }
    //统一调度器
    //单例模式
    public static EverythingPlusManager getInstance(){
        if(manager==null){
            synchronized (EverythingPlusManager.class){
                if(manager==null){
                    manager=new EverythingPlusManager();
                }
            }
        }
        return manager;
    }
    public List<Thing> search(Condition condition){
        //NOTICE
        return this.fileSearch.search(condition);
    }
    public void buildIndex(){
        Set<String> directories= EverythingplusConfig.getInstance().getHandlerPath().getIncludePath();
        if(this.executorService==null){
            this.executorService= Executors.newFixedThreadPool(directories.size(), new ThreadFactory() {
              private final AtomicInteger threadId=new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread=new Thread(r);
                    thread.setName("Thread-scan"+threadId.getAndIncrement());

                    return null;
                }
            });
        }
       final CountDownLatch countDownLatch=new CountDownLatch(directories.size());
        //这一个线程执行完成之后，调用该函数，我们的线程减一

        for(String path:directories){
           this.executorService.submit(new Runnable() {
               @Override
               public void run() {
                  EverythingPlusManager.this.fileindex.index(path);
                  //当前任务执行完成，值-1
                   countDownLatch.countDown();
               }
           });
        }
        //阻塞：直到任务完成，值0；
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
