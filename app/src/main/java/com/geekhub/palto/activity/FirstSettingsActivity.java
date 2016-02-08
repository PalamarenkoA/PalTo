package com.geekhub.palto.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.geekhub.palto.R;
import com.geekhub.palto.databinding.ActivityFirstSettingsBinding;
import com.geekhub.palto.viewmodel.FirstSettingsViewModel;
import com.squareup.picasso.Picasso;
import com.vk.sdk.util.VKUtil;

public class FirstSettingsActivity extends AppCompatActivity {
    FirstSettingsViewModel model;
    public ActivityFirstSettingsBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = DataBindingUtil.
                setContentView(this, R.layout.activity_first_settings);
        model = new FirstSettingsViewModel(this);
        binding.setModel(model);



    }
}
