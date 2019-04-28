package com.lele.everything.Core;

import lombok.Data;

//文件索引之后的记录信息Thing表示
@Data
//getter and setter方法
public class Thing {
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Integer getDepth() {
        return depth;
    }

    public FileType getFileType() {
        return fileType;
    }

    /*
        文件名称（保留名称）
         */
    //文件名称
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    //文件路径
    private  String path;
    //文件路径深度
    private Integer depth;
    //文件类型
    private FileType fileType;

}
