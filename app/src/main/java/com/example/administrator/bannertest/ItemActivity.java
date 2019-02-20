package com.example.administrator.bannertest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ItemActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Uri uri=getIntent().getExtras().getParcelable("Uri");
        Glide.with(this).load(uri).into((ImageView) findViewById(R.id.img));
    }
}
