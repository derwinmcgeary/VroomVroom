package com.mcgearybros.vroomvroom;

import android.app.Activity;
import android.util.SparseArray;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;

/**
 * Created by Lewis on 29/08/15.
 */
public class MenuXhtmlPullParser {

    public SparseArray<NavigationMainItem> mainItemsFromXhtml = new SparseArray<NavigationMainItem>();


    public SparseArray<NavigationMainItem> parse (Activity act, String fileName) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser menuParser = factory.newPullParser();
        menuParser.setInput(act.getAssets().open(fileName), "utf-8");
        menuParser.nextTag();
        int eventType = menuParser.getEventType();
        String currentTag;
        int j = 0;
        while (!(("body").equals(menuParser.getName()) && menuParser.getEventType() == XmlPullParser.END_TAG)){
            currentTag = menuParser.getName();
            while (!("section").equals(menuParser.getName())){
                menuParser.next();
            }
            while (!("h1").equals(menuParser.getName())) {
                menuParser.next();
            }
            NavigationMainItem newMainItem = new NavigationMainItem(menuParser.nextText());
            int i = 0;
            while (!(("section").equals(menuParser.getName()) &&  menuParser.getEventType() == XmlPullParser.END_TAG)) {
                if(("article").equals(menuParser.getName()) && menuParser.getEventType() == XmlPullParser.START_TAG) {
                    String newSubItemId = menuParser.getAttributeValue(null, "id");
                    while (!("h1").equals(menuParser.getName())) {
                        menuParser.next();
                    }
                    NavigationSubItem newSubItem = new NavigationSubItem(menuParser.nextText(), newSubItemId);
                    newMainItem.subItems.append(i, newSubItem);
                    i++;
                    menuParser.next();
                } else {
                    menuParser.next();
                }
            }
            mainItemsFromXhtml.append(j, newMainItem);
            j++;
            menuParser.next();
        }


            return mainItemsFromXhtml;
    }
}
