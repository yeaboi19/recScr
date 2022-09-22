package crawlers;

import model.RecipeRecord;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MyFoodBookClass {
    private final Document document;

    public Document parseUrlToDocument(String url) {
        Document document = null;
        try {
            Connection con = Jsoup.connect(url);
            con.maxBodySize(0);
            document = con.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public MyFoodBookClass() {
        this.document = parseUrlToDocument("https://myfoodbook.com.au/categories");
    }

    public ArrayList<String> getCategoryLinks() {
        ArrayList<String> categoryList = new ArrayList<>();
        for (Element element : document.getElementsByClass("view-content")) {
            Elements linkElements = element.getElementsByTag("a");
            for (int i = 0; i < element.getElementsByTag("a").size(); i += 2) {
                Attributes linkAttributes = linkElements.get(i).attributes();
                categoryList.add(linkAttributes.get("href"));
            }
        }
        return categoryList;
    }

    public ArrayList<String> getLinks(Document document) {
        ArrayList<String> linkList = new ArrayList<>();
        for (Element div : document.getElementsByClass("rc-title")) {
            for (Element a : div.getElementsByTag("a")) {
                linkList.add(a.attributes().get("href"));
            }
        }
        return linkList;
    }


    //every scraper will end with this method. each thread will bundle its own recipe and save it in database/file
    //THIS IS THE AUTOMATION VOID ONLY. THIS WILL BE CALLED IN MAIN
    public void startThreads() {
        ArrayList<String> categoryList = getCategoryLinks();
        for (String URL : categoryList) {
            Document document = parseUrlToDocument("https://myfoodbook.com.au" + URL);
            ArrayList<String> links = getLinks(document);
            for (String url : links) {
                new Thread(() -> {
                    MyFoodBookThread thread = new MyFoodBookThread("https://myfoodbook.com.au" + url);
                    System.out.println(thread.getRecipe());
                }).start();
            }
        }

    }
}


// --------------------------------------------------------get recipe info
class MyFoodBookThread {
    private Document document = null;

    public MyFoodBookThread(String url) {
        try {
            Connection con = Jsoup.connect(url);
            con.maxBodySize(0);
            this.document = con.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRecipeName() {
        return Objects.requireNonNull(document.getElementsByClass("rs-title").first()).text();
    }

    private ArrayList<String> getIngredients() {
        ArrayList<String> ret = new ArrayList<>();
        Elements ingredients = document.getElementsByClass("ingredients");
        for (Element ing : ingredients) {
            ret.add(ing.text());
        }
        return ret;
    }

    private String getInstructions() {
        return Objects.requireNonNull(document.getElementsByClass("method").first()).text();
    }

    public RecipeRecord getRecipe() {
        return new RecipeRecord(getRecipeName(), getIngredients(), getInstructions());
    }

    private void writeItDown() {

    }
}
