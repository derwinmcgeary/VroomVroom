package com.mcgearybros.vroomvroom;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.util.SparseArray;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Lewis on 29/08/15.
 */
public class MenuXmlPullParser {
    public SparseArray<NavigationMainItem> mainItemsFromXml = new SparseArray<NavigationMainItem>();
    public String mainTag = "MainItem";
    public String subTag = "SubItem";
    public String mainItemTitleAttribute = "title";
    public String subItemTitleAttribute = "title";

    public SparseArray<NavigationMainItem> parse(Activity act, int resourceId) throws XmlPullParserException, IOException{

        XmlResourceParser menuParser = act.getResources().getXml(resourceId);
        menuParser.next();
        int eventType = menuParser.getEventType();
        String currentTag;
        int j=0;
        while (eventType != XmlPullParser.END_DOCUMENT){
            if (eventType == XmlPullParser.START_DOCUMENT){
                eventType = menuParser.next();}
            else if (eventType == XmlPullParser.START_TAG){
                currentTag = menuParser.getName();
                if(currentTag.equals(mainTag)){
                    NavigationMainItem newMainItem = new NavigationMainItem(menuParser.getAttributeValue(null, mainItemTitleAttribute));
                    eventType = menuParser.next();
                    int i=0;
                    while (!menuParser.getName().equals(mainTag)){
                        if (eventType == XmlPullParser.START_TAG){
                            NavigationSubItem newSubItem = new NavigationSubItem (menuParser.getAttributeValue(null, subItemTitleAttribute));
                            newMainItem.subItems.append(i, newSubItem);
                            i++;
                            eventType = menuParser.next();
                        } else{
                            eventType= menuParser.next();
                        }
                    }
                    mainItemsFromXml.append(j, newMainItem);
                    j++;
                    eventType = menuParser.next();
                }
            }
        }
        return mainItemsFromXml;
    }
}
