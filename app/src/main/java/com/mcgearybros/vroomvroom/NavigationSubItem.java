package com.mcgearybros.vroomvroom;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lewis on 28/08/15.
 */
public class NavigationSubItem implements Parcelable {
    public String sectionTitle;
    public String subItemTitle;
    public String contentId;

    public NavigationSubItem(String newTitle, String newContentId, String newSectionTitle){
        this.subItemTitle = newTitle;
        this.contentId = newContentId;
        this.sectionTitle = newSectionTitle;
    }

    protected NavigationSubItem(Parcel in) {
        sectionTitle = in.readString();
        subItemTitle = in.readString();
        contentId = in.readString();
    }

    public static final Creator<NavigationSubItem> CREATOR = new Creator<NavigationSubItem>() {
        @Override
        public NavigationSubItem createFromParcel(Parcel in) {
            return new NavigationSubItem(in);
        }

        @Override
        public NavigationSubItem[] newArray(int size) {
            return new NavigationSubItem[size];
        }
    };

    public String getSubItemTitle() {
        return subItemTitle;
    }

    public String getContentId() {
        return contentId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sectionTitle);
        dest.writeString(subItemTitle);
        dest.writeString(contentId);
    }
}
