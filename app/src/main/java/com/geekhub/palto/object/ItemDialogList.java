package com.geekhub.palto.object;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geekhub.palto.useragent.UserAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by andrey on 08.02.16.
 */
public class ItemDialogList {
    @SuppressWarnings("unused")
    private ItemDialogList(){

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

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    String received;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;


    public ItemDialogList(String iconImage,String nick, String lastMessage, String id, String received){

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentTimeStr = dateFormat.format(cal.getTime());
        this.received = received;
        this.iconImage = iconImage;
        this.nick = nick;
        this.lastDate = currentTimeStr;
        this.lastMessage = lastMessage;
        this.id=id;
    }

}
