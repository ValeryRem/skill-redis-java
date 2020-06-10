public class Main {

    public static void main(String[] args) throws Exception {
        // Ссылка на директорию в которую надо записать страницу сайта
        String dir = "C:/Users/valery/Desktop/java_basics/11.13_ForkJoinPoolExperiment/src/main/java/result/";

        //Ссылка на страницу сайт
        String site = "secure-headland-59304.herokuapp.com";

        // Ссылка на Html файл
        String file = "https://secure-headland-59304.herokuapp.com/";

        // Сохранение страницы сайта в указаную папку
        SiteSave prog = new SiteSave(dir, site);

        // Вывод всех ссылок из Html файла
//        prog.urlFromHtmlFile(file);
        prog.start();

//        prog.urlFromString(site);
    }
}