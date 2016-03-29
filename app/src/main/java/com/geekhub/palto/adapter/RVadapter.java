package com.geekhub.palto.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekhub.palto.R;
import com.geekhub.palto.activity.ChatActivity;
import com.geekhub.palto.object.ItemDialogList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RVadapter extends RecyclerView.Adapter<RVadapter.ViewHolder>{
    List<ItemDialogList>persons;
   public Context context;
    ArrayList<String> photo;
    ArrayList<String> nicks;
    MyOnClick listener;
    public interface MyOnClick{
        public void onClick( int i);
    }
    public RVadapter(Context context, List<ItemDialogList> persons, ArrayList<String> photo, ArrayList<String> nicks, MyOnClick listener){
        this.persons = persons;
        this.context = context;
        this.photo = photo;
        this.nicks = nicks;
        this.listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Picasso.with(context).load(photo.get(i)).into(viewHolder.dialogIcon);
        Picasso.with(context).load(persons.get(i).getIconImage()).into(viewHolder.mesIcon);
        viewHolder.mes.setText(persons.get(i).getLastMessage());
        viewHolder.mesName.setText(persons.get(i).getLastMessageNick());
        viewHolder.dialogName.setText(nicks.get(i));
        viewHolder.time.setText(persons.get(i).getLastDate());
        viewHolder.setClickListener(listener);

    }

    public void remove(int position) {
        persons.remove(position);
        notifyItemRemoved(position);
    }

    public void swap(int firstPosition, int secondPosition){
        Collections.swap(persons, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }


    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView dialogName;
        TextView mesName;
        TextView mes;
        TextView time;
        ImageView dialogIcon;
        ImageView mesIcon;
        MyOnClick listener;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setOnClickListener(this);
            dialogName = (TextView) itemView.findViewById(R.id.nameDialog);
            mesName = (TextView) itemView.findViewById(R.id.nameMes);
            mes = (TextView) itemView.findViewById(R.id.mes);
            time = (TextView) itemView.findViewById(R.id.timeLastMes);
            dialogIcon = (CircleImageView) itemView.findViewById(R.id.imageDialog);
            mesIcon = (CircleImageView) itemView.findViewById(R.id.imageMes);
        }


        public void setClickListener(MyOnClick onClick){
            this.listener = onClick;
        }
        @Override
        public void onClick(View v) {
            listener.onClick(getAdapterPosition());
        }

    }

}
