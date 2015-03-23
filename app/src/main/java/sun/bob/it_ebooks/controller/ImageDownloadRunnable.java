package sun.bob.it_ebooks.controller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import sun.bob.it_ebooks.MainListActivity;
import sun.bob.it_ebooks.R;
import sun.bob.it_ebooks.model.BookInfo;

/**
 * Created by sunkuan on 15/3/19.
 */
public class ImageDownloadRunnable implements Runnable {
    private Handler handler;
    private BookInfo bookInfo;
    private ImageView imageView;
    private Bitmap bmp;
    private Context appContext;
    public ImageDownloadRunnable(Context context,Handler handler,BookInfo bookInfo, ImageView imageView){
        this.handler = handler;
        this.imageView = imageView;
        this.bookInfo = bookInfo;
        appContext = context;
    }

    @Override
    public void run() {
        if(bookInfo.getCoverImageUrl().length() == 0){
            bmp = BitmapFactory.decodeResource(appContext.getResources(),R.drawable.empty_image);
        }else {
            try {
                URL url = new URL(bookInfo.getCoverImageUrl());
                URLConnection urlConnection = url.openConnection();
                urlConnection.setRequestProperty("accept", "*/*");
                urlConnection.setRequestProperty("connection", "Keep-Alive");
                urlConnection.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                urlConnection.connect();
                urlConnection.getHeaderFields();

                bmp = BitmapFactory.decodeStream(urlConnection.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bmp);
            }
        });
        String fileNamePrefix = "/data/data/sun.bob.it_ebooks/";
        FileOutputStream fos = null;
        try {
            File f = new File(fileNamePrefix+"imgcache/"+bookInfo.getTitile());
            if(!f.exists()){
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            f = null;
            fos = new FileOutputStream(fileNamePrefix+"imgcache/"+bookInfo.getTitile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bmp.compress(Bitmap.CompressFormat.PNG, 80, fos);
        bookInfo.setCacheImageFileName(fileNamePrefix+"imgcache/"+bookInfo.getTitile());
        bookInfo.getCoverImage();
        //Todo
        //This may cause an exception.
    }
}
