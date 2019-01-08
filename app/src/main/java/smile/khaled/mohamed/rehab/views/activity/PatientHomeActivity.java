package smile.khaled.mohamed.rehab.views.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.gson.Gson;
import com.roger.catloadinglibrary.CatLoadingView;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.service.responses.both.signin.Data;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.utils.CustomTypefaceSpan;
import smile.khaled.mohamed.rehab.views.activity.both.AboutUsActivity;
import smile.khaled.mohamed.rehab.views.activity.both.MessagesActivity;
import smile.khaled.mohamed.rehab.views.activity.both.SignInActivity;
import smile.khaled.mohamed.rehab.views.activity.both.TermsActivity;
import smile.khaled.mohamed.rehab.views.fragment.PatientDatesFragment;
import smile.khaled.mohamed.rehab.views.fragment.PatientFavouriteFragment;
import smile.khaled.mohamed.rehab.views.fragment.PatientSearchFragment;

import static smile.khaled.mohamed.rehab.data.Constants.PATIENT_DATA;

public class PatientHomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_create_black_24dp,
            R.drawable.ic_favorite_border_black_24dp,
            R.drawable.ic_update_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Cairo-Regular.ttf");
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                // Get TextView Element
                if (tabViewChild instanceof TextView) {
                    // change font
                    ((TextView) tabViewChild).setTypeface(tf);
                    // change color
//                    ((TextView) tabViewChild).setTextColor(getResources().getColor(R.color.white));
                    // change size
                    ((TextView) tabViewChild).setTextSize(12);
                    // change padding
                    tabViewChild.setPadding(0, 0, 0, 0);
                    //..... etc...
                }
            }
        }

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();




        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    AppUtils.applyFontToMenuItem(this,subMenuItem);
                }
            }

            //the method we have create in activity
            AppUtils.applyFontToMenuItem(this,mi);
        }
        Gson gson=new Gson();
        AppUtils.showSuccessToast(this,gson.fromJson(CacheUtils.getPatientData(this,PATIENT_DATA),Data.class).getMobile());
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PatientSearchFragment(), "احجز الطبيب");
        adapter.addFragment(new PatientFavouriteFragment(), "المفضلة");
        adapter.addFragment(new PatientDatesFragment(), "مواعيدى");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

                finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.home:
                viewPager.setCurrentItem(0);
                break;

            case R.id.messages:
                startActivity(new Intent(PatientHomeActivity.this,MessagesActivity.class));
                break;

            case R.id.about_us:
                startActivity(new Intent(PatientHomeActivity.this,AboutUsActivity.class));
                break;

            case R.id.terms:
                startActivity(new Intent(PatientHomeActivity.this,TermsActivity.class));
                break;

            case R.id.logout:
                logout();
                break;
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(){
        new TTFancyGifDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do You Want To Exit!")
                .setPositiveBtnText("Ok")
                .setPositiveBtnBackground("#22b573")
                .setNegativeBtnText("Cancel")
                .setNegativeBtnBackground("#c1272d")
                .setGifResource(R.drawable.ic_undraw_loading_frh4)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        CacheUtils.clearCache(PatientHomeActivity.this);
                        startActivity(new Intent(PatientHomeActivity.this,SignInActivity.class));
                        finish();
                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .build();
    }
}
