package app.android.com.pappapppaid;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ftinc.scoop.Scoop;
import com.github.clans.fab.FloatingActionButton;

import java.util.Random;


public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navView;
    private ActionBarDrawerToggle drawerToggle;
    private String currentFragment;
    private boolean hasToChangeFrag;
    private Fragment fragment = null;
    private DrawerLayout drawerLayout;
    private SettingsFragment settingsFragment = null;
    private boolean setHomeChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Scoop.waffleCone()
                .addFlavor("Blue PapaJA", R.style.BluePapaJATheme, true)
                .addFlavor("Dark", R.style.DarkTheme)
                .addFlavor("Red", R.style.RedTheme)
                .addFlavor("Green", R.style.GreenTheme)
                .addFlavor("Amoled", R.style.AmoledTheme)
                .setSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this))
                .initialize();

        Scoop.getInstance().apply(this);



        setContentView(R.layout.activity_main);






        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu_black_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                }
        );
        View header = navView.getHeaderView(0);
        int backChoice = new Random().nextInt(14) + 1;
        switch (backChoice) {
            case 1:
                header.setBackgroundResource(R.mipmap.header1);
                break;
            case 2:
                header.setBackgroundResource(R.mipmap.header2);
                break;
            case 3:
                header.setBackgroundResource(R.mipmap.header3);
                break;
            case 4:
                header.setBackgroundResource(R.mipmap.header4);
                break;
            case 5:
                header.setBackgroundResource(R.mipmap.header5);
                break;
            case 6:
                header.setBackgroundResource(R.mipmap.header6);
                break;
            case 7:
                header.setBackgroundResource(R.mipmap.header7);
                break;
            case 8:
                header.setBackgroundResource(R.mipmap.header8);
                break;
            case 9:
                header.setBackgroundResource(R.mipmap.header9);
                break;
            case 10:
                header.setBackgroundResource(R.mipmap.header10);
                break;
            case 11:
                header.setBackgroundResource(R.mipmap.header11);
                break;
            case 12:
                header.setBackgroundResource(R.mipmap.header12);
                break;
            case 13:
                header.setBackgroundResource(R.mipmap.header13);
                break;
            case 14:
                header.setBackgroundResource(R.mipmap.header14);
                break;

        }
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setVisibility(View.GONE);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, RecipeParamsFragment.newInstance(), "RECIPE_PARAMS").addToBackStack(null).commit();

            }
        });
        currentFragment = "Home";
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, HomeFragment.newInstance()).commit();

    }


    /** Called when leaving the activity */
    @Override
    public void onPause() {

        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();

    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state.
             *  I switch the fragments (if needed) here to improve performance,
             *  loading them after the drawer closing animation.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                RecipeParamsFragment testFrag = (RecipeParamsFragment) getFragmentManager().findFragmentByTag("RECIPE_PARAMS");
                if(testFrag == null)
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);

                Class fragmentClass = HomeFragment.class;
                switch (currentFragment) {
                    case "Home":
                        fragmentClass = HomeFragment.class;
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                        break;
                    case "Fav_Ingr":
                        fragmentClass = FavoriteIngredientsFragment.class;
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                        break;
                    case "Fav_rec":
                        fragmentClass = FavoriteRecipesFragment.class;
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                        break;
                    case "Intolerance_drawer":
                        fragmentClass = IntolerancesFragment.class;
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                        break;
                    case "Settings":
                        findViewById(R.id.fab).setVisibility(View.VISIBLE);
                        break;

                }

                if (hasToChangeFrag) {
                    getFragmentManager().popBackStack("First", android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    if (currentFragment.equals("Settings")) {
                        settingsFragment = new SettingsFragment();
                        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, settingsFragment, "").commit();


                    } else {

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                        getFragmentManager().executePendingTransactions();
                    }
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                if (!currentFragment.equals("Home")) {
                    currentFragment = "Home";
                }
                hasToChangeFrag = true;
                break;
            case R.id.fav_ingredients:
                if (!currentFragment.equals("Fav_Ingr")) {
                    currentFragment = "Fav_Ingr";
                    hasToChangeFrag = true;
                }
                break;
            case R.id.fav_recipes:
                if (!currentFragment.equals("Fav_rec")) {
                    currentFragment = "Fav_rec";
                    hasToChangeFrag = true;
                }
                break;
            case R.id.intolerance_drawer:
                if(!currentFragment.equals("Intolerance_drawer")){
                    currentFragment = "Intolerance_drawer";
                    hasToChangeFrag = true;
                }
                break;
            case R.id.settings_drawer:
                if (!currentFragment.equals("Settings")) {
                    currentFragment = "Settings";
                    hasToChangeFrag = true;
                }
                break;
        }
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
    }

    public void hasToSetHomeChecked(boolean bHas){
        this.setHomeChecked = bHas;
    }

    @Override
    public void onStart(){
        super.onStart();
        if(setHomeChecked)
            navView.getMenu().getItem(0).setChecked(true);
    }




}
