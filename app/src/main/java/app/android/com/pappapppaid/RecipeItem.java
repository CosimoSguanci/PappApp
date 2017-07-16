package app.android.com.pappapppaid;

/**
 * Created by shind on 26/03/2017.
 */

public class RecipeItem {

    private String name, type, prep, ingredients;
    private int diff, hungryLevel, timePrep;

    public RecipeItem(){

    }

    public RecipeItem(String name, String type, String prep, String ingredients, int diff, int hungryLevel,  int timePrep){
        this.name = name;
        this.type = type;
        this.prep = prep;
        this.ingredients = ingredients;
        this.diff = diff;
        this.hungryLevel = hungryLevel;
        this.timePrep = timePrep;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrep(String prep){
        this.prep = prep;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
    }

    public void setDiff(int diff){
        this.diff = diff;
    }

    public void setHungryLevel(int hungry){
        this.hungryLevel = hungry;
    }

    public void setTimePrep(int time){
        this.timePrep = time;
    }

    public String getName(){
        return name;
    }

    public String getPrep(){
        return prep;
    }

    public String getType(){
        return type;
    }

    public String getIngredients(){
        return ingredients;
    }

    public int getDiff(){
        return diff;
    }

    public int getHungryLevel(){
        return hungryLevel;
    }

    public int getTimePrep(){
        return timePrep;
    }


}
