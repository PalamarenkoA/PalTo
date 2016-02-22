package com.geekhub.palto.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Query;
import com.geekhub.palto.R;
import com.geekhub.palto.databinding.DialogListItemBinding;
import com.geekhub.palto.databinding.MessageLeftBinding;
import com.geekhub.palto.databinding.MessageRightBinding;
import com.geekhub.palto.useragent.UserAgent;
import com.geekhub.palto.object.ItemDialogList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by andrey on 11.02.16.
 */
public class ChatListAdapter extends FirebaseListAdapter<ItemDialogList> {
    Activity activity;
    SharedPreferences srefs;

    public ChatListAdapter(Query ref, Activity activity, int layout) {
        super(ref, ItemDialogList.class, layout, activity);
        this.activity=activity;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        if(srefs.getString("VKUserID","").equals(getList().get(i).getId())){
            MessageLeftBinding binding = DataBindingUtil.inflate(activity.getLayoutInflater(),
                    R.layout.message_left, viewGroup, false);
            return super.getView(i, binding.getRoot(), viewGroup);

        }else{
            MessageRightBinding binding = DataBindingUtil.inflate(activity.getLayoutInflater(),
                    R.layout.message_right, viewGroup, false);
            return super.getView(i, binding.getRoot(), viewGroup);
        }




    }

    @Override
    protected void populateView(View v, ItemDialogList model) {
        boolean isYoursMsg = UserAgent.get().getUserId().equals(model.id);
        if (isYoursMsg) {
            MessageLeftBinding binding = DataBindingUtil.getBinding(v);
            binding.setModel(model);
        }else{
            MessageRightBinding binding = DataBindingUtil.getBinding(v);
            binding.setModel(model);
        }
    }
}
