package com.test.downloadimage;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.downloadimage.DownloadImage;
import com.github.downloadimage.DownloadImageHelper;
import com.github.downloadimage.DownloadImageListener;
import com.github.downloadimage.DownloadInputStreamListener;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    private Button bt;
    private ImageView iv;
    private TextView tvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        bt = findViewById(R.id.bt);
        tvUrl = findViewById(R.id.tvUrl);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
//                getImageBitmap();
            }
        });
        int notchHeight = getNotchHeight(this);
        Log.i("=====","====="+notchHeight);
        iv = findViewById(R.id.iv);
    }
    public static int getNotchHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("notch_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
    private void getImage() {
        String url = tvUrl.getText().toString();
        DownloadImage.getImageFromUrl(url, new DownloadInputStreamListener() {
            @Override
            public void onSuccessInIO(final InputStream obj) {
                final Bitmap bitmap = BitmapFactory.decodeStream(obj);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("=====", "=====12onSuccess"+(Looper.myLooper()==Looper.getMainLooper()));
                        iv.setImageBitmap(bitmap);
                    }
                });
                Log.i("=====", "=====11onSuccess"+(Looper.myLooper()==Looper.getMainLooper()));
            }

            @Override
            public void onError() {
                Log.i("=====", "=====onError");
            }
        });
    }

    private void getImageBitmap() {
        String url = tvUrl.getText().toString();
        DownloadImage.getImageFromUrl(url, new DownloadImageListener() {
            @Override
            public void onSuccess(Bitmap obj) {
                iv.setImageBitmap(obj);
                Log.i("=====", "=====onSuccess");
            }

            @Override
            public void onError() {
                Log.i("=====", "=====onError");
            }
        });
    }
}
