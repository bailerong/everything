package com.lele.everything.Core.search;
import com.lele.everything.Core.Condition;

import com.lele.everything.Core.Thing;
//一个接口
import java.util.List;
/*我们的文件检索*/

public interface FileSearch {
    //根据condition条件进行数据库检索
    List<Thing> search(Condition condition);

}
