package com.example.android.roomwordssample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

class DataCoverter {
    public static byte[] converImage(ImageView bitmap){
        Bitmap bitmap1=((BitmapDrawable)bitmap.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG,0,stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }
    public static  Bitmap convertImage1(byte[] array){
        return BitmapFactory.decodeByteArray(array,0,array.length);
    }
}
