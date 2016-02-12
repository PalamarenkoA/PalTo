package com.geekhub.palto.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.geekhub.palto.R;
import com.geekhub.palto.databinding.DialogListItemBinding;
import com.geekhub.palto.useragent.UserAgent;
import com.geekhub.palto.viewmodel.ItemDialogList;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by andrey on 11.02.16.
 */
public class ChatListAdapter extends FirebaseListAdapter<ItemDialogList> {
    Activity activity;


    public ChatListAdapter(Query ref, Activity activity, int layout) {
        super(ref, ItemDialogList.class, layout, activity);
        this.activity=activity;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DialogListItemBinding binding = DataBindingUtil.inflate(activity.getLayoutInflater(), R.layout.dialog_list_item, viewGroup, false);

        return super.getView(i, binding.getRoot(), viewGroup);
    }

    @Override
    protected void populateView(View v, ItemDialogList model) {
        DialogListItemBinding binding = DataBindingUtil.getBinding(v);
        binding.setModel(model);

        boolean isYoursMsg = UserAgent.get().getUserId().equals(model.id);
        CircleImageView iconImage = binding.iconImage;
        if (!isYoursMsg) {
            Picasso.with(v.getContext()).load(model.getIconImage()).placeholder(R.drawable.imgpsh_fullsize).into(iconImage);

        } else {
            Picasso.with(v.getContext()).load(model.getIconImage()).placeholder(R.drawable.imgpsh_fullsize).into(iconImage);
//            CircleImageView yourIconImage = binding.yourIconImage;
//            Picasso.with(v.getContext()).load(model.getIconImage()).placeholder(R.drawable.imgpsh_fullsize).into(yourIconImage);
//            yourIconImage.setVisibility(View.VISIBLE);
        }
    }
}
