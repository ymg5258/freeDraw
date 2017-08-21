package com.example.ymgymg.freedraw;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {


    ImageView iv;
    int startX;
    int startY;
    int stopX;
    int stopY;
    Canvas canvas;
    Paint p;
    Bitmap baseBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv= (ImageView) findViewById(R.id.iv);

        baseBitmap = Bitmap.createBitmap(700, 1000
                , Bitmap.Config.ARGB_8888);
        p=new Paint();
        p.setColor(Color.GREEN);
        p.setStrokeWidth(5);
        canvas=new Canvas();
        canvas.setBitmap(baseBitmap);
        canvas.drawColor(Color.WHITE);

        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        startX= (int) event.getX();
                        startY= (int) event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        stopX= (int) event.getX();
                        stopY= (int) event.getY();
                        canvas.drawLine(startX,startY,stopX,stopY,p);
                        iv.setImageBitmap(baseBitmap);
                        startX=stopX;
                        startY=stopY;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    public void save(View view){
        try {
            File file=new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis()+".jpg");
            FileOutputStream fos=new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
            Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,"保存失败",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
