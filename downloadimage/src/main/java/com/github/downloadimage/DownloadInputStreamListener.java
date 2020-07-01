package com.github.downloadimage;

import java.io.InputStream;

public abstract class DownloadInputStreamListener implements DownloadListener<InputStream> {
    public abstract void onSuccessInIO(InputStream inputStream);
    @Override
    public void onSuccess(InputStream inputStream) {
        onSuccessInIO(inputStream);
    }
    @Override
    public abstract void onError();
}