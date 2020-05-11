package com.example.searchmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Display;

import com.github.chrisbanes.photoview.PhotoView;

public class DetailActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        Intent in =getIntent();
        int val = in.getIntExtra("room", -1);

        int pic = getImg(val);
        PhotoView photoView = (PhotoView)findViewById(R.id.photo_view);
        scaleImg(photoView, pic);
    }

    private int getImg(int val)
    {
        switch(val)
        {
            case 136: return R.drawable.a136;
            case 137: return R.drawable.a137;
            case 138: return R.drawable.background;
            case 139: return R.drawable.a139;
            case 140: return R.drawable.a140;
            case 141: return R.drawable.background;
            case 142: return R.drawable.a142;

            case 237: return R.drawable.a237;
            case 238: return R.drawable.backsecond;
            case 239: return R.drawable.backsecond;
            case 240: return R.drawable.a240;
            case 241: return R.drawable.a241;
            case 242: return R.drawable.backsecond;
            case 243: return R.drawable.backsecond;
            case 244: return R.drawable.backsecond;
            case 245: return R.drawable.a245;
            case 246: return R.drawable.a246;
            case 247: return R.drawable.a247;
            case 248: return R.drawable.a248;

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
