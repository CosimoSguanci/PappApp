package app.android.com.pappapppaid;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shind on 21/04/2017.
 */

public class IntolerancesFragment extends Fragment {
    private Set<String> set;
    private HashSet<String> in;
    private IntolerancesAdapter adapter;
    private ArrayList<String> intolerancesArr;
    LinearLayout parentLayout;

    public IntolerancesFragment(){

    }

    public IntolerancesFragment newIstance(){
        IntolerancesFragment frag = new IntolerancesFragment();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.intolerances_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getActivity().setTitle("Intolleranze Alimentari");
        parentLayout = (LinearLayout) getActivity().findViewById(R.id.rootView);
        intolerancesArr = new ArrayList<>();
        SharedPreferences intolerances = getActivity().getSharedPreferences("Intolerances", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = intolerances.edit();
        set = intolerances.getStringSet("Intolerances", new HashSet<String>());
        in = new HashSet<>(set);

        for(String intolerance:in){
            intolerancesArr.add(intolerance);
        }

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view_intolerances);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        adapter = new IntolerancesAdapter(intolerancesArr, getActivity(), IntolerancesFragment.this);
        rv.setAdapter(adapter);

        Button btn = (Button) view.findViewById(R.id.addIntoleranceButton);
        final EditText editText = (EditText) view.findViewById(R.id.editTextIntolerance);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                if(!text.equals("")){
                    in.add(text);
                    editor.putStringSet("Intolerances", in).apply();
                    editText.setText("");

                    Snackbar snackbar = Snackbar.make(parentLayout, R.string.intolerance_added, Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(parentLayout, R.string.intolerance_not_added, Snackbar.LENGTH_LONG);

                    snackbar.show();
                }

                addIntolerance(text);

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);



            }
        });

    }

    public void removeIntolerance(int position){
        intolerancesArr.remove(position);
        adapter.notifyDataSetChanged();
        Snackbar snackbar = Snackbar.make(parentLayout, R.string.intolerance_removed, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void addIntolerance(String intolerance){
        intolerancesArr.add(intolerance);
        adapter.notifyDataSetChanged();
    }
}
