import crawlers.Copykat;

public class Main {
    public static void main(String[] args) {
        Copykat crawler = new Copykat("https://copykat.com/grilled-buffalo-wings/");
        Copykat crawler2= new Copykat("https://copykat.com/spicy-asian-chicken-wings/");
        System.out.println(crawler.getAll());
        System.out.println(crawler2.getAll());
    }
}
