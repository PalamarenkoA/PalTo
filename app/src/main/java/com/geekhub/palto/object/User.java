package com.geekhub.palto.object;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andrey on 05.02.16.
 */
public class User {
    String name;
    String lastName;
    String id;
    int sex;
    String bdate;
    String city;
    int cityID;
    String country;
    int countryID;

    public String getPhoto_200() {
        return photo_200;
    }

    String photo_200;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public int getSex() {
        return sex;
    }

    public String getBdate() {
        return bdate;
    }

    public String getCity() {
        return city;
    }

    public int getCityID() {
        return cityID;
    }

    public String getCountry() {
        return country;
    }

    public int getCountryID() {
        return countryID;
    }


   public User(JSONObject jsonObject) throws JSONException {
       photo_200 = jsonObject.getString("photo_200");
       name = jsonObject.getString("first_name");
       lastName = jsonObject.getString("last_name");
       id = jsonObject.getString("id");
       try {
           sex = jsonObject.getInt("sex");
       }catch (JSONException e){
           e.printStackTrace();

       }
       try {
           bdate = jsonObject.getString("bdate");
       }catch (JSONException e){
           e.printStackTrace();

       }
       try {
           city = jsonObject.getJSONObject("city").getString("title");
       }catch (JSONException e){
           e.printStackTrace();

       }
       try {
           cityID = jsonObject.getJSONObject("city").getInt("id");
       }catch (JSONException e){
           e.printStackTrace();

       }

       try {
           country = jsonObject.getJSONObject("country").getString("title");
       }catch (JSONException e){
           e.printStackTrace();

       }
       try {
           countryID = jsonObject.getJSONObject("country").getInt("id");
       }catch (JSONException e){
           e.printStackTrace();

       }







    }
}
