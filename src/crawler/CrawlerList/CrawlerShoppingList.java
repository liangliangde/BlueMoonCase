package crawler.CrawlerList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class CrawlerShoppingList {

    public static int errorstep = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        HtmlParserTool conparser = new HtmlParserTool();
        FileOutputStream out = new FileOutputStream("../data/list.txt", true);
        File file = new File("../data/listcategory");

        if (file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    file), "GBK");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            do {
//                int recordnum = conparser.extracLinks(lineTxt + "p" + j, out, errorURL, log);
//                for (int j = lastpage + 1; j <= 50; j++) {
//                    System.out.print(lineTxt + "p" + j + ", ");
//
//                    //crawler this url
//                    int recordnum = conparser.extracLinks(lineTxt + "p" + j, out, errorURL, log);
//
//                    i++;
//                    if (i >= 2) {
//                        Random rand = new Random();
//                        Integer r = rand.nextInt(6) + 1;
//                        Thread.sleep(r * 1000);
//                        System.out.println("sleep:" + r + "s");
//                        i = 0;
//                    }
//                    if (recordnum < 15) {
//                        break;
//                    } else if (recordnum == -1) {
//                        j--;
//                    }
//                }
            } while ((lineTxt = bufferedReader.readLine()) != null);
        }
        out.close();
        System.out.println("!job finish!");
    }
}
