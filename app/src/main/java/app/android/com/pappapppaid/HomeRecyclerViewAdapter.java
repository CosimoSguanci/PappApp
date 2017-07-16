package app.android.com.pappapppaid;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shind on 17/01/2017.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private List<HomeObject> list;
    private Context ctx;

    public HomeRecyclerViewAdapter(List<HomeObject> list, Context ctx){
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.single_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        HomeObject obj = list.get(position);
        TextView textView = viewHolder.text;
        textView.setText(obj.getObjText());
        ImageView imageView = viewHolder.img;
        imageView.setImageResource(obj.getObjImgId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView text;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            text = (TextView) itemView.findViewById(R.id.high_textviewcard);
            img = (ImageView) itemView.findViewById(R.id.meal_image);
        }

        @Override
        public void onClick(View view){
            HomeObject obj = list.get(getAdapterPosition());
            String type = obj.getType();
            new GetRecipes(type).execute();


        }

        private class GetRecipes extends AsyncTask <Void, Void, List<RecipeItem>>{

            private String type;

            public GetRecipes(String mealType) {
                super();
                this.type = mealType;

            }

            protected void onPreExecute(){
                super.onPreExecute();
            }

            protected List<RecipeItem> doInBackground(Void... params) {
                ArrayList<RecipeItem> recipes = new ArrayList<>();
                DatabaseManager db = new DatabaseManager(getContext());
                Cursor ric = db.getRecipesHome(type);

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
                ((Activity) ctx).getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SearchResultsFragment.newInstance(recipes, false)).addToBackStack("First").commit();
            }

        }

    }




    private Context getContext(){
        return ctx;
    }




}
