import SqlConnections.DataConnect;
import crawlers.MyFoodBookClass;


import java.sql.Connection;

public class Demo {


    public static void scrapperDemo() {
        MyFoodBookClass myFoodBookClass = new MyFoodBookClass();
        myFoodBookClass.startThreads();
    }

    public static void sqlDemo() {
        Connection con = DataConnect.getConnection();
    }
}
