package com.mcgearybros.vroomvroom;

/**
 * Created by Lewis on 28/08/15.
 */
public class NavigationSubItem {
    public String subItemTitle;
    public String contentId;

    public NavigationSubItem(String newTitle, String newContentId){
        this.subItemTitle = newTitle;
        this.contentId = newContentId;
    }

    public String getSubItemTitle() {
        return subItemTitle;
    }

    public String getContentId() {
        return contentId;
    }
}
