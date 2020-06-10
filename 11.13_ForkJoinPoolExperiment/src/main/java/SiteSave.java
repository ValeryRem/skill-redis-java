import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SiteSave extends Thread {
    String page;
    String siteDir;

    public SiteSave(String dir, String site) throws Exception {
        this.siteDir = dir;
        String sitee = "http://" + site;
        this.page = pageSave(sitee);
        savePageToFile(page, dir, site);
    }

    public void run() {
//        urlFromHtmlFile();
        try {
            urlFromHtmlFile(siteDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Доставание в строке Htmp файла
    public String pageSave(String userUrl) throws Exception {
        URL url = new URL(userUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        String page = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            char[] buf = new char[1024];
            int r;
            StringBuilder sb = new StringBuilder();
            do {
                if ((r = br.read(buf)) > 0)
                    sb.append(new String(buf, 0, r));
            } while (r >= 0);
            page = sb.toString();
        } finally {
            http.disconnect();
        }
        return page;
    }

    // Сохранение Страницы сайта в файл
    public void savePageToFile(String page, String dir, String site) {
        FileWriter writeFile = null;
        StringBuilder sb = new StringBuilder();
        sb.append(dir).append(site).append(".html");
        System.out.println("Html saved at " + sb.toString());
        try {
            File logFile = new File(sb.toString());
            writeFile = new FileWriter(logFile);
            writeFile.write(page);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writeFile != null) {
                try {
                    writeFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Вывод всех ссылок с Html страницы
    public void urlFromString(String site) {
        System.out.println();
        String sitee = "http://" + site;
        System.out.println("Urls and tags from site: " + sitee);
        try {
            Document doc = Jsoup.connect(sitee).get();
            Elements anchors = doc.select("a");
            for(Element e : anchors) {
                if(e.hasAttr("href"))
                    System.out.printf("%s\n", e.attr("href"));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    // Метод читания ссылок из Html Файла
    public void urlFromHtmlFile(String fileDir) throws Exception {
        System.out.println();
        System.out.println("All urls from file: " + fileDir);
        String p = "";
        BufferedReader br = new BufferedReader(new FileReader(fileDir));
        String str;
        while ((str = br.readLine()) != null) {
            p += str;
        }
        br.close();
        try {
            Document doc = Jsoup.parse(p);
            Elements anchors = doc.select("a");
            for(Element e : anchors) {
                if(e.hasAttr("href"))
                    System.out.printf("%s\n", e.attr("href"));
            }
        } catch(Exception e) {

        }
    }
}