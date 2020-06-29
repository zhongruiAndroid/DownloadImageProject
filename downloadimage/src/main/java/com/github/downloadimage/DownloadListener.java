package com.github.downloadimage;

interface DownloadListener<T> {
    void onSuccess(T obj);
    void onError();
}
