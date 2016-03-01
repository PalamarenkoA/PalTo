package com.geekhub.palto.object;

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

    public String id;
    String iconImage;
    String lastMessageNick;
    String lastDate;
    String lastMessage;
    String received;
    String userChatWithNick;



    public ItemDialogList(String iconImage,String lastMessageNick, String lastMessage, String id, String received){

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentTimeStr = dateFormat.format(cal.getTime());
        this.received = received;
        this.iconImage = iconImage;
        this.lastMessageNick = lastMessageNick;
        this.lastDate = currentTimeStr;
        this.lastMessage = lastMessage;
        this.id=id;
    }

    public String getIconImage() {
        return iconImage;
    }

    public String getLastMessageNick() {
        return lastMessageNick;
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

    public void setLastMessageNick(String lastMessageNick) {
        this.lastMessageNick = lastMessageNick;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserChatWithNick() {
        return userChatWithNick;
    }

    public void setUserChatWithNick(String userChatWithNick) {
        this.userChatWithNick = userChatWithNick;
    }
}
