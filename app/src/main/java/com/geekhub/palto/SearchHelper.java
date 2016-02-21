package com.geekhub.palto;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.geekhub.palto.object.Interest;
import com.geekhub.palto.object.UserForSearch;

import java.util.ArrayList;


/**
 * Created by andrey on 17.02.16.
 */
public class SearchHelper {
    final int POINTFORCITY = 10;
    final int POINTFORMUSIK = 2;
    final int POINTFORFILM = 2;
    final int POINTFORZNAKOM = 2;
    final int POINTFOREYES = 3;
    final int POINTFORGROWTH = 4;

    SharedPreferences srefs;
    public UserForSearch  init (ArrayList<UserForSearch> list, UserForSearch param,
                                ArrayList<String> users,
                                ArrayList<String> films,
                                ArrayList<String> musiks,
                                ArrayList<String> znakoms){
        srefs = PreferenceManager.getDefaultSharedPreferences(PaltoApplication.CONTEXT);

        list = pointAdd(list,param,films,musiks,znakoms);
        int maxPoint = fiendMaxPoint(list);

       return choiceUser(list,maxPoint,users);}

    private int fiendMaxPoint(ArrayList<UserForSearch> list){
        int maxPoint = 0;
        for (UserForSearch userForSearch : list){
            if(!(userForSearch.getId().equals(srefs.getString("VKUserID","")))){
            if(userForSearch.getPoints()> maxPoint){
                maxPoint = userForSearch.getPoints();
            }}}
   return maxPoint; }
    private UserForSearch choiceUser(ArrayList<UserForSearch> list,int maxPoint, ArrayList<String> users){

        ArrayList<UserForSearch> choices = new ArrayList<>();
        for (UserForSearch userForSearch : list){
            if(userForSearch.getPoints() == maxPoint && !userForSearch.getId()
                    .equals(srefs.getString("VKUserID", "")) ){
                if(!users.contains(userForSearch.getId())) {

                    choices.add(userForSearch);
                }
            }}
        int i = (int)(Math.random()*(choices.size()));
        Log.d("logos", String.valueOf(choices.size()));
        Log.d("logos", String.valueOf(i));
        if(choices.size()== 0){
            return null;
        }
        UserForSearch choice = choices.get(i);

        return  choice;
    }
    private ArrayList<UserForSearch> pointAdd(ArrayList<UserForSearch> list,
                                              UserForSearch param,
                                              ArrayList<String> films,
                                              ArrayList<String> musiks,
                                              ArrayList<String> znakoms
                                              ){

        for (UserForSearch userForSearch : list){
            ArrayList<String> znakomsUser = createList(userForSearch.getInterest(),2);
            ArrayList<String> filmsUser = createList(userForSearch.getInterest(),0);
            ArrayList<String> musiksUser = createList(userForSearch.getInterest(),1);
            for(String s:films){
                if(filmsUser.contains(s)){
                    userForSearch.setPoints(userForSearch.getPoints() + POINTFORFILM);
                }
            }
            for(String s:musiks){
                if(musiksUser.contains(s)){
                    userForSearch.setPoints(userForSearch.getPoints() + POINTFORMUSIK);
                }
            }
            for(String s:znakoms){
                if(znakomsUser.contains(s)){
                    userForSearch.setPoints(userForSearch.getPoints() + POINTFORZNAKOM);
                }
            }

            if(userForSearch.getCityID() == param.getCityID()){
                userForSearch.setPoints(userForSearch.getPoints() + POINTFORCITY);
            }
            if(userForSearch.getInterest().getEyes().equals(param.getInterest().getEyes())){
                userForSearch.setPoints(userForSearch.getPoints() + POINTFOREYES);
            }
            if(userForSearch.getInterest().getGrowth().equals(param.getInterest().getGrowth())){
                userForSearch.setPoints(userForSearch.getPoints() + POINTFORGROWTH);
            }
            Log.d("user", userForSearch.getName() + " nabral - " + userForSearch.getPoints());

    }
return list;}
    private ArrayList<String> createList (Interest interest, int par){
    ArrayList<String> arrayList = new ArrayList<>();
    if(par == 0){
        arrayList.add(interest.getFilm().getFilm0());
        arrayList.add(interest.getFilm().getFilm1());
        arrayList.add(interest.getFilm().getFilm2());
        arrayList.add(interest.getFilm().getFilm3());
        arrayList.add(interest.getFilm().getFilm4());
    }else {
        if(par ==1){
            arrayList.add(interest.getMusik().getMusik0());
            arrayList.add(interest.getMusik().getMusik1());
            arrayList.add(interest.getMusik().getMusik2());
            arrayList.add(interest.getMusik().getMusik3());
            arrayList.add(interest.getMusik().getMusik4());
        }else{
            if(par ==2){
            arrayList.add(interest.getZnakom().getZnakom0());
            arrayList.add(interest.getZnakom().getZnakom1());
            arrayList.add(interest.getZnakom().getZnakom2());
           }
        }
    }

   return arrayList; }
}
