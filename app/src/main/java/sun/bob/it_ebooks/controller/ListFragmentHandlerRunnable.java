package sun.bob.it_ebooks.controller;

import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import sun.bob.it_ebooks.MainListFragment;

/**
 * Created by sunkuan on 15/3/19.
 */
public class ListFragmentHandlerRunnable implements Runnable {
    private ListFragment listFragment;
    public ListFragmentHandlerRunnable(ListFragment listFragment){
        this.listFragment = listFragment;
    }
    @Override
    public void run() {
        ((ArrayAdapter)listFragment.getListAdapter()).notifyDataSetChanged();
        ((MainListFragment)listFragment).searchPageNumber++;
    }
}
