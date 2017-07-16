package app.android.com.pappapppaid;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by shind on 26/03/2017.
 */

public class SearchResultsFragment extends Fragment {

    private Set<String> set;
    private HashSet<String> in;

    private List<RecipeItem> recipes;
    private SearchResultsAdapter adapter = null;
    private RecyclerView rv;
    private boolean shouldUpdateList = false;

    public SearchResultsFragment() {
    }


    public static SearchResultsFragment newInstance(List<RecipeItem> recipes, boolean should) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        fragment.setRecipeList(recipes);
        fragment.setShouldUpdateList(should);
        return fragment;
    }

    public void setShouldUpdateList(boolean should){
        this.shouldUpdateList = should;
    }

    public void setRecipeList(List<RecipeItem> recipes){
        this.recipes = recipes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(adapter != null){
            updateArrayListRec();
        }
        return inflater.inflate(R.layout.fragment_recipe_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedIstanceState) {



        if(!shouldUpdateList)
            getActivity().setTitle("Risultati Ricerca");

        rv = (RecyclerView) getView().findViewById(R.id.recycler_view);
        View v = getActivity().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        if(recipes == null){
            if(!shouldUpdateList) {
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView tv = new TextView(getActivity());
                lparams.gravity = Gravity.CENTER;
                lparams.setMargins(0, 20, 0, 0);
                tv.setLayoutParams(lparams);
                tv.setText(R.string.no_results);
                tv.setTextSize(22);
                LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.linLayoutSearch);
                linearLayout.removeView(rv);
                linearLayout.addView(tv);
            }
            else{
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView tv = new TextView(getActivity());
                lparams.gravity = Gravity.CENTER;
                lparams.setMargins(0, 20, 0 ,0);
                tv.setLayoutParams(lparams);
                tv.setText(R.string.no_favorite_recipes);
                tv.setTextSize(22);
                LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.linLayoutSearch);
                linearLayout.removeView(rv);
                linearLayout.addView(tv);
            }
        }
        else{
            //LinearLayoutManager lm = new LinearLayoutManager(getActivity());
            StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rv.setHasFixedSize(true);
            rv.setLayoutManager(lm);
            adapter = new SearchResultsAdapter(recipes, getActivity());
            //adapter.setHasStableIds(true);
            rv.setAdapter(adapter); // Animazioni
            final ProgressDialog p = new ProgressDialog(getActivity());
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.setTitle("PappApp");
            p.setMessage("Loading...");
            rv.setVisibility(View.INVISIBLE);
            p.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    p.dismiss();
                    rv.setVisibility(View.VISIBLE);
                }
            }, 250);
        }

    }


    private void updateArrayListRec(){
        SharedPreferences favRecipes = getActivity().getSharedPreferences("FavRecipes", Context.MODE_PRIVATE);
        set = favRecipes.getStringSet("favRecipes", new HashSet<String>());
        in = new HashSet<>(set);
        if(shouldUpdateList){
            for(RecipeItem item: new ArrayList<RecipeItem>(recipes)){
                if(!in.contains(item.getName())){
                    recipes.remove(item);
                    if(recipes.size() == 0)
                        recipes = null;
                }
            }
            adapter.notifyDataSetChanged();

        }

    }

}
