package moe.ranka;

public class App {
    public static void main(String[] args) {
        var client = new HttpClient();
        var res = client.getSpriteByIdOrName(args[0]);
        var printer = new ImagePrinter();
        printer.printImage(res);
    }
}
