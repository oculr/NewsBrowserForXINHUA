package com.cjluhz.curriculum.newsbrowserforxinhua;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.cjluhz.curriculum.newsbrowserforxinhua.ui.news.NewsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NewsFragment newsFragment;
    private NavigationView navigationView;
    private Map<Integer, String> ID_XINHUA_MAP;
    private MenuItem DefaultItemSelectItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initmap();
        //设置布局相关
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView newsview = findViewById(R.id.NewsView);
                newsview.smoothScrollToPosition(0);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        DefaultItemSelectItem = navigationView.getMenu().findItem(R.id.politicspro);

        toolbar.setTitle( DefaultItemSelectItem.getTitle() );
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.nav_app_bar_open_drawer_description,R.string.navigation_drawer_close);

        toggle.syncState();
        drawer.addDrawerListener(toggle);


        //设置新闻条目加载
        newsFragment = new NewsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment,newsFragment)
                .commit();


        //监听器切换新闻模块
        navigationView.setCheckedItem(R.id.politicspro);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                toolbar.setTitle(item.getTitle().toString());
                int itemID = item.getItemId();
                newsFragment.remountData(ID_XINHUA_MAP.get(itemID), item.getTitle().toString());
                drawer.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        newsFragment.remountData(ID_XINHUA_MAP.get(DefaultItemSelectItem.getItemId()), DefaultItemSelectItem.getTitle().toString());
    }

    private void initmap(){
        ID_XINHUA_MAP = new HashMap<>();
        ID_XINHUA_MAP.put(R.id.politicspro, "politicspro");
        ID_XINHUA_MAP.put(R.id.worldpro, "worldpro");
        ID_XINHUA_MAP.put(R.id.fortunepro, "fortunepro");
        ID_XINHUA_MAP.put(R.id.techpro, "techpro");
        ID_XINHUA_MAP.put(R.id.culturepro, "culturepro");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}