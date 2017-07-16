package app.android.com.pappapppaid;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteRecipesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteRecipesFragment extends android.app.Fragment {



    private Set<String> set;
    private HashSet<String> in;

    private OnFragmentInteractionListener mListener;

    public FavoriteRecipesFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static FavoriteRecipesFragment newInstance() {
        FavoriteRecipesFragment fragment = new FavoriteRecipesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_recipes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        getActivity().setTitle("Ricette Preferite");
        SharedPreferences favRecipes = getActivity().getSharedPreferences("FavRecipes", Context.MODE_PRIVATE);
        set = favRecipes.getStringSet("favRecipes", new HashSet<String>());
        in = new HashSet<>(set);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view_favRecipes);
        if(in.isEmpty()){
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(getActivity());
            lparams.gravity = Gravity.CENTER;
            lparams.setMargins(0, 20, 0 ,0);
            tv.setLayoutParams(lparams);
            tv.setText(R.string.no_favorite_recipes);
            tv.setTextSize(22);
            LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.linearLayoutFavRecipes);
            linearLayout.removeView(rv);
            linearLayout.addView(tv);
        }
        else{

            new GetRecipes(in).execute();
        }


    }

    private class GetRecipes extends AsyncTask<Void, Void, List<RecipeItem>> {

        private HashSet<String> set;

        public GetRecipes(HashSet<String> set) {
            super();
            this.set = set;

        }

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected List<RecipeItem> doInBackground(Void... params) {
            ArrayList<RecipeItem> recipes = new ArrayList<>();
            Cursor c;
            DatabaseManager db = new DatabaseManager(getActivity());;
            for(String s:in){
                c = db.getRecipeFavorite(s);
                if(c != null && c.getCount() > 0){
                    c.moveToFirst();
                    do{
                        RecipeItem recipeItem = new RecipeItem();
                        recipeItem.setName(c.getString(c.getColumnIndex("Nome")));
                        recipeItem.setType(c.getString(c.getColumnIndex("Tipo")));
                        recipeItem.setDiff(c.getInt(c.getColumnIndex("Difficolta")));
                        recipeItem.setHungryLevel(c.getInt(c.getColumnIndex("Fame")));
                        recipeItem.setPrep(c.getString(c.getColumnIndex("Preparazione")));
                        recipeItem.setTimePrep(c.getInt(c.getColumnIndex("Tempo_preparazione")));
                        recipes.add(recipeItem);

                    }while(c.moveToNext());
                }
            }

            return recipes;

        }

        protected void onPostExecute(List<RecipeItem> recipes){
            (getActivity()).getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SearchResultsFragment.newInstance(recipes, true)).commit();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
