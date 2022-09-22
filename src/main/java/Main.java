import crawlers.MyFoodBookClass;
import org.jsoup.nodes.Document;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        MyFoodBookClass myFoodBookClass = new MyFoodBookClass();
        long a = System.currentTimeMillis();
        myFoodBookClass.startThreads();
        System.out.println("Time spent - " + ((System.currentTimeMillis() - a) / 1000) + " seconds");

    }

}


