package com.dribbb.sun.dribbblapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.dribbb.sun.dribbblapp.activity.WebActivity;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.databinding.ActivityHomeBinding;
import com.dribbb.sun.dribbblapp.fragment.SelectedFragment;
import com.dribbb.sun.service.api.ApiService;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding>
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int gallery_image_res[] =
            {R.drawable.ic_wall_1, R.drawable.ic_wall_2, R.drawable.ic_wall_3, R.drawable.ic_wall_4};
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        Uri uri = getIntent().getData();
        if(uri != null) {
            Log.d("home", "initViews: " + uri.toString());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewFlipper = (ViewFlipper) navigationView.getHeaderView(0).findViewById(R.id.header_view_flipper) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.home_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.home_viewPager);
        setTabViewPager(tabLayout, viewPager);

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "add shot...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        //登录
        navigationView.getHeaderView(0).findViewById(R.id.author_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WebActivity.class);
                intent.putExtra("url", "https://dribbble.com/oauth/authorize");
                startActivity(intent);
            }
        });
        //登录背景动画
        for(int i = 0; i < gallery_image_res.length; i ++){
            addResourceToFlipper(gallery_image_res[i]);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.author_iv) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setTabViewPager(TabLayout tabLayout, ViewPager viewPager){

        HomePageAdapter  homePageAdapter = new HomePageAdapter(getSupportFragmentManager());

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(SelectedFragment.newInstance(ApiService.POPULAR_SHOTS_URL));
        fragmentList.add(SelectedFragment.newInstance(ApiService.RECENT_SHOTS_URL));
        fragmentList.add(SelectedFragment.newInstance(ApiService.DEBUT_SHOTS_URL));
        fragmentList.add(SelectedFragment.newInstance(ApiService.ANIMATED_SHOTS_URL));
        homePageAdapter.setFragments(fragmentList);

        List<String> titles = new ArrayList<>();
        titles.add("POPULAR");
        titles.add("RECENT");
        titles.add("DEBUTS");
        titles.add("ANIMATED");
        homePageAdapter.setTitles(titles);

        viewPager.setAdapter(homePageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }

    private void addResourceToFlipper(int res){
        ImageView image = new ImageView(getApplicationContext());
        image.setBackgroundResource(res);
        viewFlipper.addView(image);
    }

    private class HomePageAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments;
        List<String> titles;


        public HomePageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setFragments(List<Fragment> fragments) {
            this.fragments = fragments;
        }

        public void setTitles(List<String> titles) {
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments == null ? null : fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
