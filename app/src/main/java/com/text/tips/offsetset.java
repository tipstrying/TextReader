package com.text.tips;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;
import android.support.v7.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tips on 2016/9/12.
 */
public class offsetset extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.offsetset);

        try
        {
            InputStream fin = openFileInput("history.txt");
            byte buf[] = new byte[fin.available()];
            fin.read(buf);

            String strbuf = new String(buf);
            String strl[] = strbuf.split("_");
            if (strl.length > 1) {
                if( strl.length > 2)
                {
                    EditText encode = (EditText)findViewById(R.id.oldencode);
                    encode.setText(strl[2]);
                }
                else
                {
                    EditText encode = (EditText)findViewById(R.id.oldencode);
                    encode.setText("UTF-8");
                }
                EditText oldedit = (EditText) findViewById(R.id.OldOffset);
                oldedit.setText((strl[1]));
            }
            else
            {
                Toast.makeText(offsetset.this,"旧偏移量读取失败",Toast.LENGTH_SHORT).show();
            }
            fin.close();
        }
        catch (IOException e)
        {
            Toast.makeText(offsetset.this,"旧偏移量读取失败",Toast.LENGTH_SHORT).show();
            e.toString();
            finish();
        }
    }



    public void okclick (View view)
    {
        try
        {
            InputStream fin = openFileInput("history.txt");
            byte buf[] = new byte[fin.available()];
            fin.read(buf);
            fin.close();
            String strbuf = new String(buf);
            String strl[] = strbuf.split("_");
          //  if (strl.length > 1) {
            EditText oldedit = (EditText) findViewById(R.id.OldOffset);
            oldedit.setText(strl[1]);

          //  }
            /*
            else
            {
                Toast.makeText(offsetset.this,"旧偏移量读取失败",Toast.LENGTH_SHORT).show();
            }
            */
            EditText editText = (EditText)findViewById(R.id.NewOffset);
            EditText encode = (EditText)findViewById(R.id.newencoding);
            String tmp = editText.getText().toString();
            if(tmp.isEmpty() && encode.getText().toString().isEmpty())
            {
                Toast.makeText(offsetset.this,"未修改",Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                try {

                    OutputStream fou = openFileOutput("history.txt", MODE_PRIVATE);

                    EditText oldencode = (EditText)findViewById(R.id.oldencode);
                   // EditText newencole = (EditText)findViewById(R.id.newencoding);
                    if(!encode.getText().toString().isEmpty()) {
                        /*
                        fou.write((strl[0] + "_" + editText.getText().toString() + "_" + encode.getText().toString()).getBytes());

                        Toast.makeText(offsetset.this, editText.getText().toString(), Toast.LENGTH_LONG).show();
                        */
                        strl[2] = encode.getText().toString();
                    }
                    /*
                    else
                    {
                        fou.write((strl[0] + "_" + editText.getText().toString() + "_" + oldencode.getText().toString()).getBytes());
                    }
                    */
                    if(!editText.getText().toString().isEmpty())
                    {
                        if(editText.getText().toString().length() < 10) {
                            strl[1] = editText.getText().toString();
                        }
                    }
                    fou.write( (strl[0] + "_" + strl[1] + "_" + strl[2] ).getBytes() );
                       fou.close();
                } catch (IOException err) {
                    err.toString();
                    finish();
                }
            }

        }
        catch (IOException e)
        {
            e.toString();
        }
        finish();
    }
   public void Cancel(View view)
   {
       Toast.makeText(offsetset.this,"放弃修改",Toast.LENGTH_SHORT).show();
       finish();
   }
    public void encodingok(View view)
    {
        TextView encoding = (TextView)findViewById(R.id.encoding);
        EditText editText = (EditText)findViewById(R.id.newencoding);
        String tmp =  new String( editText.getText().toString());
        if(tmp.isEmpty())
        {
            finish();
        }
        else {
          //  encoding.setText(tmp);
            try
            {
                InputStream fin = openFileInput("history.txt");
                byte buf[] = new byte[fin.available()];
                fin.read(buf);
                fin.close();
                String strbuf = new String(buf);
                String strl[] = strbuf.split("_");
                String tmps[] = new String[3];
                if(strl.length > 2)
                {
                    strl[2] = tmp;
                }
                else
                {

                    tmps[0] = strl[0];
                    tmps[1] = strl[1];
                    tmps[2] = tmp;
                }
                try {
                    OutputStream fou = openFileOutput("history.txt", MODE_PRIVATE);

                    fou.write((tmps[0] + "_" + tmps[1] + "_" + tmps[2]).getBytes());


                       fou.close();
                } catch (IOException err) {
                    err.toString();
                }

            }
            catch (IOException e)
            {
                e.toString();
            }
            Toast.makeText(offsetset.this,tmp,Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}
