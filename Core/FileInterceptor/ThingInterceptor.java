package com.lele.everything.Core.FileInterceptor;

import com.lele.everything.Core.Thing;

public interface ThingInterceptor {
    //检索结果Thing的拦截器
    void apply(Thing thing);
}
