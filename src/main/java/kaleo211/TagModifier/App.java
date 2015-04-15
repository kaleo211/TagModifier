package kaleo211.TagModifier;

import java.io.IOException;

import kaleo211.TagModifier.Crawler.Crawler;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;


public class App {
    public static void main( String[] args ) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {


        Crawler crawler = new Crawler();
        crawler.crawl("Burn");





//        M4aFile file = new M4aFile("/Users/kaleo211/Desktop/夜空中最亮的星.m4a");
//        Mp4Tag tag = file.getTag();
//        tag.setField(FieldKey.ARTIST, "zhou");
//        file.setTag(tag);

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
    }
}
