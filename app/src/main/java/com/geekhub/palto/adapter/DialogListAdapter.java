package com.geekhub.palto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.geekhub.palto.R;
import com.geekhub.palto.object.ItemDialogList;
import com.geekhub.palto.object.UserForSearch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by andrey on 08.02.16.
 */
public class DialogListAdapter extends BaseAdapter{
    ArrayList <ItemDialogList>dialogArrayList;
    Context context;
    ArrayList<String> iDAraay;
    public DialogListAdapter(Context context, ArrayList <ItemDialogList>dialogArrayList, ArrayList<String> arrayList){
        this.dialogArrayList = dialogArrayList;
        this.context = context;
        this.iDAraay = arrayList;

    }
    @Override
    public int getCount() {
        return dialogArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dialogArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    static class ViewHolder {
        ImageView iconImage;
        ImageView iconImageLastMes;
        TextView nick;
        TextView lastDate;
        TextView lastMessage;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       final ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dialog_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iconImage = (ImageView) convertView.findViewById(R.id.iconImage);
            viewHolder.iconImageLastMes = (ImageView) convertView.findViewById(R.id.iconImageLastMes);
            viewHolder.lastDate = (TextView) convertView.findViewById(R.id.lastDate);
            viewHolder.lastMessage = (TextView) convertView.findViewById(R.id.lastMessage);
            viewHolder.nick = (TextView) convertView.findViewById(R.id.nick);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(dialogArrayList.get(position).getIconImage()).into(viewHolder.iconImageLastMes);
        viewHolder.lastDate.setText(dialogArrayList.get(position).getLastDate());
        viewHolder.lastMessage.setText(dialogArrayList.get(position).getLastMessage());
        viewHolder.nick.setText(dialogArrayList.get(position).getNick());
        Picasso.with(context).load(iDAraay.get(position)).into(viewHolder.iconImage);
        return convertView;

    }
}
