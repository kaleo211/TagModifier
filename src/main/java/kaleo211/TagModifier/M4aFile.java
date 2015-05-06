package kaleo211.TagModifier;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.mp4.Mp4Tag;

public class M4aFile {

    private AudioFile audio_file = null;
    private Mp4Tag tag = null;
    private String file_name = null;

    private static final Pattern pattern = Pattern.compile("(.+)\\.m4a");

    // Constructors
    public M4aFile(String path) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        this(new File(path));
    }

    public M4aFile(File file) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        Matcher m = pattern.matcher(file.getName());
        if (m.find()) {
            file_name = m.group(1);
            audio_file = AudioFileIO.read(file);
            tag = (Mp4Tag)audio_file.getTag();
        } else {
            throw new CannotReadException();
        }
    }

    // Static Methods
    public static boolean isValid(String file_name) {
        if (pattern.matcher(file_name.toLowerCase()).find()) {
            return true;
        }
        return false;
    }

    // Getters & Setters
    public String getName() {
        return file_name;
    }

    public Mp4Tag getTag() {
        return tag;
    }

    public void setTag(Mp4Tag tag) throws CannotWriteException {
        this.tag = tag;
        audio_file.commit();
    }
}
