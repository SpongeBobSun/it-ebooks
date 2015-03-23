package sun.bob.it_ebooks.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by sunkuan on 15/3/4.
 */
public class BookInfo {
    private String CoverImageUrl;
    private long ID;
    private String ShortDescription;
    private String DownloadUrl;
    private String Titile;
    private String SubTitle;
    private String cacheImageFileName;
    private Bitmap coverImage;
    private String Author;
    private String Publisher;
    private String FullDescription;

    public String getCoverImageUrl() {
        return CoverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        CoverImageUrl = coverImageUrl;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public void setDownloadUrl(String downloadUrl) {
        DownloadUrl = downloadUrl;
    }

    public void setTitile(String titile) {
        Titile = titile;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public long getID() {
        return ID;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public String getDownloadUrl() {
        return DownloadUrl;
    }

    public String getTitile() {
        return Titile;
    }

    public String getSubTitle() {
        return SubTitle;
    }
    public String getCacheImageFileName(){
      return cacheImageFileName;
    }
    public void setCacheImageFileName(String cacheImageFileName){
      this.cacheImageFileName = cacheImageFileName;
    }
    public boolean needDownloadCoverImage(){
      File file = new File("/data/data/sun.bob.it_ebooks/imgcache/"+getTitile());
      boolean ret = file.exists();
      file = null;
      if(ret){
          if(coverImage == null)
            this.setCacheImageFileName("/data/data/sun.bob.it_ebooks/imgcache/"+getTitile());
      }
      return !ret;
    }
    public Bitmap getCoverImage(){
        if(coverImage == null) {
            coverImage = BitmapFactory.decodeFile(getCacheImageFileName());
        }
        return coverImage;
    }
    public boolean correctNullValues(BooksList booksList){
        if(ID == 0){
            BooksList.getStaticInstance(null).getArrayList().remove(this);
            return false;
        }
        if(Titile == null){
            BooksList.getStaticInstance(null).getArrayList().remove(this);
            return false;
        }
        if(SubTitle == null){
            SubTitle = Titile;
        }
        if(ShortDescription == null){
            ShortDescription = Titile;
        }
        if(CoverImageUrl == null){
            CoverImageUrl = "";
        }
        return true;
    }
    public void setFullDescription(String arg){
        this.FullDescription = arg;
    }
    public String getFullDescription(){
        return this.FullDescription;
    }
    public void setAuthor(String author){
        this.Author = author;
    }
    public String getAuthor(){
      return this.Author;
    }
    public void setPublisher(String publisher){
      this.Publisher = publisher;
    }
    public String getPublisher(){ return this.Publisher;}
}
