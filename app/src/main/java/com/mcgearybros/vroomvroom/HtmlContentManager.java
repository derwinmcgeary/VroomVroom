package com.mcgearybros.vroomvroom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Lewis on 30/08/15.
 */
public class HtmlContentManager {

    public ArrayList<NavigationSubItem> fullSectionArray;
    public ArrayList<NavigationSubItem> historyArray;
    public LinkedHashMap<String, NavigationSubItem> fullContentHashMap;
    public LinkedList<String> idList;
    public String currentId;
    public ListIterator<String> iterator;

    public HtmlContentManager(ArrayList<NavigationSubItem> navigationSubItems, LinkedHashMap fullContentHashMap){
        this.fullSectionArray = navigationSubItems;
        this.fullContentHashMap = fullContentHashMap;
        idList = new LinkedList<>(fullContentHashMap.entrySet());
        iterator = idList.listIterator();

    }

    public NavigationSubItem getNextSubItem(){
        NavigationSubItem nextSubItem = fullContentHashMap.get(iterator.next());
        return nextSubItem;
    }

    public NavigationSubItem getPreviousSubItem(){
        NavigationSubItem previousSubItem = fullContentHashMap.get(iterator.previous());
        return previousSubItem;
    }

    public void setCurrentPosition(String currentContentId){
        iterator.set(currentContentId);
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }
}
