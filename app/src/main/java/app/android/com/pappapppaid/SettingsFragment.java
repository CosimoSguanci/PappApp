package app.android.com.pappapppaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ftinc.scoop.ui.ScoopSettingsActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by shind on 14/04/2017.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        getActivity().setTitle("Impostazioni");
        final LinearLayout parentLayout = (LinearLayout) getActivity().findViewById(R.id.rootView);

        view.findViewById(R.id.themesTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ScoopSettingsActivity.createIntent(getActivity(), "Scegli un tema");
                startActivityForResult(intent, 0);
            }
        });

        view.findViewById(R.id.linkToSite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
                webPageIntent.setData(Uri.parse("http://bluepapajaapps.info"));
                startActivity(webPageIntent);
            }
        });
        return view;
    }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 0){
            ((MainActivity)getActivity()).hasToSetHomeChecked(true);
            getActivity().recreate();
        }
        else{
            ((MainActivity)getActivity()).hasToSetHomeChecked(false);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedIstanceState){


    }






}
