package app.android.com.pappapppaid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends android.app.Fragment {


    public HomeFragment() {}

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedIstanceState){
        getActivity().setTitle("PappApp");
        List<HomeObject> list = getHomeObjects();
        GridLayoutManager gridLM = new GridLayoutManager(getActivity(), 2);
        //StaggeredGridLayoutManager gridLM = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(gridLM);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(list, getActivity());
        rv.setAdapter(adapter);

    }


    protected List<HomeObject> getHomeObjects(){
        List<HomeObject> objs = new ArrayList<HomeObject>();
        objs.add(new HomeObject(getResources().getString(R.string.breakfast), R.drawable.colazione, "Colazione"));
        objs.add(new HomeObject(getResources().getString(R.string.lunch), R.drawable.pranzo, "Pranzo"));
        objs.add(new HomeObject(getResources().getString(R.string.snack), R.drawable.snack, "Spuntino"));
        objs.add(new HomeObject(getResources().getString(R.string.aperitive), R.drawable.brunch, "Aperitivo"));
        objs.add(new HomeObject(getResources().getString(R.string.dinner), R.drawable.cena, "Cena"));
        objs.add(new HomeObject(getResources().getString(R.string.dessert), R.drawable.dessert, "Dessert"));

        return objs;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
