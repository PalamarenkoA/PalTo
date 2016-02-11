package com.geekhub.palto.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.geekhub.palto.R;
import com.geekhub.palto.object.ItemDialogList;
import com.squareup.picasso.Picasso;

/**
 * Created by andrey on 11.02.16.
 */
public class ChatListAdapter extends FirebaseListAdapter<ItemDialogList> {



    public ChatListAdapter(Query ref, Activity activity, int layout) {
        super(ref, ItemDialogList.class, layout, activity);
    }

    @Override
    protected void populateView(View v, ItemDialogList model) {
        ((TextView) v.findViewById(R.id.lastDate)).setText(model.getLastDate());
        ((TextView) v.findViewById(R.id.lastMessage)).setText(model.getLastMessage());
        ((TextView) v.findViewById(R.id.nick)).setText(model.getNick());
        Picasso.with(v.getContext()).load(model.getIconImage()).into( ((ImageView)v.findViewById(R.id.iconImage)));


    }
}
