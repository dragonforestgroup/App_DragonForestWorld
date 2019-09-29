package com.dragonforest.app.dragonforestworld.view.searchedit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.dragonforest.app.dragonforestworld.R;

/**
 * @author 韩龙林
 * @date 2019/8/27 13:58
 */
public class SearchEditText extends android.support.v7.widget.AppCompatEditText {
    private int searchIconId = R.drawable.app_icon_search;
    private Bitmap searchBitmap;
    private Paint mPaint;
    private OnSearchListener onSearchListener;
    private Drawable[] compoundDrawables;

    public SearchEditText(Context context) {
        super(context);
        init(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        setBackgroundResource(R.drawable.app_bg_search);
        setAlpha(0.3f);
        setHint("请输入搜索内容");
        compoundDrawables = getCompoundDrawables();
        setSearchIcon(searchIconId);
        Log.e("TAG", "compoundDrawables.Length:" + compoundDrawables.length);
    }

    public void setSearchIcon(int searchIconId){
        this.searchIconId=searchIconId;
        Bitmap searchIconBitmap = BitmapFactory.decodeResource(getResources(), searchIconId);
        Drawable drawableRight = new BitmapDrawable(searchIconBitmap);
        compoundDrawables[2] = drawableRight;
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
////        drawSearchIcon(canvas);
//    }
//
//    private void drawSearchIcon(Canvas canvas) {
//        searchBitmap = BitmapFactory.decodeResource(getResources(), searchIconId);
//        if (searchBitmap != null) {
//            int height = getHeight();
//            int top = getTop();
//            int bottom = getBottom();
//            int right = getRight();
//            int iconTop = top + getPaddingTop();
//            int iconBottom = bottom + getPaddingBottom();
//            int iconHeight = iconBottom - iconTop;
//            int iconWidth = iconHeight;
//            int iconRight = right - getPaddingRight();
//            int iconLeft = iconRight - iconWidth;
//            Bitmap scaledBitmap = Bitmap.createBitmap(searchBitmap, iconLeft, iconTop, iconWidth, iconHeight);
//            canvas.drawBitmap(scaledBitmap, null, new RectF((float) iconLeft, (float) iconTop, (float) iconRight, (float) iconBottom), mPaint);
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (onSearchListener != null) {
                    String text = getText().toString();
                    onSearchListener.onSearch(text);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public interface OnSearchListener {
        void onSearch(String text);
    }
}
