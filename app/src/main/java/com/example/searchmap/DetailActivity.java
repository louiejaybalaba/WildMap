package com.example.searchmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;

import com.github.chrisbanes.photoview.PhotoView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent in = getIntent();
        int val = in.getIntExtra("room", -1);

        int pic = getImg(val);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        scaleImg(photoView, pic);

    }

    private int getImg(int val)
    {
        switch(val)
        {
            case 1: return R.drawable.mground;
            case 200: return R.drawable.m200;
            case 201: return R.drawable.m201;
            case 202: return R.drawable.m202;
            case 203: return R.drawable.m203;
            case 204: return R.drawable.m204;
            case 205: return R.drawable.msecond;
            case 206: return R.drawable.m206;
            case 207: return R.drawable.m207;
            case 208: return R.drawable.m208;
            case 209: return R.drawable.m209;
            case 210: return R.drawable.m210;
            case 211: return R.drawable.m211;
            case 212: return R.drawable.m212;
            case 213: return R.drawable.m213;
            case 214: return R.drawable.m214;
            case 215: return R.drawable.m215;
            case 216: return R.drawable.m216;
            case 217: return R.drawable.m217;
            case 218: return R.drawable.m218;
            case 219: return R.drawable.m219;
            case 220: return R.drawable.m220;
            case 222: return R.drawable.m222;
            case 224: return R.drawable.m224;
            case 226: return R.drawable.m226;
            case 227: return R.drawable.m227;
            case 228: return R.drawable.m228;
            case 229: return R.drawable.m229;
            case 230: return R.drawable.m230;
            case 231: return R.drawable.m231;


            case 300: return R.drawable.m300;
            case 301: return R.drawable.m301;
            case 302: return R.drawable.m302;
            case 303: return R.drawable.m303;
            case 304: return R.drawable.m304;
            case 306: return R.drawable.m306;
            case 307: return R.drawable.m307;
            case 308: return R.drawable.m308;
            case 309: return R.drawable.m309;
            case 310: return R.drawable.m310;
            case 311: return R.drawable.m311;
            case 312: return R.drawable.m312;
            case 313: return R.drawable.m313;
            case 314: return R.drawable.m314;
            case 315: return R.drawable.m315;
            case 316: return R.drawable.m316;
            case 317: return R.drawable.m317;
            case 318: return R.drawable.m318;
            case 319: return R.drawable.m319;
            case 320: return R.drawable.m320;
            case 321: return R.drawable.m321;
            case 322: return R.drawable.m322;
            case 323: return R.drawable.m323;
            case 324: return R.drawable.m324;
            case 326: return R.drawable.m326;
            case 328: return R.drawable.m328;
            case 329: return R.drawable.m329;
            case 330: return R.drawable.m330;
            case 331: return R.drawable.m331;

            case 136: return R.drawable.a136;
            case 137: return R.drawable.a137;
            case 139: return R.drawable.a139;
            case 140: return R.drawable.a140;

            case 237: return R.drawable.a237;
            case 240: return R.drawable.a240;
            case 241: return R.drawable.a241;
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
