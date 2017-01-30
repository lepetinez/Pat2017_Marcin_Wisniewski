package com.androidbegin.jsonparsetutorial;

import android.content.Context;

import java.io.File;

class FileCache {
    private static final String CACHE_DIRECTORY = "JsonParseTutorialCache";
    private final File cacheDir;

    FileCache(Context context) {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    CACHE_DIRECTORY);
        else
            cacheDir = context.getCacheDir();
    }

    File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        return new File(cacheDir, filename);
    }

}