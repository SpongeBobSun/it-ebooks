package sun.bob.it_ebooks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by sunkuan on 15/3/20.
 */
public class BookInfoActivity extends ActionBarActivity {
    private BookInfoFragment bookInfoFragment;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        BookInfoFragment fragment = (BookInfoFragment) fragmentManager.findFragmentById(R.id.id_fragmentContainer);
        if(fragment == null){
            bookInfoFragment = new BookInfoFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.id_fragmentContainer,bookInfoFragment)
                    .commit();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        ((SupportMenuItem)menu.getItem(0)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.id_menu_download:
                String downloadURL = bookInfoFragment.getDownloadUrl();
                Uri uri = Uri.parse(downloadURL);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
