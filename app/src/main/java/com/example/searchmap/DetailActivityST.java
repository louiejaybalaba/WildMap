package com.example.searchmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Display;

import com.github.chrisbanes.photoview.PhotoView;

public class DetailActivityST extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_st);

        Intent in = getIntent();
        String value = in.getStringExtra("room");

        int pic = getImg(value);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        scaleImg(photoView, pic);
    }

    private int getImg(String value)
    {
        switch (value)
        {
            case "ST101": return R.drawable.st101;
            case "ST102": return R.drawable.st102;
            case "ST103": return R.drawable.st103;
            case "ST104": return R.drawable.st104;
            case "ST105": return R.drawable.st105;
            case "ST106": return R.drawable.st106;
            case "ST107": return R.drawable.st107;
            case "ST108": return R.drawable.st108;

            case "ST201": return R.drawable.st201;
            case "ST202": return R.drawable.st202;
            case "ST203": return R.drawable.st203;
            case "ST204": return R.drawable.st204;
            case "ST205": return R.drawable.st205;
            case "ST206": return R.drawable.st206;
            case "ST207": return R.drawable.st207;
            case "ST208": return R.drawable.st208;

            case "ST301": return R.drawable.st301;
            case "ST302": return R.drawable.st302;
            case "ST303": return R.drawable.st303;
            case "ST304": return R.drawable.st304;
            case "ST305": return R.drawable.st305;
            case "ST306": return R.drawable.st306;
            case "ST307": return R.drawable.st307;
            case "ST308": return R.drawable.st308;
            case "ST309": return R.drawable.st309;
            case "ST310": return R.drawable.st310;

            case "mground": return R.drawable.mground;
            case "msecond": return R.drawable.msecond;
            case "mthird": return R.drawable.mthird;
            case "mfourth": return R.drawable.mfourth;

            case "stground": return R.drawable.stground;
            case "stsecond": return R.drawable.stsecond;
            case "stthird": return R.drawable.stthird;
            case "stfourth": return R.drawable.stfourth;

            case "background": return R.drawable.background;
            case "backsecond": return R.drawable.backsecond;

            case "engground": return R.drawable.eground;
            case "engsecond": return R.drawable.esecond;
            case "engthird": return R.drawable.ethird;
            case "engfourth": return R.drawable.efourth;

            default: return -1;
        }
    }

    private void scaleImg(PhotoView photoView, int pic)
    {
        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();

        if(imgWidth > screenWidth)
        {
            int ratio = Math.round((float)imgWidth / (float)screenWidth);
            options.inSampleSize = ratio;
        }

        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        photoView.setImageBitmap(scaledImg);
    }
}
