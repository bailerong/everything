package com.lele.everything.Core.comment;

import com.lele.everything.Core.FileType;
import com.lele.everything.Core.Thing;

import java.io.File;
//这个函数完成了我们的文件向Thing类型的转化
public final  class FileCovertThing {
    private FileCovertThing() { }

    public static Thing convert(File file){
        Thing thing=new Thing();
        thing.setName(file.getName());
        thing.setPath(file.getAbsolutePath());//绝对路径
        thing.setDepth(computerFilePath(file.getAbsolutePath()));
        thing.setFileType(computerFileType(file));
        return thing;
    }
    public static Integer computerFilePath(String path){
        Integer depth=0;
        //这是我们的文件路径深度如何算取，我们按照文件路径分隔符，有几个文件路径分隔符，我们的
        //文件就有多深。
       for(char c:path.toCharArray()){
           if(c==File.separatorChar){
               depth+=1;
           }
       }
        return depth;
    }
    public static FileType computerFileType(File file){
        if(file.isAbsolute()){
            return FileType.OTHER;
        }
        String fileName=file.getName();
        int index=fileName.lastIndexOf(".");
        if(index!=-1&&index<fileName.length()-1){
            String extend=file.getName().substring(index+1);
            return FileType.lookup(extend);
        }else{
            return FileType.OTHER;
        }
    }

    public static void main(String[] args) {
        File file=new File("D:\\java课件");
        Thing thing=FileCovertThing.convert(file);
        System.out.println(thing);
    }
}
