package com.mcgearybros.vroomvroom;

import android.util.SparseArray;

/**
 * Created by Lewis on 20/08/15.
 */
public class NavigationMainItem {
    public String mainItemTitle;
    public final SparseArray<NavigationSubItem> subItems = new SparseArray<NavigationSubItem>();

    public NavigationMainItem(String string){
        this.mainItemTitle = string;
    }

    public String getMainItemTitle() {
        return mainItemTitle;
    }

    public SparseArray<NavigationSubItem> getSubItems() {
        return subItems;
    }
}
