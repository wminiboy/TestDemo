package com.example.testdemo;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class PreferenceWithHeaders extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add a button to the header list.
        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("Some action");
            setListFooter(button);
        }
        
        addPreferencesFromResource(R.xml.advanced_preferences);
    }

    /**
     * Populate the activity with the top-level headers.
     */
//    @Override
//    public void onBuildHeaders(List<Header> target) {
//        loadHeadersFromResource(R.xml.preference_headers, target);
//    }
    
    @Override
    public void onResume() {
        super.onResume();
        ListView list = (ListView) findViewById(android.R.id.list);
        
        if(list != null){
            list.setPadding(0, 0, 0, 0);
            list.setDivider(null);
        }
    }

    

    @Override
    protected boolean isValidFragment(String fragmentName) {
        // TODO Auto-generated method stub
        return true;
    }
    /**
     * This fragment shows the preferences for the first header.
     */
    public static class Prefs1Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Make sure default values are applied.  In a real app, you would
            // want this in a shared function that is used to retrieve the
            // SharedPreferences wherever they are needed.
            
//            PreferenceManager.setDefaultValues(getActivity(),
//                    R.xml.advanced_preferences, false);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.advanced_preferences);
        }
        
        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                Preference preference) {
            // TODO Auto-generated method stub
            Log.d("yanjun","onPreferenceTreeClick...");
            preference.setEnabled(false);
            return super.onPreferenceTreeClick(preferenceScreen, preference);
            
        }
        
       
    }

//    /**
//     * This fragment contains a second-level set of preference that you
//     * can get to by tapping an item in the first preferences fragment.
//     */
//    public static class Prefs1FragmentInner extends PreferenceFragment {
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            // Can retrieve arguments from preference XML.
//            Log.i("args", "Arguments: " + getArguments());
//
//            // Load the preferences from an XML resource
//            addPreferencesFromResource(R.xml.fragmented_preferences_inner);
//        }
//    }

//    /**
//     * This fragment shows the preferences for the second header.
//     */
//    public static class Prefs2Fragment extends PreferenceFragment {
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            // Can retrieve arguments from headers XML.
//            Log.i("args", "Arguments: " + getArguments());
//
//            // Load the preferences from an XML resource
//            addPreferencesFromResource(R.xml.preference_dependencies);
//        }
//    }
}
