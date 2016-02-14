package com.geekhub.palto.viewmodel;

import com.geekhub.palto.R;
import com.geekhub.palto.activity.SearchNewChatActivity;

/**
 * Created by andrey on 13.02.16.
 */
public class SearchNewChatViewModel {

    SearchNewChatActivity activity;

    public SearchNewChatViewModel (SearchNewChatActivity activity){
        this.activity = activity;
        activity.binding.musikInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.musik));
        activity.binding.znakomInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.znakom));
        activity.binding.filmInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.film));
        activity.binding.growthInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.growth));
        activity.binding.eyesInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.eyes));
        activity.binding.cityInterestPicker.setItemsFromResource(activity.getResources().getStringArray(R.array.eyes));
    }
}
