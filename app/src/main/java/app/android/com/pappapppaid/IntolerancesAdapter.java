package app.android.com.pappapppaid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shind on 21/04/2017.
 */

public class IntolerancesAdapter extends RecyclerView.Adapter<IntolerancesAdapter.ViewHolder>  {

    private ArrayList<String> intolerances;
    private Context ctx;
    private IntolerancesFragment fragment;
    private Set<String> set;
    private HashSet<String> in;

    public IntolerancesAdapter(ArrayList<String> intolerances, Context ctx, IntolerancesFragment fragment){
        this.intolerances = intolerances;
        this.ctx = ctx;
        this.fragment = fragment;
    }


    @Override
    public IntolerancesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.single_ingredients, parent, false);
        IntolerancesAdapter.ViewHolder viewHolder = new IntolerancesAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IntolerancesAdapter.ViewHolder viewHolder, int position) {
        String item = intolerances.get(position);
        TextView textView = viewHolder.text;
        textView.setText(item);



    }
    @Override
    public int getItemCount() {
        return intolerances.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.textViewIngredientSingle);

            itemView.findViewById(R.id.deleteSingIngr).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences favRecipes = ctx.getSharedPreferences("Intolerances", Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = favRecipes.edit();
                    set = favRecipes.getStringSet("Intolerances", new HashSet<String>());
                    in = new HashSet<>(set);
                    in.remove(text.getText().toString());
                    editor.putStringSet("Intolerances", in).apply();
                    fragment.removeIntolerance(getAdapterPosition());

                }
            });


        }



    }



}

