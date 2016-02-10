package com.geekhub.palto.object;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by andrey on 08.02.16.
 */
public class ItemDialogList {
    public String getIconImage() {
        return iconImage;
    }

    public String getNick() {
        return nick;
    }

    public String getLastDate() {
        return lastDate;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    String iconImage;
    String nick;
    String lastDate;
    String lastMessage;
    public ItemDialogList(String iconImage,String nick, String lastDate, String lastMessage){
        this.iconImage = iconImage;
        this.nick = nick;
        this.lastDate = lastDate;
        this.lastMessage = lastMessage;
    }
}
