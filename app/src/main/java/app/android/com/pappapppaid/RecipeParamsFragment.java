package app.android.com.pappapppaid;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeParamsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeParamsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeParamsFragment extends android.app.Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView rv;
    private EditText editText;
    private Button addButton;
    private CoordinatorLayout parentLayout;
    private List<String> ingredients = new ArrayList<>();
    private FloatingActionButton fabDone;
    private EditText tempPrep;
    private Spinner hungry;
    private Spinner diff;

    public RecipeParamsFragment() {}


    public static RecipeParamsFragment newInstance() {
        RecipeParamsFragment fragment = new RecipeParamsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_params, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedIstanceState) {



        addButton = (Button) getView().findViewById(R.id.addButton);
        editText = (EditText) getView().findViewById(R.id.editTextIngredients);
        tempPrep = (EditText) getView().findViewById(R.id.prepTimeEditText);
        hungry = (Spinner) getView().findViewById(R.id.spinnerHungry);
        diff = (Spinner) getView().findViewById(R.id.spinnerDiff);
        getActivity().setTitle(R.string.set_parameters);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        parentLayout = (CoordinatorLayout) getView().findViewById(R.id.rootViewRecipeParams);
        editText.performClick();
        rv = (RecyclerView) getView().findViewById(R.id.recyclerViewRecipeParams);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        fabDone = (FloatingActionButton) view.findViewById(R.id.fabDone);



        final RecipeParamsRecyclerViewAdapter adapter = new RecipeParamsRecyclerViewAdapter(ingredients,getActivity());
        rv.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().length() == 0){
                    Snackbar snackbar = Snackbar.make(parentLayout, R.string.no_ingredient, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

                else{
                    ingredients.add(editText.getText().toString());
                    adapter.notifyItemInserted(ingredients.size() - 1);
                    editText.setText("");
                }

            }
        });

        adapter.setOnIngredientClickedListener(new RecipeParamsRecyclerViewAdapter.onIngredientClickedListener() {
            @Override
            public void deleteIngredient(int position) {
                ingredients.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void addToFavorites(int position) {

            }

        });

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int diffNum = 0;
                switch(diff.getSelectedItem().toString()){
                    case "Molto facile":
                        diffNum = 1;
                        break;
                    case "Facile":
                        diffNum = 2;
                        break;
                    case "Intermedio":
                        diffNum = 3;
                        break;
                    case "Difficile":
                        diffNum = 4;
                        break;
                    case "Molto difficile":
                        diffNum = 5;
                        break;
                    case "Very Easy":
                        diffNum = 1;
                        break;
                    case "Easy":
                        diffNum = 2;
                        break;
                    case "Regular":
                        diffNum = 3;
                        break;
                    case "Hard":
                        diffNum = 4;
                        break;
                    case "Very hard":
                        diffNum = 5;
                        break;
                }
                try{
                    new SearchRecipes(ingredients, Integer.parseInt(tempPrep.getText().toString()), Integer.parseInt(hungry.getSelectedItem().toString()), diffNum).execute();
                }catch(Exception e){
                    final LinearLayout parentLayout = (LinearLayout) getActivity().findViewById(R.id.rootView);
                    Snackbar snackbar = Snackbar.make(parentLayout, R.string.insert_correctly, Snackbar.LENGTH_LONG);
                    snackbar.show();

                }

            }
        });



    }

    private class SearchRecipes extends AsyncTask<Void, Void, List<RecipeItem>>{
        private List<String> ingredients;
        private int hungryLevelMin;
        private int tempPrepMax;
        private int diffLevelMax;

        public SearchRecipes(List<String> ingredients, int tempPrepMax, int hungryLevelMin, int diffLevelMax) {
            super();
            this.ingredients = ingredients;
            this.tempPrepMax = tempPrepMax;
            this.hungryLevelMin = hungryLevelMin;
            this.diffLevelMax = diffLevelMax;

        }

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected List<RecipeItem> doInBackground(Void... params) {
            ArrayList<RecipeItem> recipes = new ArrayList<>();
            DatabaseManager db = new DatabaseManager(getActivity());
            Cursor ric = db.getRecipesSearch(ingredients, tempPrepMax, hungryLevelMin, diffLevelMax, getActivity());

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
                return recipes;
            }

            else
                return null;




        }

        protected void onPostExecute(List<RecipeItem> recipes){
            (getActivity()).getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SearchResultsFragment.newInstance(recipes, false)).addToBackStack(null).commit();
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
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        FloatingActionButton fa = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fa.setVisibility(View.VISIBLE);
        getActivity().setTitle(R.string.app_name);
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
