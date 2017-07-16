package app.android.com.pappapppaid;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shind on 06/02/2017.
 */

public class DatabaseManager extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "RICETTE2.db";
    private static final int VERSION = 1;

    public DatabaseManager(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }


    public Cursor getRecipesHome(String mealType){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Ricette WHERE Tipo='"+mealType+"'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }

    public Cursor getRecipesSearch(List<String> ingredients, int tempPrepMax, int hungryLevelMin, int diffLevelMax, Context ctx){

        Set<String> set;
        Set<String> in;

        SharedPreferences intolerances = ctx.getSharedPreferences("Intolerances", Context.MODE_PRIVATE);
        set = intolerances.getStringSet("Intolerances", new HashSet<String>());
        in = new HashSet<>(set);

        SQLiteDatabase db = getReadableDatabase();

        tempPrepMax += 30;

        // Controlla allargamento ricerca.
        if(hungryLevelMin != 1)
            hungryLevelMin--;

        if(diffLevelMax != 5)
            diffLevelMax++;

        String query = "SELECT * FROM Ricette WHERE Tempo_preparazione<="+tempPrepMax+" AND Fame>="+hungryLevelMin+" AND Difficolta<="+diffLevelMax;

        for(String ingredient:ingredients){
            if(!in.contains(ingredient)){
                ingredient = ingredient.substring(0, ingredient.length()-1);
                query = query + " AND Ingredienti LIKE '%"+ingredient+"%'";
            }

        }


        for(String s:in){

            if(s.equals("Glutine") || s.equals("glutine")){
                query = query + " AND Ingredienti NOT LIKE '%Farina%' AND Ingredienti NOT LIKE '%Orzo%' AND Ingredienti NOT LIKE '%Frumento%' AND Ingredienti NOT LIKE '%Segale%' AND Ingredienti NOT LIKE '%Kamut%' AND Ingredienti NOT LIKE '%Pane%' AND Ingredienti NOT LIKE '%Panini%' AND Ingredienti NOT LIKE '%Panino%' " +
                        "AND Ingredienti NOT LIKE '%Pasta sfoglia%' AND Ingredienti NOT LIKE '%Biscotti%' AND Nome NOT LIKE '%Biscotti%' AND Ingredienti NOT LIKE '%Spaghetti%' AND Nome NOT LIKE '%Lasagna%' AND Nome NOT LIKE '%Ravioli%'";
            }
            else
                query = query + " AND Ingredienti NOT LIKE '%"+s+"%'";
        }

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }

    public Cursor getIngredients(String nameRecipe){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT Ingredienti FROM Ricette WHERE Nome='"+nameRecipe+"'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }

    public Cursor getRecipeFavorite(String name){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Ricette WHERE Nome='"+name+"'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }

    public Cursor getRecipesFromIngredient(String ingredient){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM Ricette WHERE Ingredienti LIKE '%"+ingredient+"%'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }

}
