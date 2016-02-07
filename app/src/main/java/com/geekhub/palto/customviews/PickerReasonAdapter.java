package com.geekhub.palto.customviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;


import com.geekhub.palto.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trinity on 11/11/15.
 */
public class PickerReasonAdapter extends BaseAdapter {

    List<String> list = new ArrayList<>();
    int selected;

    public PickerReasonAdapter() {
        this.list = new ArrayList<>();
        initList();
        selected=0;
    }

    private void initList() {
        list.add("Reason 1");
        list.add("Reason 2");
        list.add("Reason 3");
        list.add("Reason 4");
        list.add("Reason 5");
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reason_list_item, null);
            holder.name = (CheckedTextView) convertView.findViewById(R.id.reason_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String item = getItem(position);

        holder.name.setText(item);
        holder.name.setChecked(position==selected ? true : false);

        return convertView;
    }

//    public void changeList(List<UserResponse> l) {
//        if (l == null)
//            list = new ArrayList<>();
//        else
//            list = l;
//        notifyDataSetChanged();
//    }

    public List<String> getList() {
        return list;
    }


    static class ViewHolder {
        CheckedTextView name;
    }

}
