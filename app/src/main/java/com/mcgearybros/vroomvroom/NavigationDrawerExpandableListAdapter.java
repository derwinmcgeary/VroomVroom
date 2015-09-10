package com.mcgearybros.vroomvroom;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lewis on 20/08/15.
 */
public class NavigationDrawerExpandableListAdapter extends BaseExpandableListAdapter {

    private final SparseArray<NavigationMainItem> mainItems;
    public LayoutInflater inflater;
    public Activity activity;

    public NavigationDrawerExpandableListAdapter(Activity act, SparseArray<NavigationMainItem> mainItems){
        activity = act;
        this.mainItems = mainItems;
        inflater = act.getLayoutInflater();
    }

    @Override
    public int getGroupCount() {
        return mainItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mainItems.get(groupPosition).subItems.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mainItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mainItems.get(groupPosition).subItems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.navigation_drawer_main_items, parent, false);
        }
        NavigationMainItem thisMainItem = (NavigationMainItem) getGroup(groupPosition);
        CheckedTextView mainItemText = (CheckedTextView) convertView.findViewById(R.id.textView1);
        ImageView groupIndicator = (ImageView) convertView.findViewById(R.id.group_indicator);
        mainItemText.setText(thisMainItem.getMainItemTitle());
        mainItemText.setChecked(isExpanded);
        groupIndicator.setImageResource(isExpanded ? R.drawable.abc_btn_radio_to_on_mtrl_015 : R.drawable.abc_btn_radio_material);
        if (getChildrenCount(groupPosition) > 1) {
            groupIndicator.setVisibility(View.VISIBLE);
        } else {
            groupIndicator.setVisibility(View.INVISIBLE);
        }
                //((CheckedTextView) convertView).setText(thisMainItem.getMainItemTitle());
        //((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final NavigationSubItem thisSubItem = (NavigationSubItem) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.navigation_drawer_sub_items, parent, false);
        }
        TextView text = (TextView) convertView.findViewById(R.id.textView1);
        text.setText(thisSubItem.getSubItemTitle());
        convertView.setClickable(false);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
