package app.android.com.pappapppaid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shind on 11/04/2017.
 */

public class FavIngredientsAdapter  extends RecyclerView.Adapter<FavIngredientsAdapter.ViewHolder>  {

    private ArrayList<String> ingredients;
    private Context ctx;
    private FavoriteIngredientsFragment fragment;
    private Set<String> set;
    private HashSet<String> in;

    public FavIngredientsAdapter(ArrayList<String> ingredients, Context ctx, FavoriteIngredientsFragment fragment){
        this.ingredients = ingredients;
        this.ctx = ctx;
        this.fragment = fragment;
    }


    @Override
    public FavIngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.single_ingredients, parent, false);
        FavIngredientsAdapter.ViewHolder viewHolder = new FavIngredientsAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavIngredientsAdapter.ViewHolder viewHolder, int position) {
        String item = ingredients.get(position);
        TextView textView = viewHolder.text;
        textView.setText(item);



    }
    @Override
    public int getItemCount() {
        return ingredients.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.textViewIngredientSingle);

            itemView.findViewById(R.id.deleteSingIngr).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences favRecipes = ctx.getSharedPreferences("FavIngredients", Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = favRecipes.edit();
                    set = favRecipes.getStringSet("favIngr", new HashSet<String>());
                    in = new HashSet<>(set);
                    in.remove(text.getText().toString());
                    editor.putStringSet("favIngr", in).apply();
                    fragment.removeFavoriteIngredient(getAdapterPosition());

                }
            });

            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new GetRecipes(ingredients.get(getAdapterPosition())).execute();
                }
            });
        }



    }

    private class GetRecipes extends AsyncTask <Void, Void, List<RecipeItem>>{
        private String ingr;

        public GetRecipes(String ingr) {
            super();
            this.ingr = ingr;

        }

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected List<RecipeItem> doInBackground(Void... params) {
            ArrayList<RecipeItem> recipes = new ArrayList<>();
            DatabaseManager db = new DatabaseManager(ctx);
            Cursor ric = db.getRecipesFromIngredient(ingr);

            if(ric != null && ric.getCount()>0){
                ric.moveToFirst();
                do{
                    RecipeItem recipeItem = new RecipeItem();
                    recipeItem.setName(ric.getString(ric.getColumnIndex("Nome")));
                    recipeItem.setType(ric.getString(ric.getColumnIndex("Tipo")));
                    recipeItem.setDiff(ric.getInt(ric.getColumnIndex("Difficolta")));
                    recipeItem.setHungryLevel(ric.getInt(ric.getColumnIndex("Fame")));
                    recipeItem.setPrep(ric.getString(ric.getColumnIndex("Preparazione")));
                    recipeItem.setTimePrep(ric.getInt(ric.getColumnIndex("Tempo_preparazione")));
                    recipes.add(recipeItem);

                }while(ric.moveToNext());

            }


            return recipes;

        }

        protected void onPostExecute(List<RecipeItem> recipes){
            ((Activity) ctx).getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SearchResultsFragment.newInstance(recipes, false)).addToBackStack(null).commit();
        }

    }



}
