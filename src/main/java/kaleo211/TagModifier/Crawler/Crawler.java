package kaleo211.TagModifier.Crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.json.JSONArray;
import org.json.JSONObject;

public class Crawler {

    HttpClient httpClient = null;

    public final static String song_url = "http://music.163.com/#/song?id=";

    public Crawler() {
        httpClient = HttpClients.createDefault();
    }

    public Mp4Tag crawl(String name) throws ClientProtocolException, IOException {
        Mp4Tag tag = new Mp4Tag();


        HttpPost search_url = new HttpPost("http://music.163.com/api/search/pc");

        // Add Post Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("csrf_token", null));
        params.add(new BasicNameValuePair("s", name));
//        params.add(new BasicNameValuePair("hlpretag", "<span class=\"s-fc7\">"));
//        params.add(new BasicNameValuePair("hlposttag", "</span>"));
        params.add(new BasicNameValuePair("type", "1"));
        params.add(new BasicNameValuePair("offset", "0"));
//        params.add(new BasicNameValuePair("total", "true"));
        params.add(new BasicNameValuePair("limit", "30"));

        search_url.setEntity(new UrlEncodedFormEntity(params));
        search_url.addHeader("Referer", "http://music.163.com/");

        HttpResponse response = httpClient.execute(search_url);

        System.out.println(response.getStatusLine());

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

        int song_id = songs.getJSONObject(0).getInt("id");

        HttpGet song_get = new HttpGet(song_url+song_id);
        HttpResponse r = httpClient.execute(song_get);

        InputStream is = r.getEntity().getContent();

        BufferedReader br1 = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
        result = "";
        while ((line = br1.readLine()) != null) {
            result += line;
        }



        System.out.println("size: "+result);

        return tag;
    }
}
