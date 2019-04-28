package com.lele.everything.Core.index.impl;

import com.lele.everything.Core.FileInterceptor.FileInterceptor;
import com.lele.everything.Core.FileInterceptor.impl.FileIndexInterceptor;
import com.lele.everything.Core.FileInterceptor.impl.FilePrintInterceptor;
import com.lele.everything.Core.Thing;
import com.lele.everything.Core.dao.DataSourceFactory;
import com.lele.everything.Core.dao.FileIndexDaoImpl.FileIndexDaoImpl;
import com.lele.everything.Core.index.Fileindex;
import com.lele.everything.config.EverythingplusConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileIndexImpl implements Fileindex {


    //DAO

private EverythingplusConfig config=EverythingplusConfig.getInstance();
//设置我们的拦截器
private final LinkedList<FileInterceptor> interceptors=new LinkedList<>();

    @Override
    public void interceptor(FileInterceptor fileInterceptor) {
      this.interceptors.add(fileInterceptor);
    }
    //我们要对所有的部分完成
    @Override
    public void index(String path) {
    File file=new File(path);
    List<File> fileList=new ArrayList<>();
    if(file.isFile()){
     if(!config.getHandlerPath().getIncludePath().contains(path)){
         return;
     }else{
         fileList.add(file);
     }
 } else {//getIncludepath().contains((path))
    if (config.getHandlerPath().getIncludePath().contains(path)) {
       return;
    }else{
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                index(f.getAbsolutePath());
            }
        }
    }
        System.out.println(file.getAbsolutePath());
}
 //排除文件完成后 thing->写入
        for(FileInterceptor fileInterceptor:this.interceptors){
            fileInterceptor.apply(file);
        }
    }
    public void addFileInterceptor(FileInterceptor fileInterceptor){
        this.interceptors.add(fileInterceptor);
    }


}
