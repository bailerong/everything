package com.lele.everything.Core;

import lombok.Data;

@Data
public class Condition {
    public void setName(String name) {
        this.name = name;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOrderByAsc(Boolean orderByAsc) {
        this.orderByAsc = orderByAsc;
    }

    public String getName() {
        return name;
    }

    public String getFileType() {
        return fileType;
    }

    public Integer getLimit() {
        return limit;
    }

    public Boolean getOrderByAsc() {
        return orderByAsc;
    }


    @Override
    public String toString() {
        return "Condition{" +
                "name='" + name + '\'' +
                ", fileType='" + fileType + '\'' +
                ", limit=" + limit +
                ", orderByAsc=" + orderByAsc +
                '}';
    }

    private String name="123";
    private String fileType;
    private Integer limit=10;
    /*
    *检索的文件信息depth排序规则
    * 默认是true
    * */
    private Boolean orderByAsc=true;
}
