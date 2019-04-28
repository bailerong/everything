package com.lele.everything.Core.FileInterceptor.impl;

import com.lele.everything.Core.FileInterceptor.FileInterceptor;
import com.lele.everything.Core.Thing;
import com.lele.everything.Core.comment.FileCovertThing;
import com.lele.everything.Core.dao.FileIndexDao;

import java.io.File;

public class FileIndexInterceptor implements FileInterceptor {
    private final FileIndexDao fileIndexDao;
    public FileIndexInterceptor(FileIndexDao fileIndexDao){
        this.fileIndexDao=fileIndexDao;
    }
    //打印
    //转换、写入（Thing）
    @Override
    public void apply(File file) {
        Thing thing= FileCovertThing.convert(file);
        fileIndexDao.insert(thing);
    }
}
