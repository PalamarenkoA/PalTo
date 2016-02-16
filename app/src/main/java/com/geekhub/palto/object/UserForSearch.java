package com.geekhub.palto.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by andrey on 16.02.16.
 */
public class UserForSearch {

    public class Interest{
         public class Musik{


             public String getMusik0() {
                 return musik0;
             }

             public void setMusik0(String musik0) {
                 this.musik0 = musik0;
             }

             public String getMusik2() {
                 return musik2;
             }

             public void setMusik2(String musik2) {
                 this.musik2 = musik2;
             }

             public String getMusik3() {
                 return musik3;
             }

             public void setMusik3(String musik3) {
                 this.musik3 = musik3;
             }

             public String getMusik4() {
                 return musik4;
             }

             public void setMusik4(String musik4) {
                 this.musik4 = musik4;
             }

             public String getMusik1() {
                 return musik1;
             }

             public void setMusik1(String musik1) {
                 this.musik1 = musik1;
             }

             String musik0;
             String musik2;
             String musik3;
             String musik4;
             String musik1;
         }
         public class film{


             public String getFilm0() {
                 return film0;
             }

             public void setFilm0(String film0) {
                 this.film0 = film0;
             }

             public String getFilm2() {
                 return film2;
             }

             public void setFilm2(String film2) {
                 this.film2 = film2;
             }

             public String getFilm3() {
                 return film3;
             }

             public void setFilm3(String film3) {
                 this.film3 = film3;
             }

             public String getFilm4() {
                 return film4;
             }

             public void setFilm4(String film4) {
                 this.film4 = film4;
             }

             public String getFilm1() {
                 return film1;
             }

             public void setFilm1(String film1) {
                 this.film1 = film1;
             }

             String film0;
             String film2;
             String film3;
             String film4;
             String film1;
         }
         public class Znakom{
             public String getZnakom1() {
                 return znakom1;
             }

             public void setZnakom1(String znakom1) {
                 this.znakom1 = znakom1;
             }

             public String getZnakom2() {
                 return znakom2;
             }

             public void setZnakom2(String znakom2) {
                 this.znakom2 = znakom2;
             }

             public String getZnakom0() {
                 return znakom0;
             }

             public void setZnakom0(String znakom0) {
                 this.znakom0 = znakom0;
             }

             String znakom1;
             String znakom2;
             String znakom0;
         }

         public String getGrowth() {
             return growth;
         }

         public void setGrowth(String growth) {
             this.growth = growth;
         }

         public String getEyes() {
             return eyes;
         }

         public void setEyes(String eyes) {
             this.eyes = eyes;
         }

         public Znakom getZnakom() {
             return znakom;
         }

         public void setZnakom(Znakom znakom) {
             this.znakom = znakom;
         }

         public film getFilm() {
             return film;
         }

         public void setFilm(film film) {
             this.film = film;
         }

         public Musik getMusik() {
             return musik;
         }

         public void setMusik(Musik musik) {
             this.musik = musik;
         }

         String growth;
         String eyes;
         Znakom znakom;
         film film;
         Musik musik;
     }
    @SuppressWarnings("unused")
    public UserForSearch(){

    }


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
    String nick;
    String name;
    String lastName;
    String id;
    int sex;
    String bdate;
    String city;
    int cityID;
    String photo_200;

    public String getPhoto_200() {
        return photo_200;
    }

    public void setPhoto_200(String photo_200) {
        this.photo_200 = photo_200;
    }

    String country;
    int countryID;
    Interest interest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public Interest  getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }
}
