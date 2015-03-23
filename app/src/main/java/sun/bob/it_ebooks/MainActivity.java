package sun.bob.it_ebooks;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import sun.bob.it_ebooks.model.BooksList;


public class MainActivity extends ActionBarActivity {

    public String keyWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_fragment);
        initKeywords();
    }
    private void initKeywords(){
        LinearLayout androidView = (LinearLayout) findViewById(R.id.id_keyword_text_android);
        androidView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = "android";
                turnOnSearchFragment();
            }
        });
        LinearLayout iosView = (LinearLayout) findViewById(R.id.id_keyword_text_ios);
        iosView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = "ios";
                turnOnSearchFragment();
            }
        });
        LinearLayout javaView = (LinearLayout) findViewById(R.id.id_keyword_text_java);
        javaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = "java";
                turnOnSearchFragment();
            }
        });
        LinearLayout pythonView = (LinearLayout) findViewById(R.id.id_keyword_text_python);
        pythonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = "python";
                turnOnSearchFragment();
            }
        });
        LinearLayout nodeView = (LinearLayout) findViewById(R.id.id_keyword_text_node);
        nodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = "node.js";
                turnOnSearchFragment();
            }
        });
        LinearLayout linuxView = (LinearLayout) findViewById(R.id.id_keyword_text_linux);
        linuxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = "linux";
                turnOnSearchFragment();
            }
        });
        LinearLayout oracleView = (LinearLayout) findViewById(R.id.id_keyword_text_oracle);
        oracleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = "oracle";
                turnOnSearchFragment();
            }
        });
        LinearLayout hadoopView = (LinearLayout) findViewById(R.id.id_keyword_text_hadoop);
        hadoopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = "hadoop";
                turnOnSearchFragment();
            }
        });
        final EditText searchText = (EditText) findViewById(R.id.id_search_text);
        searchText.setTextColor(Color.WHITE);
        ImageView searchButton = (ImageView) findViewById(R.id.id_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord = searchText.getText().toString();
                if(keyWord.length() == 0){
                    Toast.makeText(MainActivity.this,"Key word is empty...",Toast.LENGTH_LONG).show();
                    return;
                }
                turnOnSearchFragment();
            }
        });
    }
    private void turnOnSearchFragment(){
        BooksList.getStaticInstance(MainActivity.this).clearBooks();
        Intent intent = new Intent(this,MainListActivity.class);
        intent.putExtra("keyword", keyWord);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.id_action_about) {
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
