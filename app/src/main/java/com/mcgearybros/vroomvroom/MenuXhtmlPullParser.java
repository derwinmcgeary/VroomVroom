package com.mcgearybros.vroomvroom;

import android.util.SparseArray;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Lewis on 29/08/15.
 */
public class MenuXhtmlPullParser {

    public SparseArray<NavigationMainItem> mainItemsFromXhtml = new SparseArray<>();
    public ArrayList<NavigationSubItem> subItemsFromXhtml = new ArrayList<>();
    public LinkedHashMap<String, NavigationSubItem> subItemsHashMap = new LinkedHashMap<>();

    public SparseArray<NavigationMainItem> parse (InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser menuParser = factory.newPullParser();
        menuParser.setInput(inputStream, "utf-8");
        menuParser.nextTag();
        int j = 0;
        //loop until we get to </body> tag
        while (!(("body").equals(menuParser.getName()) && menuParser.getEventType() == XmlPullParser.END_TAG)){
            //loop until we find a <section> tag
            while (!("section").equals(menuParser.getName())){
                menuParser.next();
            }
            //then find the first <h1> tag
            while (!("h1").equals(menuParser.getName())) {
                menuParser.next();
            }
            //create a new main menu item using the text of the sections h1 tag
            NavigationMainItem newMainItem = new NavigationMainItem(menuParser.nextText());
            int i = 0;
            //loop until we find the </section> tag
            while (!(("section").equals(menuParser.getName()) &&  menuParser.getEventType() == XmlPullParser.END_TAG)) {
                //if we hit an <article> tag, set up a new sub item, this will repeat for all <article>
                //tags in the section
                if(("article").equals(menuParser.getName()) && menuParser.getEventType() == XmlPullParser.START_TAG) {
                    //get the id of the <article>
                    String newSubItemId = menuParser.getAttributeValue(null, "id");
                    //find the first <h1> tag
                    while (!("h1").equals(menuParser.getName())) {
                        menuParser.next();
                    }
                    //create a new sub menu item using the text of the articles h1 tag
                    NavigationSubItem newSubItem = new NavigationSubItem(menuParser.nextText(), newSubItemId);
                    //add the sub item to the main item
                    newMainItem.subItems.append(i, newSubItem);
                    //collect each sub item in an array
                    subItemsFromXhtml.add(newSubItem);
                    subItemsHashMap.put(newSubItemId, newSubItem);
                    i++;
                    menuParser.next();
                } else {
                    menuParser.next();
                }
            }
            //add the new main item with associated  sub items to our array
            mainItemsFromXhtml.append(j, newMainItem);
            j++;
            menuParser.next();
        }


            return mainItemsFromXhtml;
    }

    public ArrayList<NavigationSubItem> getSubItemsFromXhtml() {
        return subItemsFromXhtml;
    }

    public LinkedHashMap<String, NavigationSubItem> getSubItemsHashMap() {
        return subItemsHashMap;
    }
}
