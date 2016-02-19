package com.geekhub.palto;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.geekhub.palto.object.UserForSearch;

import java.util.ArrayList;

/**
 * Created by andrey on 17.02.16.
 */
public class SearchHelper {
    final int POINTFORCITY = 10;
    SharedPreferences srefs;
    public UserForSearch  init (ArrayList<UserForSearch> list, UserForSearch param){


        list = pointAdd(list,param);




        int maxPoint = fiendMaxPoint(list);

       return choiceUser(list,maxPoint);}

    private int fiendMaxPoint(ArrayList<UserForSearch> list){
        int maxPoint = 0;
        for (UserForSearch userForSearch : list){
            if(userForSearch.getPoints()> maxPoint){
                maxPoint = userForSearch.getPoints();
            }}
   return maxPoint; }
    private UserForSearch choiceUser(ArrayList<UserForSearch> list,int maxPoint){
        srefs = PreferenceManager.getDefaultSharedPreferences(PaltoApplication.CONTEXT.getApplicationContext());
        ArrayList<UserForSearch> choices = new ArrayList<>();
        for (UserForSearch userForSearch : list){
            if(userForSearch.getPoints() == maxPoint && !userForSearch.getId()
                    .equals(srefs.getString("VKUserID", "")) ){
                choices.add(userForSearch);

            }}
        int i = (int)(Math.random()*(choices.size()));
        Log.d("logos", String.valueOf(choices.size()));
        Log.d("logos", String.valueOf(i));
        UserForSearch choice = choices.get(i);

        return  choice;
    }
    private ArrayList<UserForSearch> pointAdd(ArrayList<UserForSearch> list, UserForSearch param){
        for (UserForSearch userForSearch : list){
            if(userForSearch.getCityID() == param.getCityID()){
                userForSearch.setPoints(userForSearch.getPoints() + POINTFORCITY);
            }
            if(userForSearch.getCountryID() == param.getCountryID()){
                userForSearch.setPoints(userForSearch.getPoints() + 3);
            }
    }
return list;}
}
