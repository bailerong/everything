package com.lele.everything.Core.FileInterceptor.impl;

import com.lele.everything.Core.FileInterceptor.FileInterceptor;

import java.io.File;

public class FilePrintInterceptor implements FileInterceptor {
    @Override
    public void apply(File file) {
        System.out.println(file.getAbsolutePath());
    }
}
