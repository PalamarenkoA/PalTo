package com.geekhub.palto;

import android.util.Log;

import com.geekhub.palto.object.UserForSearch;

import java.util.ArrayList;

/**
 * Created by andrey on 17.02.16.
 */
public class SearchHelper {
    final int POINTFORCITY = 10;

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
        ArrayList<UserForSearch> choices = new ArrayList<>();
        for (UserForSearch userForSearch : list){
            if(userForSearch.getPoints() == maxPoint){
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
