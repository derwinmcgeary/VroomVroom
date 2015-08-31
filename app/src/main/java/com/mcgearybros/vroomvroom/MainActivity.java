package com.mcgearybros.vroomvroom;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    WebView htmlDisplay;
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
        htmlDisplay = (WebView) findViewById(R.id.html_display);
        htmlDisplay.getSettings().setJavaScriptEnabled(true);
        currentContentId = "content";
        htmlDisplay.loadUrl("file:///android_asset/" + htmlContentFilename);
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

        navigationDrawerListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get sub item which has been selected
                final NavigationSubItem clickedSubItem = (NavigationSubItem) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                changeToNewSection(clickedSubItem);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    public void changeToNewSection(NavigationSubItem clickedSubItem){
        String newContentId = clickedSubItem.getContentId();
        String newTitle = clickedSubItem.getSubItemTitle();
        //display section matching new id and hide previous section
        htmlDisplay.loadUrl("javascript:var x = document.getElementById('" + newContentId + "').style.display = 'block';" +
                "var y = document.getElementById('" + currentContentId + "').style.display = 'none';");
        //display title of new section in action bar
        getSupportActionBar().setTitle(newTitle);
        //keep track of new id of displayed content
        currentContentId = newContentId;
        contentManager.setCurrentPosition(newContentId);
    }

    public void changeToNextSection(View v){
        changeToNewSection(contentManager.getNextSubItem());
    }

    public void changeToPreviousSection(){
        changeToNewSection(contentManager.getPreviousSubItem());
    }

    @Override
    public void onBackPressed() {
        //When Navigation Drawer is open, back button closes it, otherwise Back behaves normally
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
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
}
