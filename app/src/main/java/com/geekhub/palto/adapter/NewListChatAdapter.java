package com.geekhub.palto.adapter;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geekhub.palto.R;
import com.geekhub.palto.object.ChatListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by andrey on 06.03.16.
 */
public class NewListChatAdapter extends RecyclerView.Adapter<NewListChatAdapter.PersonViewHolder>{
    ArrayList<ChatListItem> list;
    Context context;
    NewListChatAdapter (ArrayList<ChatListItem> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getIconForChat()).into( holder.iconForChat);
        Picasso.with(context).load(list.get(position).getIconLastMes()).into(holder.iconLastMes);
        holder.nick.setText(list.get(position).getNick());
        holder.lastMes.setText(list.get(position).getLastMes());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iconForChat;
        TextView nick;
        CircleImageView iconLastMes;
        TextView lastMes;
        CardView cv;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            iconForChat = (CircleImageView)itemView.findViewById(R.id.iconForChat);
            iconLastMes = (CircleImageView)itemView.findViewById(R.id.iconLastMes);
            nick = (TextView)itemView.findViewById(R.id.nick);
            lastMes = (TextView)itemView.findViewById(R.id.lastMes);

        }
    }

}