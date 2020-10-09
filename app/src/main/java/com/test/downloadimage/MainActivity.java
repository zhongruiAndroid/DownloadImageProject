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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
        Log.i("=====", "=====" + notchHeight);
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

    public byte[] toByteArray(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];

        int n = 0;
        try {
//            int read = input.read(buffer);
            Log.i("=====","a====="+(byte)'G');
            Log.i("=====","a====="+(byte)'I');
            Log.i("=====","a====="+(byte)'F');
            Log.i("=====","a====="+(byte)'g');
            Log.i("=====","a====="+(byte)'i');
            Log.i("=====","a====="+(byte)'f');
            while (true) {
                if (!(-1 != (n = input.read(buffer)))) break;
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }
    public   void test()  {
        InputStream inputStream = getResources().openRawResource(R.raw.aa);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        try {
            inputStream.read(buffer);
            Log.i("=====","====="+buffer[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getImage() {
        String url = tvUrl.getText().toString();
//        url = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2051651383,909700034&fm=26&gp=0.jpg";
//        url = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2571315283,182922750&fm=26&gp=0.jpg";
        url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602237542774&di=de6779bf5c3fadc2f4973eabc055e15f&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F18%2F03%2F07%2F2d9a152c1cdf83bb93c661cc908578ea.jpg";
        DownloadImage.getImageFromUrl(url, new DownloadInputStreamListener() {
            @Override
            public void onSuccessInIO(final InputStream obj) {
                test();
                byte[] bytes = toByteArray(obj);
                Log.i("=====","==byte==="+bytes[0]);
                Log.i("=====","==byte==="+bytes[1]);
                Log.i("=====","==byte==="+bytes[2]);
                //71 73 70
                //G I  F

                //80 78 71
                //P  N  G

                //74  70  73  70
                //J   F   I   F

                // 87 69 66 80
                // W  E  B  P
                final Bitmap bitmap = BitmapFactory.decodeStream(obj);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("=====", "=====12onSuccess" + (Looper.myLooper() == Looper.getMainLooper()));
                        iv.setImageBitmap(bitmap);
                    }
                });
                Log.i("=====", "=====11onSuccess" + (Looper.myLooper() == Looper.getMainLooper()));
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
