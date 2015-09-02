package com.mcgearybros.vroomvroom;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Lewis on 31/08/15.
 */
public class HighwayCodeWebView extends WebView {

    Context context;

    public HighwayCodeWebView(Context context) {
        super(context);
        init(context);
    }

    public HighwayCodeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HighwayCodeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (clampedY){
            {
                if (!(canScrollVertically(1)))
                    loadNextSection();
                else if (!(canScrollVertically(-1)))
                    loadPreviousSection();
            }
        }
    }

    private void loadPreviousSection() {
            ((MainActivity)context).changeToPreviousSection();
    }

    private void loadNextSection() {
        ((MainActivity)context).changeToNextSection();

    }

}
