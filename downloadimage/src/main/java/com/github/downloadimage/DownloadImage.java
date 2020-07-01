package com.github.downloadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImage {
    public static void getImageFromUrl(final String imageUrl, final DownloadInputStreamListener downloadListener) {
        downloadImageFromUrl(imageUrl, downloadListener);
    }

    public static void getImageFromUrl(final String imageUrl, final DownloadImageListener downloadListener) {
        downloadImageFromUrl(imageUrl, downloadListener);
    }

    private static void downloadImageFromUrl(final String imageUrl, final DownloadListener downloadListener) {
        if (downloadListener == null) {
            return;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            error(downloadListener);
            return;
        }
        DownloadImageHelper.get().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(30000);
                    httpURLConnection.setReadTimeout(30000);
                    httpURLConnection.connect();
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                        if (downloadListener instanceof DownloadInputStreamListener) {
                            success(downloadListener, inputStream);
                            closeInputStream(inputStream);
                        } else {
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            success(downloadListener, bitmap);
                        }
                        return;
                    }
                    error(downloadListener);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    error(downloadListener);
                }
            }
        });
    }

    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void error(final DownloadListener downloadListener) {
        DownloadImageHelper.get().getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadListener != null) {
                    downloadListener.onError();
                }
            }
        });
    }

    private static void success(final DownloadListener downloadListener, final InputStream inputStream) {
        if (downloadListener != null) {
            downloadListener.onSuccess(inputStream);
        }
    }

    private static void success(final DownloadListener downloadListener, final Bitmap bitmap) {
        DownloadImageHelper.get().getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (downloadListener != null) {
                    downloadListener.onSuccess(bitmap);
                }
            }
        });
    }
}
