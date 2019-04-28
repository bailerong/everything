package com.lele.everything.Core.search.impl;

import com.lele.everything.Core.Condition;
import com.lele.everything.Core.FileInterceptor.ThingInterceptor;
import com.lele.everything.Core.FileInterceptor.impl.ThingClearInterceptor;
import com.lele.everything.Core.Thing;
import com.lele.everything.Core.dao.FileIndexDao;
import com.lele.everything.Core.search.FileSearch;

import java.io.File;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

//这是我们的业务层代码
public class FileSearchimpl implements FileSearch {
   //被final关键字修饰的对象有几种初始化方式
private final FileIndexDao fileIndexDao;
private final ThingClearInterceptor interceptor;
private final Queue<Thing> thingQueue=new ArrayBlockingQueue<Thing>(1024);

    public FileSearchimpl(FileIndexDao fileIndexDao) {
        this.fileIndexDao = fileIndexDao;
        this.interceptor=new ThingClearInterceptor(this.fileIndexDao,thingQueue);
        this.backgroundClear();
    }

    @Override
    public List<Thing> search(Condition condition) {
        //如果本地文件系统将文件删除，数据库中任然存储到索引信息
        //此时如果查询结果将存在文件系统中删除的文件，那么需要在数据库中清除掉该文件的索引信息
        List<Thing> list=this.fileIndexDao.search(condition);
        Iterator<Thing>iterator=list.iterator();
        while(iterator.hasNext()){
            Thing thing=iterator.next();
            File file=new File(thing.getPath());
            if(!file.exists()){
                //删除
                iterator.remove();
                //生产者消费者模型
               this.thingQueue.add(thing);
            }
        }
        return list;
    }
    private void backgroundClear(){
    Thread thread=new Thread(this.interceptor);
    thread.setName("Thread_Clear");
    thread.setDaemon(true);
    thread.start();
    }
    //做一个项目，我们还有我们更加主要的东西，就是将我们的Java里面的各种框架搞好，各种类的实现
}
