package sun.bob.it_ebooks.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by sunkuan on 15/3/4.
 */
public class BooksList {
    public static BooksList staticInstance;
    private Context appContext;
    private ArrayList<BookInfo> arrayList;
    private ArrayList<String> idList;
    private BooksList(Context context){
        appContext = context;
        arrayList = new ArrayList<BookInfo>();
        idList = new ArrayList<String>();
    }
    public static BooksList getStaticInstance(Context context){
        if(staticInstance == null)
            staticInstance = new BooksList(context.getApplicationContext());
        return staticInstance;
    }
    public ArrayList getArrayList() {
        return arrayList;
    }
    public BookInfo getBookAt(int index){
        return arrayList.get(index);
    }
    public void addBook(BookInfo book){
        if(checkDuplicate(book.getID())){
            return;
        }
        arrayList.add(book);
        idList.add(String.valueOf(book.getID()));
    }
    public boolean checkDuplicate(long id){
        return idList.contains(String.valueOf(id));
    }
    public void clearBooks(){
        arrayList.clear();
        idList.clear();
    }
}
