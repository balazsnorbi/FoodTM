package com.example.norbert.foodcourttm;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Norbert on 6/18/2016.
 * <p/>
 * Uses AsyncTask to create a task away from the main UI thread. This task takes a
 * URL string and uses it to create an HttpUrlConnection. Once the connection
 * has been established, the AsyncTask downloads the contents of the webpage as
 * an InputStream. Finally, the InputStream is converted into a string, which is
 * displayed in the UI by the AsyncTask's onPostExecute method.
 */
public class DownloadWebPageTask extends AsyncTask<String, Void, String> {

    private TextView textView;

    public DownloadWebPageTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("DownloadWebPageTask", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            return readIt(is);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    private String readIt(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[100];
        StringBuilder stringBuilder = new StringBuilder();
        while (reader.read(buffer) > 0) {
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }
}