package com.example.administrator.bannertest;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 *
 * FragmentStatePagerAdapter:左右各缓存1个页面(共缓存3个页面)，之前的页面会被Destroy
 * FragmentPagerAdapter：左右各缓存1个页面(共缓存3个页面),之前的页面只会DestroyView
 */
public class VPBannerAdapter extends FragmentStatePagerAdapter {
    List imgsURI;
    public VPBannerAdapter(FragmentManager fm, List imgsURI) {
        super(fm);
        this.imgsURI=imgsURI;
    }

    @Override
    public Fragment getItem(int position) {
        BannerItemFragment fragment = new BannerItemFragment();
        fragment.setUri((Uri) imgsURI.get(position%imgsURI.size()));
        fragment.setPosition(position);
        return fragment;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getPageWidth(int position) {
        return 1f;
    }
}
