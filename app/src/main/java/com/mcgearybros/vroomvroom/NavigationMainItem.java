package com.mcgearybros.vroomvroom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lewis on 20/08/15.
 */
public class NavigationMainItem {
    public String navigationMainItem;
    public final List<String> navigationSubItem = new ArrayList<String>();

    public NavigationMainItem(String string){
        this.navigationMainItem = string;
    }

    public String getNavigationMainItem() {
        return navigationMainItem;
    }

    public List<String> getNavigationSubItem() {
        return navigationSubItem;
    }
}
