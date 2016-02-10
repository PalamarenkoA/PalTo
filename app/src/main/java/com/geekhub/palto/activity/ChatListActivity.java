package com.geekhub.palto.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geekhub.palto.R;
import com.geekhub.palto.databinding.ActivityChatListBinding;
import com.geekhub.palto.viewmodel.ChatListViewModel;

public class ChatListActivity extends AppCompatActivity {
    public ActivityChatListBinding binding;
    ChatListViewModel model;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = DataBindingUtil.
                setContentView(this, R.layout.activity_chat_list);
        model = new ChatListViewModel(this);
        binding.setModel(model);
    }
}
