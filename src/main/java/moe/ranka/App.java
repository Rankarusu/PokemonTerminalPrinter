package moe.ranka;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        var client = new HttpClient();
        var res = client.fetchIcon(args[0]);
        var printer = new ImagePrinter();
        printer.printImage(res);
    }
}
