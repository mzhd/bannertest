package com.example.administrator.bannertest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BannerItemFragment extends Fragment {
    Uri uri;
int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public BannerItemFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // System.out.println(position+":onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // System.out.println(position+":OnCreateView");
        View rootView = inflater.inflate(R.layout.fragment_banner_item, container, false);
        ImageView img_item = rootView.findViewById(R.id.img_item);
        Glide.with(getContext()).load(uri).into(img_item);

        img_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((MainActivity)getActivity()).toPause();
                return false;
            }
        });
        img_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toStop();

                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("Uri",uri);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // System.out.println(position+":onDestroyView");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // System.out.println(position+":onDestroy");
    }
}
