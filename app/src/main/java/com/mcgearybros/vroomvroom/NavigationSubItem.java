package com.mcgearybros.vroomvroom;

/**
 * Created by Lewis on 28/08/15.
 */
public class NavigationSubItem {
    public String sectionTitle;
    public String subItemTitle;
    public String contentId;

    public NavigationSubItem(String newTitle, String newContentId, String newSectionTitle){
        this.subItemTitle = newTitle;
        this.contentId = newContentId;
        this.sectionTitle = newSectionTitle;
    }

    public String getSubItemTitle() {
        return subItemTitle;
    }

    public String getContentId() {
        return contentId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }
}
