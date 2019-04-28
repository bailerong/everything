package com.lele.everything.Core.dao;

import com.lele.everything.Core.Condition;
import com.lele.everything.Core.Thing;

import java.util.List;
//关于我们的业务层数据的增删查改
public interface FileIndexDao {
    //我们的业务层访问数据的增删查改
    public void insert(Thing thing);
    public List<Thing> search(Condition condition);
    public void delete(Thing thing);
}
