package com.lele.everything.Core.FileInterceptor.impl;

import com.lele.everything.Core.FileInterceptor.ThingInterceptor;
import com.lele.everything.Core.Thing;
import com.lele.everything.Core.dao.FileIndexDao;

import java.util.Queue;

public class ThingClearInterceptor implements ThingInterceptor,Runnable {
    private final FileIndexDao fileIndexDao;
    private final Queue<Thing> thingQueue;
    public ThingClearInterceptor(FileIndexDao fileIndexDao,Queue thingQueue){
        this.fileIndexDao=fileIndexDao;
        this.thingQueue=thingQueue;

    }
    @Override
    public void apply(Thing thing) {
      this.fileIndexDao.delete(thing);
        //调用我们的拦截
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thing thing=this.thingQueue.poll();
            if(thing!=null){
                this.apply(thing);
            }
        }
    }
}
