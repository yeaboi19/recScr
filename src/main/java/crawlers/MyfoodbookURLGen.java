package crawlers;

import model.Recipe;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
//--------------------------------------------------------get categories
public class MyfoodbookURLGen {
    private Document doc;

    public MyfoodbookURLGen() {
        try {
            Connection con = Jsoup.connect("https://myfoodbook.com.au/categories");
            con.maxBodySize(0);
            this.doc = con.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getCatLinks(){
        ArrayList<String> ret = new ArrayList<>();
        for (Element el : doc.getElementsByClass("view-content")) {
            for (Element a : el.getElementsByTag("a")) {
                for (Attribute attr : a.attributes()) {
                    ret.add(attr.getValue());
                }
            }
        }
        return ret;
    }
    public void startThreads(){
        for (String URL : getCatLinks()) {
            MyfoodbookRecURLGen threads = new MyfoodbookRecURLGen("https://myfoodbook.com.au"+URL);
            threads.start();
        }


        //test
//        MyfoodbookRecURLGen threads = new MyfoodbookRecURLGen("https://myfoodbook.com.au"+getCatLinks().get(0));
//        threads.start();
    }
}
//--------------------------------------------------------get recipes
class MyfoodbookRecURLGen extends Thread{
    private Document doc;
    public MyfoodbookRecURLGen(String URL){
        try {
            Connection con = Jsoup.connect(URL);
            con.maxBodySize(0);
            this.doc = con.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public synchronized void start() {
        for (String URL : getLinks()) {
            Myfoodbook thread = new Myfoodbook("https://myfoodbook.com.au"+URL);
            thread.start();
        }

        //test
//        Myfoodbook thread = new Myfoodbook("https://myfoodbook.com.au"+getLinks().get(0));
//        thread.start();
    }
    private ArrayList<String> getLinks(){
        ArrayList<String> ret = new ArrayList<>();
        for (Element div : doc.getElementsByClass("rc-title")) {
            for (Element a : div.getElementsByTag("a")) {
                for (Attribute attr : a.attributes()) {
                    ret.add(attr.getValue());
                }
            }
        }
        return ret;
    }
}
// --------------------------------------------------------get recipe info
class Myfoodbook extends Thread{
    private Document doc;

    public Myfoodbook(String URL) {
        try {
            Connection con = Jsoup.connect(URL);
            con.maxBodySize(0);
            this.doc = con.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void start() {
        System.out.println(getRecipe());
    }

    private String getRecipeName(){
        return doc.getElementsByClass("rs-title").first().text();
    }
    private ArrayList<String> getIngredients(){
        ArrayList<String> ret = new ArrayList<>();
        Elements ings = doc.getElementsByClass("ingredients");
        for (Element ing : ings) {
            ret.add(ing.text());
        }
        return ret;
    }
    private String getMethod(){
        return doc.getElementsByClass("method").first().text();
    }

    private Recipe getRecipe(){
        return new Recipe(getRecipeName(),getIngredients(),getMethod());
    }

    private void writeItDown(){

    }
}
