package com.geekhub.palto.viewmodel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.activity.FirstSettingsActivity;
import com.geekhub.palto.activity.LogInActivity;
import com.geekhub.palto.databinding.ActivityLogInBinding;
import com.geekhub.palto.servise.ChatListener;
import com.geekhub.palto.useragent.UserAgent;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

/**
 * Created by duke0808 on 25.01.16.
 */
public class LoginActivityViewModel {
    LogInActivity activity;
    private int countOfItemsInAdapter;
    private Thread demonstrator;
    ActivityLogInBinding binding;

    public LoginActivityViewModel(LogInActivity activity, final ActivityLogInBinding binding, int count) {
        this.activity = activity;
        this.binding = binding;
        SharedPreferences srefs = PreferenceManager.getDefaultSharedPreferences(activity);
        if (UserAgent.get().getToken().length() > 0) {
            Intent intent;
            if (srefs.getString("VKUserFirstSettings", "").length() > 0) {
                LogInActivity.authorized = true;
//                Intent serviseIntent = new Intent(activity, MessageListenerOLD.class);
//                serviseIntent.putExtra("ID", UserAgent.get().getUserId());
//                activity.startService(serviseIntent);
                Intent serviseIntent = new Intent(activity, ChatListener.class);
                serviseIntent.putExtra("FriendID", "0000000");
                activity.startService(serviseIntent);
                intent = new Intent(activity, ChatListActivity.class);
            } else {
                intent = new Intent(activity, FirstSettingsActivity.class);
            }
            activity.startActivity(intent);
        }
        setCountOfItemsInAdapter(count);
        binding.pager.setCurrentItem(0);
        binding.dotIndicator.setSelected(0);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int selected = ((position) % 5);
                binding.dotIndicator.setSelected(selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.pager.setOffscreenPageLimit(2);

        demonstrator = new Thread() {
            @Override
            public void run() {
                do {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    showNext(null);
                } while (true);
            }
        };
        demonstrator.start();
    }

    public void showNext(View vIew) {
        Runnable runnable = new Runnable() {
            public void run() {
                binding.dotIndicator.nextDot();
                int item = (binding.pager.getCurrentItem() + 1) % countOfItemsInAdapter;
                binding.pager.setCurrentItem(item);
            }
        };
        activity.runOnUiThread(runnable);
    }

    public void setCountOfItemsInAdapter(int countOfItemsInAdapter) {
        this.countOfItemsInAdapter = countOfItemsInAdapter;
    }

    public void auth(View view) {
        String[] myScope = new String[]{VKScope.OFFLINE, VKScope.EMAIL, VKScope.PHOTOS};
        VKSdk.login(activity, myScope);
    }
}
