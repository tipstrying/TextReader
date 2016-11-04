package com.text.tips;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.util.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class mainReader extends AppCompatActivity {

    long offsetread = 0;
    String fileread = "NONE";
    int threammode = 0;
    int Lines = 40;
    List<Long> offsethistory = new ArrayList<Long>();
    String ENCOD = "UTF-8";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public long filetotextshow(String filename, long offset, int line, TextView view, String encod) {
        File fi = new File(filename);

        long offsetbakl = 0;
        long offsettmp;
        offsettmp = 0;
        byte buf[] = new byte[10000];
        try {
            InputStream fis = new FileInputStream(fi);
            fis.skip(0);
            if (offset > fis.available()) {
                offset = fis.available();

            }
            fis.skip(offset);
            for (int i = 0; i < line; ) {

                buf[((int) offsettmp)] = (byte) fis.read();
                if (buf[((int) offsettmp)] == -1)
                    break;
                if (buf[((int) offsettmp)] == (byte) 10) {
                    i++;
                }
                offsettmp++;
                if (i == line - 2) {
                    offsetbakl = offset + offsettmp;
                }

            }
            TextView all = (TextView) findViewById(R.id.allnumber);
            assert all != null;
            all.setText(String.valueOf(fis.available()));
            fis.close();
            String tmp = "";
            tmp = EncodingUtils.getString(buf, encod);
            // view.setText(new String(buf));
            view.setText(tmp);
            ScrollView textshowscroll = (ScrollView) findViewById(R.id.scrollView);
            assert textshowscroll != null;
            textshowscroll.fullScroll(ScrollView.FOCUS_UP);
        } catch (IOException e) {
            view.setText(e.toString());
            return 0;
        }
        return offsetbakl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_reader);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        TextView textshowview = (TextView) findViewById(R.id.textshow);
        try {

            InputStream historyread = openFileInput("history.txt");

            //  Toast.makeText(mainReader.this,"history open ok",Toast.LENGTH_SHORT).show();
            byte buf[] = new byte[historyread.available()];
            historyread.read(buf);
            historyread.close();
            fileread = new String(buf);
            fileread = fileread.replace(" ", "");
            fileread = fileread.replace("\n", "");
            if (!fileread.isEmpty()) {
                String strtmp[] = fileread.split("_");
                if (strtmp.length > 1) {
                    fileread = strtmp[0];
                    offsetread = Integer.valueOf(strtmp[1]);
                    TextView filename = (TextView) findViewById(R.id.showstatusname);
                    assert filename != null;
                    filename.setText(fileread);


                    TextView off = (TextView) findViewById(R.id.offsetnum);
                    assert off != null;
                    off.setText(String.valueOf(offsetread));
                    TextView encod = (TextView) findViewById(R.id.encoding);
                    if (strtmp.length > 2) {
                        String strin = strtmp[2];
                        if (strin.isEmpty()) {
                            ENCOD = "UTF-8";
                        } else {
                            ENCOD = strin;
                        }
                    } else {
                        ENCOD = "UTF-8";
                    }
                    //  offset[offsetp] = offsetread;
                    offsethistory.add(offsetread);

                    offsetread = filetotextshow(fileread, offsetread, Lines, textshowview, ENCOD);
                } else if (strtmp.length > 0) {
                    fileread = strtmp[0];
                    TextView filename = (TextView) findViewById(R.id.showstatusname);
                    assert filename != null;
                    filename.setText(fileread);
                    TextView off = (TextView) findViewById(R.id.offsetnum);
                    assert off != null;
                    off.setText(String.valueOf(0));

                } else {
                    assert textshowview != null;
                    textshowview.setText("没有查看历史，请手动打开新TXT");
                }
            }


        } catch (IOException e) {
            assert textshowview != null;
            textshowview.setText("没有查看历史，请手动打开新TXT");
        }
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        assert scrollView != null;
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float mLastY = scrollView.getScrollY();
                    if (mLastY == scrollView.getChildAt(0).getMeasuredHeight() - scrollView.getHeight()) {
                        //  Toast.makeText(mainReader.this,"到底啦",Toast.LENGTH_SHORT).show();
                        Button button = (Button) findViewById(R.id.nextpage);
                        nextpage(button);
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                        return true;

                    } else if (mLastY == 0) {
                        Button button = (Button) findViewById(R.id.beforpage);
                        beforpage(button);
                        scrollView.fullScroll(ScrollView.FOCUS_UP);

                    }
                }
                return false;
            }

        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public int settheam(int mode) {

        LinearLayout background = (LinearLayout) findViewById(R.id.linebackground);
        TextView textshow = (TextView) findViewById(R.id.textshow);
        TextView textoffset = (TextView) findViewById(R.id.offsettext);
        TextView offsetnumber = (TextView) findViewById(R.id.offsetnum);
        TextView alltext = (TextView) findViewById(R.id.alltext);
        TextView allnumber = (TextView) findViewById(R.id.allnumber);
        TextView textstatus = (TextView) findViewById(R.id.showstatustext);
        TextView textname = (TextView) findViewById(R.id.showstatusname);
        Button nextpage = (Button) findViewById(R.id.nextpage);
        Button beforpage = (Button) findViewById(R.id.beforpage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        switch (mode) {
            case 0:

                assert background != null;
                background.setBackgroundColor(Color.rgb(0, 0, 0));
                assert toolbar != null;
                toolbar.setBackgroundColor(Color.rgb(0, 0, 0));
                assert textshow != null;
                textshow.setTextColor(Color.rgb(158, 158, 158));
                assert textoffset != null;
                textoffset.setTextColor(Color.rgb(158, 158, 158));
                assert offsetnumber != null;
                offsetnumber.setTextColor(Color.rgb(158, 158, 158));
                alltext.setTextColor(Color.rgb(158, 158, 158));
                allnumber.setTextColor(Color.rgb(158, 158, 158));
                textstatus.setTextColor(Color.rgb(158, 158, 158));
                textname.setTextColor(Color.rgb(158, 158, 158));
                nextpage.setTextColor(Color.rgb(158, 158, 158));
                beforpage.setTextColor(Color.rgb(158, 158, 158));
                nextpage.setBackgroundColor(Color.rgb(0, 0, 0));
                beforpage.setBackgroundColor(Color.rgb(0, 0, 0));
                // mode = 1;
                return mode;
            case 1:

                background.setBackgroundColor(Color.rgb(255, 255, 255));
                toolbar.setBackgroundColor(Color.rgb(63, 81, 181));
                textshow.setTextColor(Color.rgb(102, 102, 102));
                textoffset.setTextColor(Color.rgb(102, 102, 102));
                offsetnumber.setTextColor(Color.rgb(102, 102, 102));
                alltext.setTextColor(Color.rgb(102, 102, 102));
                allnumber.setTextColor(Color.rgb(102, 102, 102));
                textstatus.setTextColor(Color.rgb(102, 102, 102));
                textname.setTextColor(Color.rgb(102, 102, 102));
                nextpage.setTextColor(Color.rgb(102, 102, 102));
                beforpage.setTextColor(Color.rgb(102, 102, 102));
                nextpage.setBackgroundColor(Color.rgb(255, 255, 255));
                beforpage.setBackgroundColor(Color.rgb(255, 255, 255));
                //   mode = 0;
                return mode;


        }

        return 2;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "mainReader Page", // TODO: Define a title for the content shown.
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_reader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.setmode:
                if (threammode == 0) {

                    threammode = settheam(threammode);
                    threammode = 1;
                } else {

                    threammode = settheam(threammode);
                    threammode = 0;
                }

                return true;
            case R.id.openfile:


                Intent intent = new Intent(this, filelist.class);

                int j = 1;
                startActivityForResult(intent, j);
                return true;
            case R.id.setoffset:
                Intent intent1 = new Intent(this, offsetset.class);
                //startActivity(intent1);
                int i = 1;
                startActivityForResult(intent1, i);
                return true;
            case R.id.Exit:
                finish();
                return true;
            default:
                return true;

        }
    }

    public void nextpage(View view) {
        TextView textshowview = (TextView) findViewById(R.id.textshow);

        try {
            OutputStream historywirte = openFileOutput("history.txt", MODE_PRIVATE);
            historywirte.write((fileread + "_" + String.valueOf(offsetread) + "_" + ENCOD.toString()).getBytes());
            historywirte.close();
        } catch (IOException e) {
            e.toString();
        }
        TextView name = (TextView) findViewById(R.id.showstatusname);
        name.setText(fileread);
        TextView off = (TextView) findViewById(R.id.offsetnum);
        off.setText(String.valueOf(offsetread));
        offsethistory.add(offsetread);
        offsetread = filetotextshow(fileread, offsetread, Lines, textshowview, ENCOD);
    }

    public void beforpage(View view) {
        //Toast.makeText(this,"befor",Toast.LENGTH_SHORT).show();
        TextView textshowview = (TextView) findViewById(R.id.textshow);
        if (offsethistory.size() > 1) {
            offsetread = offsethistory.get(offsethistory.size() - 2);
            offsethistory.remove(offsethistory.size() - 1);
            TextView name = (TextView) findViewById(R.id.showstatusname);
            assert name != null;
            name.setText(fileread);
            TextView off = (TextView) findViewById(R.id.offsetnum);
            assert off != null;
            off.setText(String.valueOf(offsetread));

            offsetread = filetotextshow(fileread, offsetread, Lines, textshowview, ENCOD);

        } else {
            Toast.makeText(this, "木有啦~~~~~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView textshowview = (TextView) findViewById(R.id.textshow);
        try {

            InputStream historyread = openFileInput("history.txt");

            // Toast.makeText(mainReader.this,"history open ok",Toast.LENGTH_SHORT).show();
            byte buf[] = new byte[historyread.available()];
            historyread.read(buf);
            historyread.close();
            fileread = new String(buf);
            fileread = fileread.replace(" ", "");
            fileread = fileread.replace("\n", "");
            if (!fileread.isEmpty()) {
                String strtmp[] = fileread.split("_");
                if (strtmp.length > 1) {
                    fileread = strtmp[0];
                    offsetread = Integer.valueOf(strtmp[1]);
                    TextView filename = (TextView) findViewById(R.id.showstatusname);
                    filename.setText(fileread);


                    TextView off = (TextView) findViewById(R.id.offsetnum);
                    off.setText(String.valueOf(offsetread));
                    TextView encod = (TextView) findViewById(R.id.encoding);
                    if (strtmp.length > 2) {
                        String strin = strtmp[2];
                        if (strin.isEmpty()) {
                            ENCOD = "UTF-8";
                        } else {
                            ENCOD = strin;
                        }
                    } else {
                        ENCOD = "UTF-8";
                    }
                    // offset[offsetp] = offsetread;
                    offsethistory.add(offsetread);
                    offsetread = filetotextshow(fileread, offsetread, Lines, textshowview, ENCOD);
                } else if (strtmp.length > 0) {
                    fileread = strtmp[0];
                    TextView filename = (TextView) findViewById(R.id.showstatusname);
                    filename.setText(fileread);
                    TextView off = (TextView) findViewById(R.id.offsetnum);
                    off.setText(String.valueOf(0));

                } else {
                    textshowview.setText("没有查看历史，请手动打开新TXT");
                }
            }


        } catch (IOException e) {
            textshowview.setText("没有查看历史，请手动打开新TXT");
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "mainReader Page", // TODO: Define a title for the content shown.
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
