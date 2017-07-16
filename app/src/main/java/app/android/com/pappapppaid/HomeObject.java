package app.android.com.pappapppaid;

/**
 * Created by shind on 17/01/2017.
 */

public class HomeObject {
    private String text;
    private int img;
    private String type;

    public HomeObject(String text, int img, String type){
        this.text = text;
        this.img = img;
        this.type = type;
    }

    public String getObjText(){
        return text;
    }

    public int getObjImgId(){
        return img;
    }

    public void setObjText(String text){
        this.text = text;
    }

    public void setObjImg(int img){
        this.img = img;
    }

    public String getType(){
        return type;
    }
}
