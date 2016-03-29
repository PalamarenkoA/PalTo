package com.geekhub.palto.customviews.splash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by duke0808 on 25.03.16.
 */
public class MySplashScreenPagerAdapter extends FragmentPagerAdapter {
    MySplashScreenFactory factory;

    public MySplashScreenPagerAdapter(FragmentManager fm) {
        super(fm);
        factory = new MySplashScreenFactory();
        factory.initPageList();
    }

    @Override
    public Fragment getItem(int position) {
        return factory.getNext();
    }

    @Override
    public int getCount() {
        return factory.getCount();
    }
}
