package app.android.com.pappapppaid;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SingleRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleRecipeFragment extends android.app.Fragment {

    private String recipeName;
    private String recipePrep;
    private int recipeTimePrep;
    private boolean changeStar;
    private Set<String> set;
    private Set<String> in;

    private TextView name, prep, time, ingredients;
    private ImageView image, star;

    private DatabaseManager db;




    public SingleRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment SingleRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleRecipeFragment newInstance(String name, String prep, int time) {
        SingleRecipeFragment fragment = new SingleRecipeFragment();
        fragment.setArgs(name, prep, time);
        return fragment;
    }

    private void setArgs(String name, String prep, int time){
        this.recipeName = name;
        this.recipePrep = prep;
        this.recipeTimePrep = time;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedIstanceState) {
        name = (TextView) view.findViewById(R.id.recipeName);
        prep = (TextView) view.findViewById(R.id.recipePrep);
        time = (TextView) view.findViewById(R.id.recipeTimePrep);
        ingredients = (TextView) view.findViewById(R.id.ingredients_singlerecipe);

        image = (ImageView) view.findViewById(R.id.imageSingle);
        star = (ImageView) view.findViewById(R.id.starSingleRecipe);

        String prepFirstChar = recipePrep.substring(0, 1);

        if(prepFirstChar.equals(" ")){
            StringBuilder sb = new StringBuilder(recipePrep);
            sb.deleteCharAt(0);
            recipePrep = sb.toString();
        }


        name.setText(recipeName);
        prep.setText(recipePrep);

        View parent = view.findViewById(R.id.card_view_singlerecipetext);
        parent.post(new Runnable() {
            public void run() {
                Rect delegateArea = new Rect();
                ImageView delegate = star;
                delegate.getHitRect(delegateArea);
                delegateArea.top -= 100;
                delegateArea.bottom += 100;
                delegateArea.left -= 100;
                delegateArea.right += 100;
                TouchDelegate expandedArea = new TouchDelegate(delegateArea,
                        delegate);
                if (View.class.isInstance(delegate.getParent())) {
                    ((View) delegate.getParent())
                            .setTouchDelegate(expandedArea);
                }
            }
        });


        if(recipeTimePrep == 0)
            time.setVisibility(View.GONE);

        else
            time.setText(recipeTimePrep+" minuti");

        db = new DatabaseManager(getActivity());

        String [] strParts = recipeName.split(" ");
        for(int i = 0; i<strParts.length; i++){
            strParts[i] = strParts[i]+"_";
            strParts[i] = strParts[i].toLowerCase();
        }

        strParts[strParts.length-1] = strParts[strParts.length-1].replace("_","");
        String resName = "";
        for (String s: strParts){
            resName +=s;
        }

        resName = Normalizer.normalize(resName, Normalizer.Form.NFD);
        resName = resName.replaceAll("[^\\p{ASCII}]", "");
        resName = resName.replaceAll("'", "");
        resName = resName.replaceAll("`", "_");
        resName = resName.replaceAll(",", "");
        resName = resName.replaceAll("[()]", "");





        int test = getActivity().getResources().getIdentifier(resName, "mipmap", getActivity().getPackageName());

        if(test != 0){
            image.setImageResource(test);
        }
        else{
            view.findViewById(R.id.card_view_singlerecipeimage).setVisibility(View.GONE);
        }

        String ingredientsStr = "";
        Cursor c = db.getIngredients(recipeName);
        c.moveToFirst();
        do{
            ingredientsStr += c.getString(c.getColumnIndex("Ingredienti"));


        }while(c.moveToNext());

        strParts = ingredientsStr.split(",");
        ingredientsStr = "";

        for(String s:strParts){
            ingredientsStr += s + System.getProperty("line.separator");

        }

        ingredients.setText(ingredientsStr);

        SharedPreferences favRecipes = getActivity().getSharedPreferences("FavRecipes", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = favRecipes.edit();
        set = favRecipes.getStringSet("favRecipes", new HashSet<String>());
        in = new HashSet<>(set);


        boolean bStar = false;


        for (String s : set) {
            if (s.equals(recipeName)) {
                bStar = true;
            }
        }

        if(bStar){
            star.setImageResource(R.mipmap.ic_star_black_24dp);
            changeStar = true;
        }
        else{
            star.setImageResource(R.mipmap.ic_star_outline_black_24dp);
            changeStar = false;
        }
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(changeStar){

                    star.setImageResource(R.mipmap.ic_star_outline_black_24dp);
                    changeStar = false;
                    in.remove(recipeName);
                    editor.putStringSet("favRecipes", in).apply();
                }
                else{
                    star.setImageResource(R.mipmap.ic_star_black_24dp);
                    changeStar = true;
                    in.add(recipeName);
                    editor.putStringSet("favRecipes", in).apply();
                }


            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_recipe, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
