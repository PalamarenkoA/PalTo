package com.geekhub.palto.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geekhub.palto.R;
import com.geekhub.palto.databinding.ActivityUserInfoBinding;
import com.geekhub.palto.viewmodel.UserInfoViewModel;

public class UserInfoActivity extends AppCompatActivity {
   public ActivityUserInfoBinding binding;
    UserInfoViewModel model;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = DataBindingUtil.
                setContentView(this, R.layout.activity_user_info);
        model = new UserInfoViewModel(this);
        binding.setModel(model);
    }
}
