package com.geekhub.palto.object;

/**
 * Created by andrey on 06.03.16.
 */
public class ChatListItem {
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIconLastMes() {
        return iconLastMes;
    }

    public void setIconLastMes(String iconLastMes) {
        this.iconLastMes = iconLastMes;
    }

    public String getLastMes() {
        return lastMes;
    }

    public void setLastMes(String lastMes) {
        this.lastMes = lastMes;
    }

    public String getIconForChat() {

        return iconForChat;
    }

    public void setIconForChat(String iconForChat) {
        this.iconForChat = iconForChat;
    }

    String iconForChat;
    String nick;
    String iconLastMes;
    String lastMes;

}
