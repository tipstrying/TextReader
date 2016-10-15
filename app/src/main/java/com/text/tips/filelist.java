package com.text.tips;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.*;
import android.widget.AdapterView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tips on 2016/9/12.
 */
public class filelist extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist);
        //TextView textshowview = ( TextView )findViewById(R.id.textshow);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        File strname = new File("/mnt/sdcard/book");
        if (strname.canRead()) {
            File filelist[] = strname.listFiles();
            /*
            int i = 0;
            for (File f:
                    filelist
                 ) {
                if(!f.isDirectory())
                {
                    i = i + 1;
                }

            }
            String str[] = new String[i];
            i = 0;
            for (File f :
                    filelist) {
                if (!f.isDirectory())
                {
                     str[i] = f.toString();
                    i = i + 1;
                }
            }
            */
            List<String> list = new ArrayList<String>();

            for (File f :
                    filelist) {
                if (!f.isDirectory()) {
                    // str[i] = f.toString();
                    //i = i + 1;
                    list.add(f.getName().toString());
                }
            }
            ListView filelist1 = (ListView) findViewById(R.id.filelist);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, /*str*/ list);
            filelist1.setAdapter(adapter);

            filelist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File openedtext = new File(parent.getItemAtPosition(position).toString());
                    if (openedtext.isDirectory()) {
                        Toast.makeText(filelist.this, "打开的是目录", Toast.LENGTH_SHORT).show();
                    } else {
                       // Toast.makeText(filelist.this, openedtext.toString(), Toast.LENGTH_LONG).show();
                        TextView file = (TextView) findViewById(R.id.showstatusname);
                        try {
                            OutputStream historywrite = openFileOutput("history.txt", MODE_PRIVATE);
                            historywrite.write(("/mnt/sdcard/book/" + openedtext.toString() + "_" + "0" + "_GBK").getBytes());
                            //Toast.makeText(filelist.this, "ok", Toast.LENGTH_SHORT).show();
                            historywrite.close();
                            finish();
                        } catch (IOException e) {
                            Toast.makeText(filelist.this, "打开失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "filelist Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.text.tips/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "filelist Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.text.tips/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
