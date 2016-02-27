package com.geekhub.palto.Helper;

import com.geekhub.palto.object.Interest;

import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by andrey on 26.02.16.
 */
public class Helper {
    public ArrayList<String> createList (Interest interest, int par){
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
