package sgh;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileWriter;

public class StockData {

    public static void getAndProcessChange(String stock) throws IOException {
        String filePath = "data_in/" + stock + ".csv";
        File datain = new File(filePath);
        if (!datain.exists()) {
                //TODO HINT: You might need to check if the file doesn't already exist...
        download("https://query1.finance.yahoo.com/v7/finance/download/" + stock +
                                "?period1=1554504399&period2=1586126799&interval=1d&events=history",
                        filePath);

            }
         Scanner sc = new Scanner(datain);
         String wiersz = sc.nextLine();
         FileWriter dataout = new FileWriter("data_out/" + stock + ".csv");
         dataout.write(wiersz + ",Change" + "\n");

                if (sc.hasNextLine()) {
                    wiersz = sc.nextLine();
                    String[] wartosci = wiersz.split(",");
                    double poczatek = Double.valueOf(wartosci[1]);
                    double koniec = Double.valueOf(wartosci[4]);
                    dataout.write(wiersz + "," + ((koniec - poczatek) / poczatek) * 100 + "\n");
                }
                dataout.close();
            }
    public static void download(String url, String fileName) throws IOException {
        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }

    public static void main(String[] args) throws IOException {
        String[] stocks = new String[] { "IBM", "MSFT", "GOOG" };
        for (String s : stocks) {
            getAndProcessChange(s);
        }
    }
}
