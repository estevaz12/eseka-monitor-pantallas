package ar.com.leo.monitor.fx;

public class Launcher {
    // For FAT JAR you will need a main class that doesn't extend from Application
    public static void main(String[] args) {
        if (args.length > 2)
            MainApp.main(args);
        else
            System.out.println("Erorr: Se necesitan por lo menos 3 parámetros-> \"TIEMPO\" \"SECTOR\" \"GRUPOS\"");
    }
}