package com.example.searchmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Display;

import com.github.chrisbanes.photoview.PhotoView;

public class DetailActivityE_4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_e_4);

        Intent in =getIntent();

        int pic = getImg();
        PhotoView photoView = (PhotoView)findViewById(R.id.photo_view);
        scaleImg(photoView, pic);
    }

    private int getImg()
    {
        return R.drawable.efourth;
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
