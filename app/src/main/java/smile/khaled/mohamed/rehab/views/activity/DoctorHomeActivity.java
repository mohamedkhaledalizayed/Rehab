package smile.khaled.mohamed.rehab.views.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;

import smile.khaled.mohamed.rehab.R;
import smile.khaled.mohamed.rehab.data.CacheUtils;
import smile.khaled.mohamed.rehab.utils.AppUtils;
import smile.khaled.mohamed.rehab.views.activity.BaseActivity;
import smile.khaled.mohamed.rehab.views.activity.both.AboutUsActivity;
import smile.khaled.mohamed.rehab.views.activity.both.MessagesActivity;
import smile.khaled.mohamed.rehab.views.activity.both.SignInActivity;
import smile.khaled.mohamed.rehab.views.activity.both.TermsActivity;
import smile.khaled.mohamed.rehab.views.fragment.DoctorAddDateFragment;
import smile.khaled.mohamed.rehab.views.fragment.DoctorDatesFragment;
import smile.khaled.mohamed.rehab.views.fragment.DoctorProfileFragment;
import smile.khaled.mohamed.rehab.views.interfaces.doctor.IDoctorDataHandler;

import static smile.khaled.mohamed.rehab.data.Constants.DOCTOR_DATA;


public class DoctorHomeActivity extends BaseActivity  implements NavigationView.OnNavigationItemSelectedListener,
        IDoctorDataHandler {


    private Toolbar toolbar;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_account_circle_black_24dp,
            R.drawable.ic_access_alarms_black_24dp,
            R.drawable.ic_update_black_24dp
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        AppUtils.showSuccessToast(this,CacheUtils.getUserToken(this,DOCTOR_DATA));
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
//


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
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DoctorProfileFragment(), "ملفى");
        adapter.addFragment(new DoctorAddDateFragment(), "مواعيد عملى");
        adapter.addFragment(new DoctorDatesFragment(), "مواعيدى");
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
                startActivity(new Intent(DoctorHomeActivity.this,MessagesActivity.class));
                break;

            case R.id.about_us:
                startActivity(new Intent(DoctorHomeActivity.this,AboutUsActivity.class));
                break;

            case R.id.terms:
                startActivity(new Intent(DoctorHomeActivity.this,TermsActivity.class));
                break;

            case R.id.logout:
                logout();
                break;
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(){


        new iOSDialogBuilder(this)
                .setTitle("Logout")
                .setSubtitle("Do You Want To Exit!")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener(getString(R.string.ok),new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        CacheUtils.clearCache(DoctorHomeActivity.this);
                        startActivity(new Intent(DoctorHomeActivity.this,SignInActivity.class));
                        finish();
                        dialog.dismiss();

                    }
                })
                .setNegativeListener(getString(R.string.cancel), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();

    }

    @Override
    public void onClick(String id) {
        AppUtils.showSuccessToast(this,id);
    }
}
