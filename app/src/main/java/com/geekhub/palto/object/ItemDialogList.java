package com.geekhub.palto.object;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrey on 08.02.16.
 */
public class ItemDialogList {
    @SuppressWarnings("unused")
    private ItemDialogList(){

    }
    private ItemDialogList(String str){

        Log.d("logo",str);
    }
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

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
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
