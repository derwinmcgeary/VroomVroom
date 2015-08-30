package com.mcgearybros.vroomvroom;

import java.util.ArrayList;

/**
 * Created by Lewis on 30/08/15.
 */
public class HtmlContentManager {

    public ArrayList<NavigationSubItem> fullSectionArray;
    public ArrayList<NavigationSubItem> historyArray;

    public HtmlContentManager(ArrayList<NavigationSubItem> navigationSubItems){
        this.fullSectionArray = navigationSubItems;
    }
}
