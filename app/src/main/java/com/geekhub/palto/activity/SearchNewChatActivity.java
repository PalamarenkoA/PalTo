package com.geekhub.palto.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.geekhub.palto.R;
import com.geekhub.palto.databinding.ActivitySearchNewChatBinding;
import com.geekhub.palto.viewmodel.SearchNewChatViewModel;

public class SearchNewChatActivity extends AppCompatActivity {
    public ActivitySearchNewChatBinding binding;
    SearchNewChatViewModel model;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = DataBindingUtil.
                setContentView(this, R.layout.activity_search_new_chat);
        model = new SearchNewChatViewModel(this);
        binding.setModel(model);
    }

}
