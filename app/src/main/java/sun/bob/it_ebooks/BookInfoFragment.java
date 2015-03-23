package sun.bob.it_ebooks;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sun.bob.it_ebooks.controller.APIRunnable;
import sun.bob.it_ebooks.controller.BookDetailRunnable;

/**
 * Created by sunkuan on 15/3/20.
 */
public class BookInfoFragment extends Fragment {
    private String title;
    private String subTitle;
    private String description;
    private BookDetailRunnable bookDetailRunnable;
    private long bookID;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        title = getActivity().getIntent().getStringExtra("title");
        subTitle = getActivity().getIntent().getStringExtra("subtitle");
        description = getActivity().getIntent().getStringExtra("description");
        bookID = getActivity().getIntent().getLongExtra("bookid",0);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState){
        View retView = inflater.inflate(R.layout.bookinfo_fragment,parent,false);
        TextView titleTextView = (TextView) retView.findViewById(R.id.id_textview_book_title);
        titleTextView.setText(title);
        Handler handler = new Handler();
        bookDetailRunnable = new BookDetailRunnable(getActivity(),handler,retView);
        APIRunnable apiRunnable = new APIRunnable(handler,bookDetailRunnable);
        apiRunnable.runBookDetail(bookID);

        return retView;
    }
    public String getDownloadUrl(){
        return bookDetailRunnable.getBookDetail().getDownloadUrl();
    }
}
