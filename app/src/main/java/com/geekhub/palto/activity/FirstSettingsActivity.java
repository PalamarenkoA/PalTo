package com.geekhub.palto.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geekhub.palto.R;
import com.geekhub.palto.databinding.ActivityFirstSettingsBinding;
import com.geekhub.palto.viewmodel.FirstSettingsViewModel;

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
