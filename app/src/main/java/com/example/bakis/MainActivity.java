package com.example.bakis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int test = 20;

        //drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //bottom nav
        /*BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);*/

        //pradinis langas
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AllCourses()).commit();
            navigationView.setCheckedItem(R.id.nav_allCourses);
        }
    }

    /*private  BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.bnav_allCourses:
                            selectedFragment = new AllCourses();
                            break;
                        case R.id.bnav_likedCourses:
                            selectedFragment = new LikedCourses();
                            break;
                        case R.id.bnav_statistics:
                            selectedFragment = new Statistics();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) { // menu pasirinkimai (kas padaroma paspaudus)
            case R.id.nav_allCourses:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AllCourses()).commit();
                break;
            case R.id.nav_createdCourses:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CreatedCourses()).commit();
                break;
            case R.id.nav_likedCourses:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LikedCourses()).commit();
                break;
            case R.id.nav_statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Statistics()).commit();
                break;
            case R.id.nav_allExercises:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AllExercises()).commit();
                break;
            case R.id.nav_createdExercises:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CreatedExercises()).commit();
                break;
            case R.id.changeLanguage:
                //handled with showPopup
                break;
            case R.id.reminders:
                Toast.makeText(this, "reminders yra", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login:
                Toast.makeText(this, "login yra", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) { // end if menu is on left side
            drawer.closeDrawer(GravityCompat.END); // end if menu is on left side
        } else {
            super.onBackPressed();
        }
    }
}