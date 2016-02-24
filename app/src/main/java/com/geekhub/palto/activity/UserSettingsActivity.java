package com.geekhub.palto.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;

import com.geekhub.palto.R;
import com.geekhub.palto.databinding.ActivityUserSettingsBinding;
import com.geekhub.palto.viewmodel.UserSettingsViewModel;

public class UserSettingsActivity extends Activity {
    public ActivityUserSettingsBinding binding;
    UserSettingsViewModel model;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = DataBindingUtil.
                setContentView(this, R.layout.activity_user_settings);
        model = new UserSettingsViewModel(this);
        binding.setModel(model);
    }

}
