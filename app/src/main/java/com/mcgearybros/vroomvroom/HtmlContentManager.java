package com.mcgearybros.vroomvroom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

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

    public HtmlContentManager(ArrayList<NavigationSubItem> navigationSubItems, LinkedHashMap<String, NavigationSubItem> fullContentHashMap){
        this.fullSectionArray = navigationSubItems;
        this.fullContentHashMap = fullContentHashMap;
        idList = new LinkedList<>(fullContentHashMap.keySet());
        iterator = idList.listIterator();

    }

    public NavigationSubItem getCurrentSubItem(){
        NavigationSubItem currentSubItem = fullContentHashMap.get(currentId);
        return currentSubItem;
    }

    public NavigationSubItem getNextSubItem() throws NoSuchElementException {
        NavigationSubItem nextSubItem = fullContentHashMap.get(iterator.next());
        if (nextSubItem.getContentId().equals(currentId)){
            nextSubItem = fullContentHashMap.get(iterator.next());
        }
        currentId = nextSubItem.contentId;
        return nextSubItem;
    }

    public NavigationSubItem getPreviousSubItem() throws NoSuchElementException {
        NavigationSubItem previousSubItem = fullContentHashMap.get(iterator.previous());
        if (previousSubItem.getContentId().equals(currentId)){
            previousSubItem = fullContentHashMap.get(iterator.previous());
        }
        currentId = previousSubItem.getContentId();
        return previousSubItem;
    }

    public void setCurrentPosition(String currentContentId){
        currentId = currentContentId;
        //reset iterator
        iterator = idList.listIterator();
        while (!(iterator.next().equals(currentId))){
            //keep looping until at currentId
        }
    }

    public String getCurrentId() {
        return currentId;
    }

}
