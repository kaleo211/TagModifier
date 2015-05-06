package kaleo211.TagModifier;

import kaleo211.TagModifier.Crawler.Crawler;

import org.jaudiotagger.tag.mp4.Mp4Tag;


public class App {
    public static void main( String[] args ) throws Exception {

        M4aFile file = new M4aFile("/Users/kaleo211/Desktop/夜空中最亮的星.m4a");
        Mp4Tag t = file.getTag();

        Crawler crawler = new Crawler();
        System.out.println(file.getName());
        crawler.crawl(file.getName(), t);

        file.setTag(t);


//        List<M4aFile> files = new ArrayList<M4aFile>();
//        Files.walk(Paths.get("/Users/kaleo211/Desktop")).forEach(path -> {
//            if (M4aFile.isValid(path.toString())) {
//                try {
//                    files.add(new M4aFile(new File(path.toString())));
//                    System.out.println(path.toString());
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        });

        System.exit(1);
    }
}
