package sun.bob.it_ebooks;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by sunkuan on 15/3/22.
 */
public class AboutActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
