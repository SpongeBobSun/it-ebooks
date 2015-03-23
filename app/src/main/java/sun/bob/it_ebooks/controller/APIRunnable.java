package sun.bob.it_ebooks.controller;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import sun.bob.it_ebooks.R;
import sun.bob.it_ebooks.model.BookInfo;
import sun.bob.it_ebooks.model.BooksList;

/**
 * Created by sunkuan on 15/3/18.
 */
public class APIRunnable implements Runnable{
    private int RunFlag;

    private String searchArg;
    private String searchUrl = "http://it-ebooks-api.info/v1/search/";
    private ListFragment appContext;
    private Handler handler;
    private int totalPage;

    private Handler detailHandler;
    private BookDetailRunnable detailRunnable;
    private String bookDetailUrl = "http://it-ebooks-api.info/v1/book/";
    private String bookDetailId;
    private BookInfo bookDetail;

    /**
      For search thread.
    */
    public APIRunnable(ListFragment context, Handler handler){
        appContext = context;
        this.handler = handler;
    }
    /**
      For detail thread.
    */
    public APIRunnable(Handler handler,Runnable runnable){
      this.detailHandler = handler;
      this.detailRunnable = (BookDetailRunnable)runnable;
    }

    private String APISearch() throws Exception {
        String ret = new String();
        URL url = new URL(searchUrl+searchArg);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("accept", "*/*");
        urlConnection.setRequestProperty("connection", "Keep-Alive");
        urlConnection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        urlConnection.connect();
        Map<String,List<String>> headers =  urlConnection.getHeaderFields();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream())
        );
        String line;
        while((line = bufferedReader.readLine())!=null){
            ret += line;
        }
        return ret;
    }
    private String APIDetail() throws Exception{
        String ret = new String();
        URL url = new URL(bookDetailUrl+bookDetailId);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("accept", "*/*");
        urlConnection.setRequestProperty("connection", "Keep-Alive");
        urlConnection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        urlConnection.connect();
        Map<String,List<String>> headers =  urlConnection.getHeaderFields();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream())
        );
        String line;
        while((line = bufferedReader.readLine())!=null){
            ret += line;
        }
        return ret;
    }
    private void parse(String jsonString){
        JSONObject wholeResponse;
        JSONArray inventory;
        try {
            wholeResponse = new JSONObject(jsonString);
            totalPage = wholeResponse.getInt("Total");
            if(totalPage%10 == 0){
                totalPage /= 10;
            }else{
                totalPage /= 10;
                totalPage += 1;
            }
            inventory = wholeResponse.getJSONArray("Books");
        }catch (JSONException e){
            e.printStackTrace();
            return;
        }
            JSONObject book = null;
            BooksList booksList = BooksList.getStaticInstance(appContext.getActivity());
            BookInfo bookInfo;

        for (int i = 0; i < inventory.length(); i++) {
            bookInfo = new BookInfo();
            try {
                book = inventory.getJSONObject(i);
                bookInfo.setID(book.getLong("ID"));
                bookInfo.setTitile(book.getString("Title"));
                bookInfo.setSubTitle(book.getString("SubTitle"));
                bookInfo.setShortDescription(book.getString("Description"));
                bookInfo.setCoverImageUrl(book.getString("Image"));
                booksList.addBook(bookInfo);
            }catch(JSONException e){
                e.printStackTrace();
                if(bookInfo.correctNullValues(booksList)){
                    booksList.addBook(bookInfo);
                }
                continue;
            }

        }
        handler.post(new ListFragmentHandlerRunnable(appContext));
    }
    public void parseDetail(String jsonString){
        BookInfo bookDetail = new BookInfo();
        JSONObject bookjson;
        try {
            bookjson = new JSONObject(jsonString);
            bookDetail.setTitile(bookjson.getString("Title"));
            bookDetail.setSubTitle(bookjson.getString("SubTitle"));
            bookDetail.setCoverImageUrl(bookjson.getString("Image"));
            bookDetail.setDownloadUrl(bookjson.getString("Download"));
            bookDetail.setFullDescription(bookjson.getString("Description"));
            bookDetail.setAuthor(bookjson.getString("Author"));
            bookDetail.setPublisher(bookjson.getString("Publisher"));
            bookDetail.setCacheImageFileName("/data/data/sun.bob.it_ebooks/imgcache/"+bookDetail.getTitile());
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Todo
        detailRunnable.setBookDetail(bookDetail);
        detailHandler.post(detailRunnable);
        return;
    }
    public void runSearch(String searchString,int page){
        RunFlag = 1;
        searchArg = searchString;
        if((page >= totalPage) && page != 0){
            return;
        }
        page+=1;
        searchArg += "/page/"+page;
        new Thread(this).start();
    }

    public void runBookDetail(long id){
        RunFlag = 2;
        bookDetailId = String.valueOf(id);
        new Thread(this).start();
    }

    @Override
    public void run(){
        switch (RunFlag){
            case 1:
                try {
                    parse(APISearch());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 2:
                try{
                    parseDetail(APIDetail());
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }

}
