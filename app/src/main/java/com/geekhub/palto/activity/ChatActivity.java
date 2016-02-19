package com.geekhub.palto.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.geekhub.palto.R;
import com.geekhub.palto.databinding.ActivityChatBinding;
import com.geekhub.palto.viewmodel.ChatListViewModel;
import com.geekhub.palto.viewmodel.ChatViewModel;


public class ChatActivity extends AppCompatActivity {


   public ActivityChatBinding binding;
    ChatViewModel model;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = DataBindingUtil.
                setContentView(this, R.layout.activity_chat);
        model = new ChatViewModel(this);
        binding.setModel(model);



}}
