package com.geekhub.palto.viewmodel;

import com.geekhub.palto.activity.ChatListActivity;
import com.geekhub.palto.adapter.DialogListAdapter;
import com.geekhub.palto.object.ItemDialogList;

import java.util.ArrayList;

/**
 * Created by andrey on 08.02.16.
 */
public class ChatListViewModel {
    ChatListActivity activity;

    public ChatListViewModel (ChatListActivity activity){
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
        activity.binding.dialogList.setAdapter(dialogListAdapter);



    }
}
