package app.android.com.pappapppaid;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shind on 11/04/2017.
 */

public class FavoriteIngredientsFragment extends Fragment {

    private Set<String> set;
    private HashSet<String> in;
    private FavIngredientsAdapter adapter;
    private ArrayList<String> favIngr;

    public FavoriteIngredientsFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static FavoriteIngredientsFragment newInstance() {
        FavoriteIngredientsFragment fragment = new FavoriteIngredientsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_ingredients, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        super.onViewCreated(view, bundle);
        getActivity().setTitle("Ingredienti Preferiti");
        favIngr = new ArrayList<>();
        SharedPreferences favRecipes = getActivity().getSharedPreferences("FavIngredients", Context.MODE_PRIVATE);
        set = favRecipes.getStringSet("favIngr", new HashSet<String>());
        in = new HashSet<>(set);

        for(String s:in){
            favIngr.add(s);
        }
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view_favIngredients);

        if(favIngr.isEmpty()){
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(getActivity());
            lparams.gravity = Gravity.CENTER;
            lparams.setMargins(0, 20, 0 ,0);
            tv.setLayoutParams(lparams);
            tv.setText(R.string.no_favorite_ingredients);
            tv.setTextSize(22);
            LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.linearLayoutFavIngr);
            linearLayout.removeView(rv);
            linearLayout.addView(tv);
        }
        else {

            LinearLayoutManager lm = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(lm);
            adapter = new FavIngredientsAdapter(favIngr, getActivity(), FavoriteIngredientsFragment.this);
            rv.setAdapter(adapter);
        }


    }

    public void removeFavoriteIngredient(int i){
        favIngr.remove(i);
        adapter.notifyDataSetChanged();
    }
}
