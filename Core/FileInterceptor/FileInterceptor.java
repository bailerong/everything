package com.lele.everything.Core.FileInterceptor;
//设置拦截器
import java.io.File;
@FunctionalInterface
//这个是为了方便我们写lamdon表达式
public interface FileInterceptor {
    /*这是一个文件拦截器*/
    void apply(File file);
}
