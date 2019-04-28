package com.lele.everything.Core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum FileType {
    //图片类型
    IMG("png","jpeg","jpg","gif"),
    //文件类型
    DOC("ppt","pptx","doc","docx","pdf"),
    //二进制类型
    BIN("exe","sh","jar","msi"),
    //压缩包格式
    ARCHIVE("zip","rar" ),
    OTHER("*");
    private Set<String> extend=new HashSet<>();
    FileType(String...extend){
        //转集合
        this.extend.addAll(Arrays.asList(extend));
    }

    public static FileType lookup(String extend){
       for(FileType fileType:FileType.values()){
           //如果你的集合里面包括，我就展示出
           if(fileType.extend.contains(extend)){
               return fileType;
           }
       }
       //如果不包括，那就返回其他
       return FileType.OTHER;
    }
    //根据文件类型名获取文件类型对象
    public static FileType lookupByName(String name){
        for(FileType fileType:FileType.values()){
            if(fileType.name().equals(name)){
                return fileType;
            }
        }
        return FileType.OTHER;
    }

}
