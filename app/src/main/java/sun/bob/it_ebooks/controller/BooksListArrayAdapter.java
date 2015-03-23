package sun.bob.it_ebooks.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import sun.bob.it_ebooks.R;
import sun.bob.it_ebooks.model.BookInfo;
import sun.bob.it_ebooks.model.BooksList;

/**
 * Created by sunkuan on 15/3/4.
 */
public class BooksListArrayAdapter extends ArrayAdapter<BookInfo> {
    Context appContext;
    public BooksListArrayAdapter(Context context, int resource,ArrayList<BookInfo> arrayList) {
        super(context, resource,arrayList);
        appContext = context;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View retView;
        retView = ((Activity)appContext).getLayoutInflater().inflate(R.layout.book_item_view,null);
        BooksList list = BooksList.getStaticInstance(getContext());
        BookInfo bookInfo = list.getBookAt(position);

        ImageView imageView = (ImageView) retView.findViewById(R.id.id_book_cover);
        // Handler handler = new Handler();
        // Thread imgThread = new Thread(new ImageDownloadRunnable(handler,bookInfo,imageView));
        // imgThread.start();
        if(bookInfo.needDownloadCoverImage()){
          Handler handler = new Handler();
          Thread imgThread = new Thread(new ImageDownloadRunnable(getContext(),handler,bookInfo,imageView));
          imgThread.start();
        }else{
          imageView.setImageBitmap(bookInfo.getCoverImage());
        }

        TextView titleTextView = (TextView)retView.findViewById(R.id.id_text_title);
        titleTextView.setText(bookInfo.getTitile());
        TextView descTextView = (TextView)retView.findViewById(R.id.id_text_description);
        descTextView.setText(bookInfo.getShortDescription());

        return retView;
    }
}
