package com.geekhub.palto.customviews.splash;


import java.util.ArrayList;

/**
 * Created by duke0808 on 25.03.16.
 */
public class MySplashScreenFactory {
    static ArrayList<Page> pageArrayList = new ArrayList<>();
    static ArrayList<SplahFrag> fragArrayList = new ArrayList<>();
    static int counter = 0;

    public SplahFrag getNext() {
        if (counter >= fragArrayList.size()) {
            counter = 0;
        }
        return fragArrayList.get(counter++);
    }

    public void initPageList() {
        pageArrayList.add(new Page("http://www.easydistancejob.com/wp-content/uploads/2015/06/%D0%BE%D0%B1%D1%89%D0%B5%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-%D1%84%D0%BE%D1%80%D1%83%D0%BC%D0%B0%D1%85-%D0%B2-%D0%B8%D0%BD%D1%82%D0%B5%D1%80%D0%BD%D0%B5%D1%82%D0%B5.jpg",
                "С нами ты можеш просто начать чат с новым человеком"));
        pageArrayList.add(new Page("https://f-a.d-cd.net/97d501u-960.jpg", "Подружиться"));
        pageArrayList.add(new Page("http://nlp-texnika.ru/images/img_41.jpg", "Расширить свой круг общения"));
        pageArrayList.add(new Page("http://firstsocial.info/wp-content/uploads/2013/08/1346168378_anon-d0bfd0b5d181d0bed187d0bdd0b8d186d0b0-d0bed0bfd180d0bed181-d0bbd18ed0b1d0bed0b2d18c-226667.jpeg?48c69a",
                "Возможно...Ты найдешь свою любовь здесь..."));
        pageArrayList.add(new Page("http://rabotai.in/rabota/index.jpg", "Или новую, крутую работу!!!"));
        pageArrayList.add(new Page("http://fishki.net/picsw/062012/28/bonus/podmig/005.jpg", "Скорее начни"));
        pageArrayList.add(new Page("http://chto-znachit.su/wp-content/uploads/2014/11/14077666984620.jpg", "С PalTo ты не соскучишся!"));
        pageArrayList.add(new Page("http://holidaydays.ru/wp-content/uploads/2014/11/Disco-3.jpg", "Просто заполни анкету, и вперед!"));
        initFragList(pageArrayList);
    }

    public void initFragList(ArrayList<Page> pageArrayList) {
        for (Page p : pageArrayList) {
            fragArrayList.add(SplahFrag.newInstance(p.url, p.text));
        }
    }

    public int getCount() {
        return pageArrayList.size();
    }

    private class Page {
        String url;
        String text;

        public Page(String url, String text) {
            this.url = url;
            this.text = text;
        }
    }
}
