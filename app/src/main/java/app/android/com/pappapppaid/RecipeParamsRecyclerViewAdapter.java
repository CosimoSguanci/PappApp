package app.android.com.pappapppaid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shind on 25/01/2017.
 */

public class RecipeParamsRecyclerViewAdapter  extends RecyclerView.Adapter<RecipeParamsRecyclerViewAdapter.ViewHolder> {

    private List<String> list;
    private Context ctx;
    private onIngredientClickedListener listener = null;

    public RecipeParamsRecyclerViewAdapter(List<String> list, Context ctx){
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public RecipeParamsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.single_recipe_params_obj, parent, false);
        RecipeParamsRecyclerViewAdapter.ViewHolder viewHolder = new RecipeParamsRecyclerViewAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeParamsRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        String ingredient = list.get(position);
        TextView textView = viewHolder.text;
        textView.setText(ingredient);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView trash;
        public ImageView star;
        public boolean changeStar = true;
        SharedPreferences favRecipes = ctx.getSharedPreferences("FavIngredients", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = favRecipes.edit();
        Set<String> set = favRecipes.getStringSet("favIngr", new HashSet<String>());
        HashSet<String> in = new HashSet<String>(set);
        boolean add = true;

        public ViewHolder(View itemView) {
            super(itemView);



            text = (TextView) itemView.findViewById(R.id.ingredient_tetxview);
            trash = (ImageView) itemView.findViewById(R.id.trash);
            star = (ImageView) itemView.findViewById(R.id.star);

            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.deleteIngredient(getAdapterPosition());
                }
            });

            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    set = favRecipes.getStringSet("favIngr", new HashSet<String>());
                    in = new HashSet<String>(set);
                    if(changeStar){
                        star.setImageResource(R.mipmap.ic_star_black_24dp);
                        changeStar = false;
                        for(String s: in){
                            if(s.equals(text.getText()))
                                add = false;
                        }

                        if(add){
                            in.add(list.get(getAdapterPosition()));
                            editor.putStringSet("favIngr", in).apply();
                        }
                    }
                    else{
                        star.setImageResource(R.mipmap.ic_star_outline_black_24dp);
                        changeStar = true;
                        in.remove(text.getText().toString());
                        editor.putStringSet("favIngr", in).apply();
                    }

                    listener.addToFavorites(getAdapterPosition());
                }
            });
        }

    }




    private Context getContext(){
        return ctx;
    }

    public void setOnIngredientClickedListener(onIngredientClickedListener listener) {
        this.listener = listener;
    }


    public interface onIngredientClickedListener{
        void deleteIngredient(int position);
        void addToFavorites(int position);
    }





}
