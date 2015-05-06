package kaleo211.TagModifier.Crawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jaudiotagger.tag.mp4.Mp4FieldKey;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.json.JSONArray;
import org.json.JSONObject;

public class Crawler {

    HttpClient httpClient;

    private final static String song_detail = "http://music.163.com/api/song/detail/?id=song_id&ids=[song_id]";
    private final static String search = "http://music.163.com/api/search/pc";

    public Crawler() {
        httpClient = HttpClients.createDefault();
    }

    private String getSongID(String name) throws Exception {
        HttpPost search_post = new HttpPost(search);

        // Add Post Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("s", name));
        params.add(new BasicNameValuePair("limit", "30"));
        params.add(new BasicNameValuePair("type", "1"));
        params.add(new BasicNameValuePair("offset", "0"));
//        params.add(new BasicNameValuePair("hlpretag", "<span class=\"s-fc7\">"));
//        params.add(new BasicNameValuePair("hlposttag", "</span>"));
//        params.add(new BasicNameValuePair("total", "true"));
//        params.add(new BasicNameValuePair("csrf_token", null));

        search_post.setEntity(new UrlEncodedFormEntity(params));
        search_post.addHeader("Referer", "http://music.163.com/");

        HttpResponse response = httpClient.execute(search_post);

        // Save the response as JSON
        HttpEntity entity = response.getEntity();
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
        String line, result="";
        while ((line = br.readLine()) != null) {
            result += line;
        }
        System.out.println(result);
        JSONObject obj = new JSONObject(result);
        JSONArray songs = obj.getJSONObject("result").getJSONArray("songs");

        String id = String.valueOf(songs.getJSONObject(0).getInt("id"));

        return id;
    }


    public Mp4Tag crawl(String name, Mp4Tag tag) throws Exception {
        String song_id = getSongID(name);

        HttpGet song_get = new HttpGet(song_detail.replaceAll("song_id", song_id));
        System.out.println(song_get.getURI());

        HttpResponse response = httpClient.execute(song_get);

        HttpEntity entity = response.getEntity();
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
        String result = "", line;
        while ((line = br.readLine()) != null) {
            result += line;
        }
        JSONObject obj = new JSONObject(result);
        JSONObject song = obj.getJSONArray("songs").getJSONObject(0);
        String song_name = song.getString("name");
        String song_artist = song.getJSONArray("artists").getJSONObject(0).getString("name");
        String song_album = song.getJSONObject("album").getString("name");

        System.out.println("name: "+song_name+" artist: "+song_artist+" album: "+song_album);
        tag.setField(tag.createField(Mp4FieldKey.ARTIST, song_artist));
        tag.setField(tag.createField(Mp4FieldKey.ALBUM, song_album));
        tag.setField(tag.createField(Mp4FieldKey.TITLE, song_name));
        return tag;
    }
}
