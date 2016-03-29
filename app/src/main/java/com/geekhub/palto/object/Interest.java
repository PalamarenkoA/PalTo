package com.geekhub.palto.object;

public class Interest{

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

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
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
    Film film;
    Musik musik;
}
