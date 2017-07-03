package com.example.myserialtest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@SuppressLint({"SdCardPath"})
public class UsbPrintPic {
    private Canvas canvas = null;
    private Paint paint = null;
    private Bitmap bm = null;
    private int width;
    private float length = 0.0F;
    private byte[] bitbuf = null;

    public int getLength()
    {
        return (int)this.length;
    }

    public int getWidth()
    {
        return this.width;
    }

    public void initCanvas(int w)
    {
        int h = 10 * w;
        this.bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        this.canvas = new Canvas(this.bm);
        this.canvas.drawColor(-1);
        this.width = w;
        this.bitbuf = new byte[this.width / 8];
    }

    public void initPaint()
    {
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setColor(Color.BLACK);
        this.paint.setStyle(Paint.Style.STROKE);
    }

    public void drawImage(float x, float y, String path)
    {
        try
        {
            Bitmap btm = BitmapFactory.decodeFile(path);
            this.canvas.drawBitmap(btm, x, y, null);
            if (this.length < y + btm.getHeight())
                this.length = (y + btm.getHeight());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printPng()
    {
        File f = new File("/mnt/sdcard/0.png");
        FileOutputStream fos = null;
        Bitmap nbm = Bitmap.createBitmap(this.bm, 0, 0, this.width,
                getLength());
        try {
            fos = new FileOutputStream(f);
            nbm.compress(Bitmap.CompressFormat.PNG, 50, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] printDraw()
    {
        if (getLength() == 0) {
            return null;
        }
        Bitmap nbm = Bitmap.createBitmap(this.bm, 0, 0, this.width,
                getLength());
        byte[] imgbuf = new byte[this.width / 8 * getLength()];
        int s = 0;

        for (int i = 0; i < getLength(); i++) {
            for (int k = 0; k < this.width / 8; k++)
            {
                int c0 = nbm.getPixel(k * 8 + 0, i);
                int p0;
                if (c0 == -1)
                    p0 = 0;
                else {
                    p0 = 1;
                }
                int c1 = nbm.getPixel(k * 8 + 1, i);
                int p1;
                if (c1 == -1)
                    p1 = 0;
                else {
                    p1 = 1;
                }
                int c2 = nbm.getPixel(k * 8 + 2, i);
                int p2;
                if (c2 == -1)
                    p2 = 0;
                else {
                    p2 = 1;
                }
                int c3 = nbm.getPixel(k * 8 + 3, i);
                int p3;
                if (c3 == -1)
                    p3 = 0;
                else {
                    p3 = 1;
                }
                int c4 = nbm.getPixel(k * 8 + 4, i);
                int p4;
                if (c4 == -1)
                    p4 = 0;
                else {
                    p4 = 1;
                }
                int c5 = nbm.getPixel(k * 8 + 5, i);
                int p5;
                if (c5 == -1)
                    p5 = 0;
                else {
                    p5 = 1;
                }
                int c6 = nbm.getPixel(k * 8 + 6, i);
                int p6;
                if (c6 == -1)
                    p6 = 0;
                else {
                    p6 = 1;
                }
                int c7 = nbm.getPixel(k * 8 + 7, i);
                int p7;
                if (c7 == -1)
                    p7 = 0;
                else {
                    p7 = 1;
                }
                int value = p0 * 128 + p1 * 64 + p2 * 32 + p3 * 16 + p4 * 8 +
                        p5 * 4 + p6 * 2 + p7;
                this.bitbuf[k] = ((byte)value);
            }

            for (int t = 0; t < this.width / 8; t++) {
                imgbuf[s] = this.bitbuf[t];
                s++;
            }
        }
        return imgbuf;
    }
}
