package com.geekhub.palto.viewmodel;

import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.adapter.DialogListAdapter;
import com.geekhub.palto.object.ItemDialogList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by andrey on 09.02.16.
 */
public class ChatViewModel {
    ChatActivity activity;
    public ChatViewModel ( ChatActivity activity){
        this.activity = activity;
        ItemDialogList itemDialogList = new ItemDialogList ("http://cs623630.vk.me/v623630022/44bd6/wEJMXO7t_ic.jpg","Romashka","12.12.03","Где героин, РОМА где он???");
        ArrayList<ItemDialogList> itemDialogListArrayList = new ArrayList<>();

        itemDialogListArrayList.add(itemDialogList);
        itemDialogListArrayList.add(itemDialogList);
        itemDialogListArrayList.add(itemDialogList);
        itemDialogListArrayList.add(itemDialogList);
        itemDialogListArrayList.add(itemDialogList);
        itemDialogListArrayList.add(itemDialogList);
        itemDialogListArrayList.add(itemDialogList);
        itemDialogListArrayList.add(itemDialogList);
        DialogListAdapter dialogListAdapter = new DialogListAdapter(activity,itemDialogListArrayList);
        activity.binding.chatList.setAdapter(dialogListAdapter);
        Picasso.with(activity).load("http://cs623630.vk.me/v623630022/44bd6/wEJMXO7t_ic.jpg").into(activity.binding.myIcon);
        Picasso.with(activity).load("http://cs623630.vk.me/v623630022/44bd6/wEJMXO7t_ic.jpg").into(activity.binding.yourIcon);
    }
}
