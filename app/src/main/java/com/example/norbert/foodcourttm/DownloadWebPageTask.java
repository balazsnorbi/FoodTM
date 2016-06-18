package com.example.norbert.foodcourttm;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Norbert on 6/18/2016.
 * <p/>
 * Uses AsyncTask to create a task away from the main UI thread. This task takes a
 * URL string and uses it to create an HttpUrlConnection. Once the connection
 * has been established, the AsyncTask downloads the contents of the webpage as
 * an InputStream. Finally, the InputStream is converted into a string, which is
 * displayed in the UI by the AsyncTask's onPostExecute method.
 */
public class DownloadWebPageTask extends AsyncTask<String, Void, Document> {

    private TextView textView;

    public DownloadWebPageTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected Document doInBackground(String... urls) {
        Document document = null;
        try {
            document = Jsoup.connect(urls[0]).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(Document document) {
        Element listItems = document.select("ul").get(0);
        Elements restaurants = listItems.select("li");
    }
}