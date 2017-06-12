package com.example.viktor.sensesmart;

import android.annotation.TargetApi;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

/**
 *  BaseActivity contains the toolbar and navigationbar that
 *  is used by many of the activities in the app.
 */
public class BaseActivity extends AppCompatActivity{

    /*
    * BottomNavigation -> tabs in the bottom
    * Toolbar -> Acitivtybar in the top
    *
    */
    AHBottomNavigation bottomNavigation;
    TabLayout tabLayout;
    Toolbar toolbar;
    ViewPager viewPagerTab;
    NoSwipeViewPager viewPagerPage;
    ViewPagerAdapter viewPagerAdapterPage;
    ViewPagerAdapter viewPagerAdapterTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        findViewsById();
        showPage();
        showTab();
        setNavigationBarProperties();
        tabLayout.setVisibility(View.GONE);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorWhite));

        initializeNavigationBar(1);
        setToolbarTitle("SKELLEFTEÅ");
    }

    //FUNCTION TO FIND ALL THE REQUIRED VIEWS FROM THE XML FILE.
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void findViewsById() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.myBottomNavigation_ID);
        viewPagerPage = (NoSwipeViewPager) findViewById(R.id.viewPagerPage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPagerTab = (ViewPager) findViewById(R.id.viewPagerTab);
    }

    //FUNCTION TO CREATE AND INITIALIZE THE CURRENT POSITION OF THE NAVIGATION BAR.
    protected void initializeNavigationBar(int position) {

        bottomNavigation.setCurrentItem(position);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        showTabbed("SKELLEFTEÅ");
                        break;
                    case 1:
                        showPaged(0,"SKELLEFTEÅ");
                        break;
                    case 2:
                        showPaged(1,"PRISER");
                        break;

                }
                return true;
            }
        });
    }

    //Function to do as it says, set the toolbar title.
    public void setToolbarTitle(String title){
        TextView toolbarText = (TextView)findViewById(R.id.toolbarTitle);
        toolbarText.setText(title);
        bottomNavigation.setBehaviorTranslationEnabled(false);
    }

    public void showTab(){
        if (tabLayout == null) {

            tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            viewPagerAdapterTab = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapterTab.addFragments(new ListFragment(), "LISTA");
            viewPagerAdapterTab.addFragments(new GoogleMapsFragment(), "KARTA");
            viewPagerTab.setAdapter(viewPagerAdapterTab);
            tabLayout.setupWithViewPager(viewPagerTab);
            }
        }
    public void showPage() {
        if(viewPagerAdapterPage == null) {
            viewPagerAdapterPage = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapterPage.addFragments(new ActiveChatFragment(), "CHATT");
            viewPagerAdapterPage.addFragments(new AchievementFragment(), "PRISER");
            viewPagerPage.setAdapter(viewPagerAdapterPage);
        }
    }

    //FUNCTION TO SET THE PROPERTIES OF THE NAVIGATION BAR.
    protected void setNavigationBarProperties() {
        AHBottomNavigationItem mapItem = new AHBottomNavigationItem(getResources()
                .getString(R.string.navigation_map_name), R.drawable.icon_map);
        AHBottomNavigationItem chatItem = new AHBottomNavigationItem(getResources()
                .getString(R.string.navigation_chat_name), R.drawable.icon_chat);
        AHBottomNavigationItem achievementItem = new AHBottomNavigationItem(getResources()
                .getString(R.string.navigation_achievement_name), R.drawable.icon_achievement);

        bottomNavigation.addItem(mapItem);
        bottomNavigation.addItem(chatItem);
        bottomNavigation.addItem(achievementItem);
        bottomNavigation.setTitleTextSize(30, 30);

        //SET PROPERTIES
        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor
                (getApplicationContext(),R.color.colorPrimary));
        bottomNavigation.setInactiveColor(ContextCompat.getColor
                (getApplicationContext(),R.color.colorWhiteTransparent));
        bottomNavigation.setNotificationBackgroundColor(ContextCompat.getColor
                (getApplicationContext(),R.color.colorRed));
        bottomNavigation.setAccentColor(ContextCompat.getColor
                (getApplicationContext(),R.color.colorWhite));
    }

    /*This function is used when the page to be shown is a pageview. That means, every page
    * except the map tab where you have a tab in the toolbar.*/
    public void showPaged(int pos, String title){
        viewPagerPage.setCurrentItem(pos, false);
        viewPagerPage.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.GONE);
        viewPagerTab.setVisibility(View.GONE);
        setToolbarTitle(title);
    }

    /*This function is used when the page to be shown includes a tab.*/
    public void showTabbed(String title){
        viewPagerTab.setVisibility(View.VISIBLE);
        viewPagerPage.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        setToolbarTitle(title);
    }

    /*Thread to continously update the location of the user.*/
    @Override
    public void onStart() {
        super.onStart();
        Thread background = new Thread(new LocationAPI(getApplicationContext(), handler));
        background.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Location loc = (Location)msg.obj;
            ((ActiveChatFragment)(viewPagerAdapterPage.getItem(0))).updateLocation(loc);
            super.handleMessage(msg);
        }
    };
}
