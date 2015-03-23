package sun.bob.it_ebooks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import sun.bob.it_ebooks.model.BooksList;

/**
 * Created by sunkuan on 15/3/21.
 */
public class MainListActivity extends ActionBarActivity {
    MainListFragment searchFragment;
    private String keyWord;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        keyWord = getIntent().getStringExtra("keyword");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        Fragment mainFragment =  fragmentManager.findFragmentById(R.id.id_fragmentContainer);
        if(mainFragment == null){
            searchFragment = new MainListFragment();
            searchFragment.setKeyWord(keyWord);
            fragmentManager.beginTransaction()
                    .add(R.id.id_fragmentContainer,searchFragment)
                    .commit();
        }
    }
}
