package com.geekhub.palto;

import com.geekhub.palto.object.UserForSearch;

import java.util.ArrayList;

/**
 * Created by andrey on 17.02.16.
 */
public class SearchHelper {
    public ArrayList<UserForSearch>  init (ArrayList<UserForSearch> list, UserForSearch param){
        for (UserForSearch userForSearch : list){
            if(userForSearch.getCityID() == param.getCityID()){
                userForSearch.setPoints(userForSearch.getPoints() + 10);
            }
            if(userForSearch.getCountryID() == param.getCountryID()){
                userForSearch.setPoints(userForSearch.getPoints() + 3);
            }
        }

    return  list;}
}
