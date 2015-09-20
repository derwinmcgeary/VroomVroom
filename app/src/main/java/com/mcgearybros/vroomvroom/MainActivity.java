package com.mcgearybros.vroomvroom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;


public class MainActivity extends AppCompatActivity implements WebViewFragment.OnFragmentInteractionListener{

    DrawerLayout mDrawerLayout;
    String htmlContentFilename = "hwcode.html";
    //array to be populated using xml parser then passed on to create navigation menu
    SparseArray<NavigationMainItem> mainItems = new SparseArray<>();
    //id in html file of currently displayed section
    String currentContentId;
    HtmlContentManager contentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parseXhtml();
        initialiseActivityUi();
        initialiseWebview();
        setupNavigationMenu();
    }

    private void parseXhtml() {
        MenuXhtmlPullParser menuXhtmlPullParser = new MenuXhtmlPullParser();
        try {
            InputStream inputStream = getAssets().open(htmlContentFilename);
            //create an array of main navigation objects, each containing sub navigation objects
            mainItems = menuXhtmlPullParser.parse(inputStream);
            contentManager = new HtmlContentManager(menuXhtmlPullParser.getSubItemsFromXhtml(), menuXhtmlPullParser.getSubItemsHashMap());
            inputStream.close();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialiseActivityUi() {
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initialiseWebview() {
        currentContentId = "introduction";
        contentManager.setCurrentPosition(currentContentId);
        WebViewFragment webViewFragment = WebViewFragment.newInstance(contentManager.getCurrrentSubItem());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_container, webViewFragment);
        fragmentTransaction.commit();
    }

    private void setupNavigationMenu() {
        //populate navigation menu using data parsed from the html content page
        final ExpandableListView navigationDrawerListView = (ExpandableListView) findViewById(R.id.navigation_drawer_listView);
        final NavigationDrawerExpandableListAdapter adapter = new NavigationDrawerExpandableListAdapter(this, mainItems);
        navigationDrawerListView.setAdapter(adapter);
        //only allow one menu subgroup to be open at a time
        navigationDrawerListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    navigationDrawerListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        navigationDrawerListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.getExpandableListAdapter().getChildrenCount(groupPosition) == 1){
                    final NavigationSubItem clickedSubItem = (NavigationSubItem) parent.getExpandableListAdapter().getChild(groupPosition, 0);
                    changeToSelectedSection(clickedSubItem);
                    mDrawerLayout.closeDrawers();
                    return true;
                } else {

                    return false;
                }
            }
        });

        navigationDrawerListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get sub item which has been selected

                final NavigationSubItem clickedSubItem = (NavigationSubItem) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                changeToSelectedSection(clickedSubItem);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    public void changeToNewSection(NavigationSubItem clickedSubItem, FragmentTransaction fragmentTransaction){
        WebViewFragment webViewFragment = WebViewFragment.newInstance(clickedSubItem);
        fragmentTransaction.replace(R.id.content_container, webViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void changeToNextSection(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction downTransaction = fragmentManager.beginTransaction();
        downTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_left, R.anim.slide_out_right);
        try {
            changeToNewSection(contentManager.getNextSubItem(), downTransaction);
        } catch (NoSuchElementException e) {
            //no more content
        }
    }

    public void changeToPreviousSection(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction upTransaction = fragmentManager.beginTransaction();
        upTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_left, R.anim.slide_out_right);
        try {
            changeToNewSection(contentManager.getPreviousSubItem(), upTransaction);
        } catch (NoSuchElementException e) {
            //no more content
        }
    }

    public void changeToSelectedSection(NavigationSubItem selectedSubItem){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction selectedTransaction = fragmentManager.beginTransaction();
        selectedTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        changeToNewSection(selectedSubItem, selectedTransaction);
    }

    @Override
    public void onBackPressed() {
        //When Navigation Drawer is open, back button closes it, otherwise Back behaves normally
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else if(getFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void setAppbarTitle (String title) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void updatePositionInContentManager(String contentId) {
        contentManager.setCurrentPosition(contentId);
    }
}
