package com.text.tips;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.*;
import android.widget.AdapterView;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelist);
        //TextView textshowview = ( TextView )findViewById(R.id.textshow);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
            List <String> list = new ArrayList<String>();
            for (File f :
                    filelist) {
                if (!f.isDirectory())
                {
                   // str[i] = f.toString();
                    //i = i + 1;
                   list.add(f.toString());
                }
            }
            ListView filelist1 = (ListView) findViewById(R.id.filelist);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, /*str*/ list);
            filelist1.setAdapter(adapter);

            filelist1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File openedtext = new File(  parent.getItemAtPosition(position).toString());
                    if( openedtext.isDirectory())
                    {
                        Toast.makeText(filelist.this,"打开的是目录",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(filelist.this, openedtext.toString(), Toast.LENGTH_LONG).show();
                        TextView file = (TextView)findViewById(R.id.showstatusname);
                        try {
                            OutputStream historywrite = openFileOutput("history.txt",MODE_PRIVATE);
                            historywrite.write((openedtext.toString() + "_" + "0"+"_GBK").getBytes());
                            Toast.makeText(filelist.this,"ok",Toast.LENGTH_SHORT).show();
                            historywrite.close();
                            finish();
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(filelist.this,"打开失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }

    }

}
