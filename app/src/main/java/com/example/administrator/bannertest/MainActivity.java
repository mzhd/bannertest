package com.example.administrator.bannertest;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 实现功能：
 * 1、自动轮播图片
 * 2、手动轮播图片
 * 3、当用户触摸图片时停止轮播
 * 4、当用户点击图片实现跳转
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager vp_banner;

    List imgsURI;

    public static final int MESSAGE_WAHT_PLAY = 0;

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_WAHT_PLAY:
                    vp_banner.setCurrentItem(vp_banner.getCurrentItem() + 1);
                    break;
            }
        }
    }

    Handler mHandler = new MyHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_banner);

        imgsURI = new ArrayList();
        imgsURI.add(getUriFromDrawableRes(this, R.drawable.img_1));
        imgsURI.add(getUriFromDrawableRes(this, R.drawable.img_2));
        imgsURI.add(getUriFromDrawableRes(this, R.drawable.img_3));
        imgsURI.add(getUriFromDrawableRes(this, R.drawable.img_4));
        imgsURI.add(getUriFromDrawableRes(this, R.drawable.img_5));
        vp_banner = findViewById(R.id.vp_banner);
        VPBannerAdapter vpBannerAdapter = new VPBannerAdapter(getSupportFragmentManager(), imgsURI);
        vp_banner.setAdapter(vpBannerAdapter);
        vp_banner.setCurrentItem(1000);
        vp_banner.setPageMargin(10);
        DisplayMetrics outMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        int  pageMargin=200;
        layoutParams.width=outMetrics.widthPixels-pageMargin/2;
        layoutParams.gravity= Gravity.CENTER_HORIZONTAL;
        vp_banner.setLayoutParams(layoutParams);

        vp_banner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("" + event.getAction());
                toPause();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println(MotionEvent.ACTION_DOWN);
                    toPause();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    System.out.println(MotionEvent.ACTION_UP);
                    toPlay();
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        initPlay();

    }

    @Override
    protected void onStop() {
        super.onStop();
        toStop();
    }

    boolean isPlay;

    long millis = 3000;
     Boolean stop = false;

     final Object object=new Object();

    class PlayRunnable implements Runnable {

        @Override
        public void run() {
            synchronized (object) {

                while (!stop) {
                    try {
                        Thread.sleep(millis);
                        if (isPlay) {
                            mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_WAHT_PLAY));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }

    public void initPlay() {
        toPlay();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(new PlayRunnable());
    }


    public void toPlay() {
        stop = false;
        isPlay = true;
    }

    public void toPause() {
        isPlay = false;
    }

    public void toStop() {
        stop = true;
    }


    public void toReset() {
        vp_banner.setCurrentItem(0);
    }


    /**
     * 获取drawable下图片的Uri
     *
     * @param context
     * @param id
     * @return
     */
    public Uri getUriFromDrawableRes(Context context, int id) {
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getResources().getResourcePackageName(id)
                + "/" + context.getResources().getResourceTypeName(id) + "/" + context.getResources().getResourceEntryName(id);
        return Uri.parse(path);
    }

}
