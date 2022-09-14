package crawlers;

import model.Recipe;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;


public class Copykat {

    public Copykat(String URL) {
        this.URL = URL;
    }
    private final String URL;
    private Document doc;

    public Recipe getAll() {
        try {
            Connection con = Jsoup.connect(URL);
            con.maxBodySize(0);
            this.doc = con.get();
            return new Recipe(getName(),getIngredients(),getInstructions());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getName() {
        return doc.getElementsByClass("entry-title").first().text();
    }

    private ArrayList<String> getIngredients() {
        ArrayList<String> ret = new ArrayList<>();
        for (Element ul: doc.getElementsByTag("ul")){
            if(ul.className().isEmpty()){
                for (Element li : ul.getElementsByTag("li")) {
                    if (li.children().isEmpty()) {
                        ret.add(li.text());
                    }
                }
            }
        }
        return ret;
    }
    private String getInstructions(){
        StringBuilder ret= new StringBuilder();
        for (Element ol : doc.getElementsByTag("ol")) {
            if(ol.hasAttr("type")){
                ret.append(ol.text());
            }
            if(ol.hasAttr("start")){
                ret.append(ol.text());
            }
        }
        return ret.toString();
    }
}
