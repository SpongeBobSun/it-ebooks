package sun.bob.it_ebooks.controller;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sun.bob.it_ebooks.R;
import sun.bob.it_ebooks.model.BookInfo;

/**
 * Created by bob.sun on 2015/3/20.
 */
public class BookDetailRunnable implements Runnable {
    private Handler handler;
    private View view;
    private BookInfo bookDetail;
    private Context appContext;
    public BookDetailRunnable(Context context,Handler handler,View view){
        this.handler = handler;
        this.view = view;
        appContext = context;
    }
    public void setBookDetail(BookInfo bookDetail){
        this.bookDetail = bookDetail;
    }
    @Override
    public void run() {
        if((bookDetail == null)|(view == null)|(bookDetail == null))  return;

        TextView descriptionTextView = (TextView) view.findViewById(R.id.id_textview_book_description);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_imageview_bookinfo);
        descriptionTextView.setText(bookDetail.getFullDescription());
        if(bookDetail.needDownloadCoverImage()){
            Thread imgThread = new Thread(new ImageDownloadRunnable(appContext,handler,bookDetail,imageView));
            imgThread.start();
        }else{
            imageView.setImageBitmap(bookDetail.getCoverImage());
        }
        TextView publisherTextView = (TextView) view.findViewById(R.id.id_textview_book_publisher);
        publisherTextView.setText(bookDetail.getPublisher());
        TextView authorTextView = (TextView) view.findViewById(R.id.id_textview_book_author);
        authorTextView.setText(bookDetail.getAuthor());
    }
    public BookInfo getBookDetail(){
        return bookDetail;
    }
}
