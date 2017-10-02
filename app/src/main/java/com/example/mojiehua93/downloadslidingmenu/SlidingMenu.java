package com.example.mojiehua93.downloadslidingmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by MOJIEHUA93 on 2017/10/1.
 */

public class SlidingMenu extends HorizontalScrollView {

    private LinearLayout mLinearLayout;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuRightMargin;
    private boolean mIsMeasure;
    private int mMenuWidth;
    private boolean isOpen;

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu,
                defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i=0; i <= n; i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.SlidingMenu_rightMargin:
                    mMenuRightMargin = a.getDimensionPixelSize(attr, pxToDp(context, 50));
                    break;

                default:break;
            }
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        a.recycle();
    }

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private int pxToDp(Context context, int px){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
                context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!mIsMeasure){
            mLinearLayout = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mLinearLayout.getChildAt(0);
            mContent = (ViewGroup) mLinearLayout.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightMargin;
            mContent.getLayoutParams().width = mScreenWidth;
            mIsMeasure = true;
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            this.scrollTo(mMenuWidth, 0);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2){
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                }else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public void openMenu(){
        if (isOpen)return;
        this.smoothScrollTo(0,0);
    }

    public void closeMenu(){
        if (!isOpen)return;
        this.smoothScrollTo(mMenuWidth, 0);
    }

    public void toggle(){
        if (isOpen){
            closeMenu();
            isOpen = false;
        }else {
            openMenu();
            isOpen = true;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0F / mMenuWidth;

        float leftScale = 1.0F - 0.3F * scale;
        float leftAlpha = 1.0F - 0.4F * scale;
        mMenu.setTranslationX(mMenuWidth * scale * 0.8F);
        mMenu.setAlpha(leftAlpha);
        mMenu.setScaleX(leftScale);
        mMenu.setScaleY(leftScale);

        float rightScale = 0.7F + 0.3F * scale;
        mContent.setPivotX(mMenuWidth * scale);
        mContent.setScaleX(rightScale);
        mContent.setScaleY(rightScale);
    }
}
