package sun.bob.it_ebooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;


import sun.bob.it_ebooks.controller.APIRunnable;
import sun.bob.it_ebooks.controller.BooksListArrayAdapter;
import sun.bob.it_ebooks.model.BookInfo;
import sun.bob.it_ebooks.model.BooksList;

/**
 * Created by sunkuan on 15/3/4.
 */
public class MainListFragment extends ListFragment {
    BooksList booksList;
    APIRunnable apiThread;
    Handler handler;
    public int searchPageNumber;
    private String keyWord;
    View listFooterView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        handler = new Handler();
        searchPageNumber = 0;
        apiThread = new APIRunnable(this, handler);
        booksList = BooksList.getStaticInstance(this.getActivity());
        BooksListArrayAdapter arrayAdapter = new BooksListArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,booksList.getArrayList());
        this.setListAdapter(arrayAdapter);
        this.runSearch(keyWord);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup parent,Bundle savedInstanceState){
        View retView = inflater.inflate(R.layout.mainlistfragment_listview,parent,false);
        final ListView listView = (ListView) retView.findViewById(android.R.id.list);
        if(listFooterView == null){
            listFooterView = inflater.inflate(R.layout.listview_footer_view,null,false);
        }
//        listView.addFooterView(listFooterView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int lastItem;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int total = MainListFragment.this.getListAdapter().getCount();
                if((lastItem == total)&&(scrollState == this.SCROLL_STATE_IDLE)){
                    apiThread.runSearch(keyWord,searchPageNumber);
//                    listView.removeFooterView(listFooterView);
                    listFooterView.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (listView.getFooterViewsCount() == 0
//                        && (totalItemCount!=visibleItemCount)) {
//                    listView.addFooterView(listFooterView);
//                    lastItem = firstVisibleItem+visibleItemCount-1;
//                    return;
//                }
                if(listFooterView.getVisibility() == View.INVISIBLE
                        && (totalItemCount != visibleItemCount)){
                    listFooterView.setVisibility(View.VISIBLE);
                }
                lastItem = firstVisibleItem+visibleItemCount;

            }
        });
        return retView;
    }
    private void runSearch(String arg){
        apiThread.runSearch(arg,0);
    }
    public void setKeyWord(String arg){
//        if(this.keyWord != arg || this.keyWord ==null){
            this.keyWord = arg;
//            booksList.clearBooks();
//        }
    }

    @Override
    public void onListItemClick(ListView listView,View view,int position,long id){
        BookInfo bookInfo = ((BooksListArrayAdapter)getListAdapter()).getItem(position);
        Intent intent = new Intent(getActivity(),BookInfoActivity.class);
        intent.putExtra("bookid", bookInfo.getID());
        intent.putExtra("title",bookInfo.getTitile());
        intent.putExtra("subtitle",bookInfo.getSubTitle());
        intent.putExtra("description",bookInfo.getShortDescription());
        intent.putExtra("coverimagefile",bookInfo.getCacheImageFileName());

        getActivity().startActivity(intent);
    }

}
