package com.example.dimka.currencyconverter;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.dimka.currencyconverter.adapter.TabsFragmentAdapter;
import com.example.dimka.currencyconverter.dialog.DialogInformation;
import com.example.dimka.currencyconverter.dialog.DialogStyles;
import com.example.dimka.currencyconverter.readers.InformationReader;
import com.example.dimka.currencyconverter.style.ChangeStyles;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LAYOUT = R.layout.main;
    private ChangeStyles styles;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        styles = new ChangeStyles(this);
        setTheme(styles.styleType());
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        initToolbar();
        initNavigationView();
        initTabs();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.menu_converter:
                showConverterTab();
                break;

            case R.id.menu_saved:
                showSavedTab();
                break;

            case R.id.menu_style:
                FragmentManager managerStyle = getSupportFragmentManager();
                DialogStyles styles = new DialogStyles();
                styles.show(managerStyle, "style");
                break;

            case R.id.menu_information:
                FragmentManager managerInfo = getSupportFragmentManager();
                InformationReader informationReader = new InformationReader(getApplicationContext());
                DialogInformation info = DialogInformation.newInstance(informationReader.read());
                info.show(managerInfo, "information");
                break;
        }
        return true;
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsFragmentAdapter adapter =
                new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                        R.string.view_navigation_open,
                        R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showConverterTab() {
        viewPager.setCurrentItem(Constants.TAB_ONE);
    }

    private void showSavedTab() {
        viewPager.setCurrentItem(Constants.TAB_TWO);
    }
}
