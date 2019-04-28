package com.lele.everything.Core.index;

import com.lele.everything.Core.FileInterceptor.FileInterceptor;
import com.lele.everything.Core.FileInterceptor.impl.FileIndexInterceptor;
import com.lele.everything.Core.FileInterceptor.impl.FilePrintInterceptor;
import com.lele.everything.Core.Thing;
import com.lele.everything.Core.dao.DataSourceFactory;
import com.lele.everything.Core.dao.FileIndexDao;
import com.lele.everything.Core.dao.FileIndexDaoImpl.FileIndexDaoImpl;
import com.lele.everything.Core.index.impl.FileIndexImpl;

import javax.sql.DataSource;

public interface Fileindex {
    /*将指定path路径下的所有目录和文件以及子目录和文件递归扫描*/
     void index(String path);

    void interceptor(FileInterceptor fileInterceptor);

    public static void main(String[] args) {
        Fileindex fileindex=new FileIndexImpl();
        //第一个，打印输出拦截器
        fileindex.interceptor(new FilePrintInterceptor());
        //索引文件到数据库的拦截器
        DataSource dataSource= DataSourceFactory.dataSource();
        FileIndexDao fileIndexDao=new FileIndexDaoImpl(dataSource);
        fileindex.interceptor(new FileIndexInterceptor(fileIndexDao));
        fileindex.index("D");
    }
}
