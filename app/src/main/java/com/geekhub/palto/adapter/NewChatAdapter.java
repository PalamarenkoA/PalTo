package com.geekhub.palto.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.tool.Binding;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Query;
import com.geekhub.palto.R;
import com.geekhub.palto.databinding.MessageLeftBinding;
import com.geekhub.palto.databinding.MessageRightBinding;
import com.geekhub.palto.object.ItemDialogList;

import java.util.ArrayList;

/**
 * Created by duke0808 on 23.02.16.
 */
public class NewChatAdapter extends FirebaseRecyclerAdapter<NewChatAdapter.ViewHolder, ItemDialogList> {
    Activity activity;

    public NewChatAdapter(Query query, Class<ItemDialogList> itemClass, Activity activity) {
        super(query, itemClass);
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 1: MessageLeftBinding bindingLeft = DataBindingUtil.inflate(activity.getLayoutInflater(),
                    R.layout.message_left, parent, false);
                return new ViewHolder(true, bindingLeft.getRoot());
            case 0:
                MessageRightBinding bindingRight = DataBindingUtil.inflate(activity.getLayoutInflater(),
                        R.layout.message_right, parent, false);
                return new ViewHolder(false, bindingRight.getRoot());
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemDialogList model = getItem(position);
        if (holder.isLeft){
            holder.bindingLeft.setModel(model);
        }else {
            holder.bindingRight.setModel(model);
        }
    }

    @Override
    protected void itemAdded(ItemDialogList item, String key, int position) {

    }

    @Override
    protected void itemChanged(ItemDialogList oldItem, ItemDialogList newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(ItemDialogList item, String key, int position) {

    }

    @Override
    protected void itemMoved(ItemDialogList item, String key, int oldPosition, int newPosition) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        boolean isLeft=false;
        MessageLeftBinding bindingLeft;
        MessageRightBinding bindingRight;

        public ViewHolder (boolean isLeft, View v){
            super(v);
            if (isLeft){
                this.isLeft=true;
                bindingLeft = DataBindingUtil.bind(v);
            } else {
                bindingRight = DataBindingUtil.bind(v);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        SharedPreferences srefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String itemId = getItem(position).id;
        String vkUserID = srefs.getString("VKUserID", "");
        if (itemId.equals(vkUserID)){
            return 1;
        } else return 0;
    }
}
