package com.example.searchmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Display;

import com.github.chrisbanes.photoview.PhotoView;

public class DetailActivityE_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_e_3);

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
            case "E301": return R.drawable.ethird;
            case "E302": return R.drawable.ethird;
            case "E303": return R.drawable.ethird;
            case "E304": return R.drawable.e304;
            case "E305": return R.drawable.e305;
            case "E306": return R.drawable.e306;
            case "E307": return R.drawable.e307;
            case "E308": return R.drawable.e308;
            case "E309": return R.drawable.e309;
            case "E310": return R.drawable.e310;
            case "E311": return R.drawable.e311;

            case "E401": return R.drawable.e401;
            case "E402": return R.drawable.efourth;
            case "E403": return R.drawable.efourth;
            case "E404": return R.drawable.e404;
            case "E405": return R.drawable.e405;
            case "E406": return R.drawable.e406;
            case "E407": return R.drawable.e407;
            case "E408": return R.drawable.e408;

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
