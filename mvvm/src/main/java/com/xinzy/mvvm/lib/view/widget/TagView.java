package com.xinzy.mvvm.lib.view.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.customview.widget.ViewDragHelper;

import com.xinzy.mvvm.lib.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.xinzy.mvvm.lib.util.Utils.dp2px;
import static com.xinzy.mvvm.lib.util.Utils.sp2px;

public class TagView extends ViewGroup {

    /**
     * Vertical interval, default 5(dp)
     */
    private int mVerticalInterval;

    /**
     * The list to store the tags color info
     */
    private List<int[]> mColorArrayList;

    /**
     * Horizontal interval, default 5(dp)
     */
    private int mHorizontalInterval;

    /**
     * TagContainerLayout border width(default 0.5dp)
     */
    private float mBorderWidth = 0f;

    /**
     * TagContainerLayout border radius(default 10.0dp)
     */
    private float mBorderRadius = 0f;

    /**
     * The sensitive of the ViewDragHelper(default 1.0f, normal)
     */
    private float mSensitivity = 1.0f;

    /**
     * ItemView average height
     */
    private int mChildHeight;

    /**
     * TagContainerLayout border color(default #22FF0000)
     */
    private int mBorderColor = 0;

    /**
     * TagContainerLayout background color(default #11FF0000)
     */
    private int mBackgroundColor = 0;

    /**
     * The container layout gravity(default left)
     */
    private int mGravity = Gravity.LEFT;

    /**
     * The max line count of TagContainerLayout
     */
    private int mMaxLines = 0;

    /**
     * The max length for ItemView(default max length 23)
     */
    private int mTagMaxLength = 23;

    /**
     * ItemView Border width(default 0.5dp)
     */
    private float mTagBorderWidth = 0.5f;

    /**
     * ItemView Border radius(default 15.0dp)
     */
    private float mTagBorderRadius = 15.0f;

    /**
     * ItemView Text size(default 14sp)
     */
    private float mTagTextSize = 14;

    /**
     * Text direction(support:TEXT_DIRECTION_RTL & TEXT_DIRECTION_LTR, default TEXT_DIRECTION_LTR)
     */
    private int mTagTextDirection = View.TEXT_DIRECTION_LTR;

    /**
     * Horizontal padding for ItemView, include left & right padding(left & right padding are equal, default 10dp)
     */
    private int mTagHorizontalPadding = 10;

    /**
     * Vertical padding for ItemView, include top & bottom padding(top & bottom padding are equal, default 8dp)
     */
    private int mTagVerticalPadding = 2;

    /**
     * ItemView border color(default #88F44336)
     */
    private int mTagBorderColor = Color.parseColor("#88F44336");

    /**
     * ItemView background color(default #33F44336)
     */
    private int mTagBackgroundColor = Color.parseColor("#33F44336");

    /**
     * Selected ItemView background color(default #33FF7669)
     */
    private int mSelectedTagBackgroundColor = Color.parseColor("#33FF7669");

    /**
     * ItemView text color(default #FF666666)
     */
    private int mTagTextColor = Color.parseColor("#FF666666");

    /**
     * ItemView typeface
     */
    private Typeface mTagTypeface = Typeface.DEFAULT;

    /**
     * Whether ItemView can clickable(default unclickable)
     */
    private boolean isTagViewClickable;

    /**
     * Whether ItemView can selectable(default unselectable)
     */
    private boolean isTagViewSelectable;

    /**
     * Tags
     */
    private List<String> mTags;

    /**
     * Default image for new tags
     */
    private int mDefaultImageDrawableID = -1;

    /**
     * Can drag ItemView(default false)
     */
    private boolean mDragEnable;

    /**
     * ItemView drag state(default STATE_IDLE)
     */
    private int mTagViewState = ViewDragHelper.STATE_IDLE;

    /**
     * The distance between baseline and descent(default 2.75dp)
     */
    private float mTagBdDistance = 0f;

    /**
     * OnTagClickListener for ItemView
     */
    private OnTagClickListener mOnTagClickListener;

    /**
     * Whether to support 'letters show with RTL(eg: Android to diordnA)' style(default false)
     */
    private boolean mTagSupportLettersRTL = false;

    private Paint mPaint;

    private RectF mRectF;

    private ViewDragHelper mViewDragHelper;

    private List<View> mChildViews;

    private int[] mViewPos;

    /**
     * View theme(default PURE_CYAN)
     */
    private int mTheme = ColorFactory.PURE_CYAN;

    /**
     * Default interval(dp)
     */
    private static final int DEFAULT_INTERVAL = 5;

    /**
     * Default tag min length
     */
    private static final int TAG_MIN_LENGTH = 3;

    /**
     * The ripple effect duration(In milliseconds, default 1000ms)
     */
    private int mRippleDuration = 1000;

    /**
     * The ripple effect color(default #EEEEEE)
     */
    private int mRippleColor;

    /**
     * The ripple effect color alpha(the value may between 0 - 255, default 128)
     */
    private int mRippleAlpha = 128;

    /**
     * Enable draw cross icon(default false)
     */
    private boolean mEnableCross = false;

    /**
     * The cross area width(your cross click area, default equal to the ItemView's height)
     */
    private float mCrossAreaWidth = 0.0f;

    /**
     * The padding of the cross area(default 10dp)
     */
    private float mCrossAreaPadding = 10.0f;

    /**
     * The cross icon color(default Color.BLACK)
     */
    private int mCrossColor = Color.BLACK;

    /**
     * The cross line width(default 1dp)
     */
    private float mCrossLineWidth = 1.0f;

    /**
     * ItemView background resource
     */
    private int mTagBackgroundResource;

    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TagView,
                defStyleAttr, 0);
        mVerticalInterval = (int) attributes.getDimension(R.styleable.TagView_verticalSpace,
                dp2px(context, DEFAULT_INTERVAL));
        mHorizontalInterval = (int) attributes.getDimension(R.styleable.TagView_horizontalSpace,
                dp2px(context, DEFAULT_INTERVAL));
        mBorderWidth = attributes.getDimension(R.styleable.TagView_borderWidth,
                dp2px(context, mBorderWidth));
        mBorderRadius = attributes.getDimension(R.styleable.TagView_borderRadius,
                dp2px(context, mBorderRadius));
        mTagBdDistance = attributes.getDimension(R.styleable.TagView_itemBorderWidth,
                dp2px(context, mTagBdDistance));
        mBorderColor = attributes.getColor(R.styleable.TagView_borderColor,
                mBorderColor);
        mBackgroundColor = attributes.getColor(R.styleable.TagView_backgroundColor,
                mBackgroundColor);
        mDragEnable = attributes.getBoolean(R.styleable.TagView_dragEnable, false);
        mSensitivity = attributes.getFloat(R.styleable.TagView_dragSensitivity,
                mSensitivity);
        mGravity = attributes.getInt(R.styleable.TagView_gravity, mGravity);
        mMaxLines = attributes.getInt(R.styleable.TagView_maxLines, mMaxLines);
        mTagMaxLength = attributes.getInt(R.styleable.TagView_itemMaxLength, mTagMaxLength);
        mTheme = attributes.getInt(R.styleable.TagView_itemTheme, mTheme);
        mTagBorderWidth = attributes.getDimension(R.styleable.TagView_itemBorderWidth,
                dp2px(context, mTagBorderWidth));
        mTagBorderRadius = attributes.getDimension(R.styleable.TagView_itemRadius, dp2px(context, mTagBorderRadius));
        mTagHorizontalPadding = (int) attributes.getDimension(R.styleable.TagView_itemHorizontalPadding,
                dp2px(context, mTagHorizontalPadding));
        mTagVerticalPadding = (int) attributes.getDimension(R.styleable.TagView_itemVerticalPadding,
                dp2px(context, mTagVerticalPadding));
        mTagTextSize = attributes.getDimension(R.styleable.TagView_itemTextSize, sp2px(context, mTagTextSize));
        mTagBorderColor = attributes.getColor(R.styleable.TagView_itemBorderColor, mTagBorderColor);
        mTagBackgroundColor = attributes.getColor(R.styleable.TagView_itemBackgroundColor, mTagBackgroundColor);
        mTagTextColor = attributes.getColor(R.styleable.TagView_itemTextColor, mTagTextColor);
        mTagTextDirection = attributes.getInt(R.styleable.TagView_itemTextDirection, mTagTextDirection);
        isTagViewClickable = attributes.getBoolean(R.styleable.TagView_itemClickable, false);
        isTagViewSelectable = attributes.getBoolean(R.styleable.TagView_itemSelectable, false);
        mRippleColor = attributes.getColor(R.styleable.TagView_itemRippleColor, Color.parseColor("#EEEEEE"));
        mRippleAlpha = attributes.getInteger(R.styleable.TagView_itemRippleAlpha, mRippleAlpha);
        mRippleDuration = attributes.getInteger(R.styleable.TagView_itemRippleDuration, mRippleDuration);
        mEnableCross = attributes.getBoolean(R.styleable.TagView_itemEnableCross, mEnableCross);
        mCrossAreaWidth = attributes.getDimension(R.styleable.TagView_itemCrossWidth, dp2px(context, mCrossAreaWidth));
        mCrossAreaPadding = attributes.getDimension(R.styleable.TagView_itemCrossAreaPadding,
                dp2px(context, mCrossAreaPadding));
        mCrossColor = attributes.getColor(R.styleable.TagView_itemCrossColor, mCrossColor);
        mCrossLineWidth = attributes.getDimension(R.styleable.TagView_itemCrossLineWidth,
                dp2px(context, mCrossLineWidth));
        mTagSupportLettersRTL = attributes.getBoolean(R.styleable.TagView_itemSupportLettersRlt,
                mTagSupportLettersRTL);
        mTagBackgroundResource = attributes.getResourceId(R.styleable.TagView_itemBackground,
                mTagBackgroundResource);
        attributes.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
        mChildViews = new ArrayList<>();
        mViewDragHelper = ViewDragHelper.create(this, mSensitivity, new DragHelperCallBack());
        setWillNotDraw(false);
        setTagMaxLength(mTagMaxLength);
        setTagHorizontalPadding(mTagHorizontalPadding);
        setTagVerticalPadding(mTagVerticalPadding);

        if (isInEditMode()) {
            addTag("sample tag");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        final int childCount = getChildCount();
        int lines = childCount == 0 ? 0 : getChildLines(childCount);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (heightSpecMode == MeasureSpec.AT_MOST
                || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(widthSpecSize, (mVerticalInterval + mChildHeight) * lines
                    - mVerticalInterval + getPaddingTop() + getPaddingBottom());
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(0, 0, w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount;
        if ((childCount = getChildCount()) <= 0) {
            return;
        }
        int availableW = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int curRight = getMeasuredWidth() - getPaddingRight();
        int curTop = getPaddingTop();
        int curLeft = getPaddingLeft();
        int sPos = 0;
        mViewPos = new int[childCount * 2];

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                int width = childView.getMeasuredWidth();
                if (mGravity == Gravity.RIGHT) {
                    if (curRight - width < getPaddingLeft()) {
                        curRight = getMeasuredWidth() - getPaddingRight();
                        curTop += mChildHeight + mVerticalInterval;
                    }
                    mViewPos[i * 2] = curRight - width;
                    mViewPos[i * 2 + 1] = curTop;
                    curRight -= width + mHorizontalInterval;
                } else if (mGravity == Gravity.CENTER) {
                    if (curLeft + width - getPaddingLeft() > availableW) {
                        int leftW = getMeasuredWidth() - mViewPos[(i - 1) * 2]
                                - getChildAt(i - 1).getMeasuredWidth() - getPaddingRight();
                        for (int j = sPos; j < i; j++) {
                            mViewPos[j * 2] = mViewPos[j * 2] + leftW / 2;
                        }
                        sPos = i;
                        curLeft = getPaddingLeft();
                        curTop += mChildHeight + mVerticalInterval;
                    }
                    mViewPos[i * 2] = curLeft;
                    mViewPos[i * 2 + 1] = curTop;
                    curLeft += width + mHorizontalInterval;

                    if (i == childCount - 1) {
                        int leftW = getMeasuredWidth() - mViewPos[i * 2]
                                - childView.getMeasuredWidth() - getPaddingRight();
                        for (int j = sPos; j < childCount; j++) {
                            mViewPos[j * 2] = mViewPos[j * 2] + leftW / 2;
                        }
                    }
                } else {
                    if (curLeft + width - getPaddingLeft() > availableW) {
                        curLeft = getPaddingLeft();
                        curTop += mChildHeight + mVerticalInterval;
                    }
                    mViewPos[i * 2] = curLeft;
                    mViewPos[i * 2 + 1] = curTop;
                    curLeft += width + mHorizontalInterval;
                }
            }
        }

        // layout all child views
        for (int i = 0; i < mViewPos.length / 2; i++) {
            View childView = getChildAt(i);
            childView.layout(mViewPos[i * 2], mViewPos[i * 2 + 1],
                    mViewPos[i * 2] + childView.getMeasuredWidth(),
                    mViewPos[i * 2 + 1] + mChildHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mBorderColor);
        canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            requestLayout();
        }
    }

    private int getChildLines(int childCount) {
        int availableW = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int lines = 1;
        for (int i = 0, curLineW = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int dis = childView.getMeasuredWidth() + mHorizontalInterval;
            int height = childView.getMeasuredHeight();
            mChildHeight = i == 0 ? height : Math.min(mChildHeight, height);
            curLineW += dis;
            if (curLineW - mHorizontalInterval > availableW) {
                lines++;
                curLineW = dis;
            }
        }

        return mMaxLines <= 0 ? lines : mMaxLines;
    }

    private int[] onUpdateColorFactory() {
        int[] colors;
        if (mTheme == ColorFactory.RANDOM) {
            colors = ColorFactory.onRandomBuild();
        } else if (mTheme == ColorFactory.PURE_TEAL) {
            colors = ColorFactory.onPureBuild(ColorFactory.PURE_COLOR.TEAL);
        } else if (mTheme == ColorFactory.PURE_CYAN) {
            colors = ColorFactory.onPureBuild(ColorFactory.PURE_COLOR.CYAN);
        } else {
            colors = new int[]{mTagBackgroundColor, mTagBorderColor, mTagTextColor, mSelectedTagBackgroundColor};
        }
        return colors;
    }

    private void onSetTag() {
        if (mTags == null) {
            throw new RuntimeException("NullPointer exception!");
        }
        removeAllTags();
        if (mTags.size() == 0) {
            return;
        }
        for (int i = 0; i < mTags.size(); i++) {
            onAddTag(mTags.get(i), mChildViews.size());
        }
        postInvalidate();
    }

    private void onAddTag(String text, int position) {
        if (position < 0 || position > mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        ItemView tagView;
        if (mDefaultImageDrawableID != -1) {
            tagView = new ItemView(getContext(), text, mDefaultImageDrawableID);
        } else {
            tagView = new ItemView(getContext(), text);
        }
        initTagView(tagView, position);
        mChildViews.add(position, tagView);
        if (position < mChildViews.size()) {
            for (int i = position; i < mChildViews.size(); i++) {
                mChildViews.get(i).setTag(i);
            }
        } else {
            tagView.setTag(position);
        }
        addView(tagView, position);
    }

    private void initTagView(ItemView tagView, int position) {
        int[] colors;
        if (mColorArrayList != null && mColorArrayList.size() > 0) {
            if (mColorArrayList.size() == mTags.size() &&
                    mColorArrayList.get(position).length >= 4) {
                colors = mColorArrayList.get(position);
            } else {
                throw new RuntimeException("Illegal color list!");
            }
        } else {
            colors = onUpdateColorFactory();
        }

        tagView.setTagBackgroundColor(colors[0]);
        tagView.setTagBorderColor(colors[1]);
        tagView.setTagTextColor(colors[2]);
        tagView.setTagSelectedBackgroundColor(colors[3]);
        tagView.setTagMaxLength(mTagMaxLength);
        tagView.setTextDirection(mTagTextDirection);
        tagView.setTypeface(mTagTypeface);
        tagView.setBorderWidth(mTagBorderWidth);
        tagView.setBorderRadius(mTagBorderRadius);
        tagView.setTextSize(mTagTextSize);
        tagView.setHorizontalPadding(mTagHorizontalPadding);
        tagView.setVerticalPadding(mTagVerticalPadding);
        tagView.setIsViewClickable(isTagViewClickable);
        tagView.setIsViewSelectable(isTagViewSelectable);
        tagView.setBdDistance(mTagBdDistance);
        tagView.setOnTagClickListener(mOnTagClickListener);
        tagView.setRippleAlpha(mRippleAlpha);
        tagView.setRippleColor(mRippleColor);
        tagView.setRippleDuration(mRippleDuration);
        tagView.setEnableCross(mEnableCross);
        tagView.setCrossAreaWidth(mCrossAreaWidth);
        tagView.setCrossAreaPadding(mCrossAreaPadding);
        tagView.setCrossColor(mCrossColor);
        tagView.setCrossLineWidth(mCrossLineWidth);
        tagView.setTagSupportLettersRTL(mTagSupportLettersRTL);
        tagView.setBackgroundResource(mTagBackgroundResource);
    }

    private void invalidateTags() {
        for (View view : mChildViews) {
            final TagView tagView = (TagView) view;
            tagView.setOnTagClickListener(mOnTagClickListener);
        }
    }

    private void onRemoveTag(int position) {
        if (position < 0 || position >= mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        mChildViews.remove(position);
        removeViewAt(position);
        for (int i = position; i < mChildViews.size(); i++) {
            mChildViews.get(i).setTag(i);
        }
        // TODO, make removed view null?
    }

    private void onRemoveConsecutiveTags(List<Integer> positions) {
        int smallestPosition = Collections.min(positions);
        for (int position : positions) {
            if (position < 0 || position >= mChildViews.size()) {
                throw new RuntimeException("Illegal position!");
            }
            mChildViews.remove(smallestPosition);
            removeViewAt(smallestPosition);
        }
        for (int i = smallestPosition; i < mChildViews.size(); i++) {
            mChildViews.get(i).setTag(i);
        }
        // TODO, make removed view null?
    }

    private int[] onGetNewPosition(View view) {
        int left = view.getLeft();
        int top = view.getTop();
        int bestMatchLeft = mViewPos[(int) view.getTag() * 2];
        int bestMatchTop = mViewPos[(int) view.getTag() * 2 + 1];
        int tmpTopDis = Math.abs(top - bestMatchTop);
        for (int i = 0; i < mViewPos.length / 2; i++) {
            if (Math.abs(top - mViewPos[i * 2 + 1]) < tmpTopDis) {
                bestMatchTop = mViewPos[i * 2 + 1];
                tmpTopDis = Math.abs(top - mViewPos[i * 2 + 1]);
            }
        }
        int rowChildCount = 0;
        int tmpLeftDis = 0;
        for (int i = 0; i < mViewPos.length / 2; i++) {
            if (mViewPos[i * 2 + 1] == bestMatchTop) {
                if (rowChildCount == 0) {
                    bestMatchLeft = mViewPos[i * 2];
                    tmpLeftDis = Math.abs(left - bestMatchLeft);
                } else {
                    if (Math.abs(left - mViewPos[i * 2]) < tmpLeftDis) {
                        bestMatchLeft = mViewPos[i * 2];
                        tmpLeftDis = Math.abs(left - bestMatchLeft);
                    }
                }
                rowChildCount++;
            }
        }
        return new int[]{bestMatchLeft, bestMatchTop};
    }

    private int onGetCoordinateReferPos(int left, int top) {
        int pos = 0;
        for (int i = 0; i < mViewPos.length / 2; i++) {
            if (left == mViewPos[i * 2] && top == mViewPos[i * 2 + 1]) {
                pos = i;
            }
        }
        return pos;
    }

    private void onChangeView(View view, int newPos, int originPos) {
        mChildViews.remove(originPos);
        mChildViews.add(newPos, view);
        for (View child : mChildViews) {
            child.setTag(mChildViews.indexOf(child));
        }

        removeViewAt(originPos);
        addView(view, newPos);
    }

    private int ceilTagBorderWidth() {
        return (int) Math.ceil(mTagBorderWidth);
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            mTagViewState = state;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            requestDisallowInterceptTouchEvent(true);
            return mDragEnable;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int leftX = getPaddingLeft();
            final int rightX = getWidth() - child.getWidth() - getPaddingRight();
            return Math.min(Math.max(left, leftX), rightX);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topY = getPaddingTop();
            final int bottomY = getHeight() - child.getHeight() - getPaddingBottom();
            return Math.min(Math.max(top, topY), bottomY);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            requestDisallowInterceptTouchEvent(false);
            int[] pos = onGetNewPosition(releasedChild);
            int posRefer = onGetCoordinateReferPos(pos[0], pos[1]);
            onChangeView(releasedChild, posRefer, (int) releasedChild.getTag());
            mViewDragHelper.settleCapturedViewAt(pos[0], pos[1]);
            invalidate();
        }
    }

    /**
     * Get current drag view state.
     *
     * @return
     */
    public int getTagViewState() {
        return mTagViewState;
    }

    /**
     * Get TagView text baseline and descent distance.
     *
     * @return
     */
    public float getTagBdDistance() {
        return mTagBdDistance;
    }

    /**
     * Set TagView text baseline and descent distance.
     *
     * @param tagBdDistance
     */
    public void setTagBdDistance(float tagBdDistance) {
        this.mTagBdDistance = dp2px(getContext(), tagBdDistance);
    }

    /**
     * Set tags
     *
     * @param tags
     */
    public void setTags(List<String> tags) {
        mTags = tags;
        onSetTag();
    }

    /**
     * Set tags with own color
     *
     * @param tags
     * @param colorArrayList
     */
    public void setTags(List<String> tags, List<int[]> colorArrayList) {
        mTags = tags;
        mColorArrayList = colorArrayList;
        onSetTag();
    }

    /**
     * Set tags
     *
     * @param tags
     */
    public void setTags(String... tags) {
        mTags = Arrays.asList(tags);
        onSetTag();
    }

    /**
     * Inserts the specified TagView into this ContainerLayout at the end.
     *
     * @param text
     */
    public void addTag(String text) {
        addTag(text, mChildViews.size());
    }

    /**
     * Inserts the specified TagView into this ContainerLayout at the specified location.
     * The TagView is inserted before the current element at the specified location.
     *
     * @param text
     * @param position
     */
    public void addTag(String text, int position) {
        onAddTag(text, position);
        postInvalidate();
    }

    /**
     * Remove a TagView in specified position.
     *
     * @param position
     */
    public void removeTag(int position) {
        onRemoveTag(position);
        postInvalidate();
    }

    /**
     * Remove TagView in multiple consecutive positions.
     */
    public void removeConsecutiveTags(List<Integer> positions) {
        onRemoveConsecutiveTags(positions);
        postInvalidate();
    }

    /**
     * Remove all TagViews.
     */
    public void removeAllTags() {
        mChildViews.clear();
        removeAllViews();
        postInvalidate();
    }

    /**
     * Set OnTagClickListener for TagView.
     *
     * @param listener
     */
    public void setOnTagClickListener(OnTagClickListener listener) {
        mOnTagClickListener = listener;
        invalidateTags();
    }

    /**
     * Toggle select a tag
     *
     * @param position
     */
    public void toggleSelectTagView(int position) {
        if (isTagViewSelectable) {
            ItemView tagView = ((ItemView) mChildViews.get(position));
            if (tagView.getIsViewSelected()) {
                tagView.deselectView();
            } else {
                tagView.selectView();
            }
        }
    }

    /**
     * Select a tag
     *
     * @param position
     */
    public void selectTagView(int position) {
        if (isTagViewSelectable)
            ((ItemView) mChildViews.get(position)).selectView();
    }

    /**
     * Deselect a tag
     *
     * @param position
     */
    public void deselectTagView(int position) {
        if (isTagViewSelectable)
            ((ItemView) mChildViews.get(position)).deselectView();
    }

    /**
     * Return selected ItemView positions
     *
     * @return list of selected positions
     */
    public List<Integer> getSelectedTagViewPositions() {
        List<Integer> selectedPositions = new ArrayList<>();
        for (int i = 0; i < mChildViews.size(); i++) {
            if (((ItemView) mChildViews.get(i)).getIsViewSelected()) {
                selectedPositions.add(i);
            }
        }
        return selectedPositions;
    }

    /**
     * Return selected ItemView text
     *
     * @return list of selected tag text
     */
    public List<String> getSelectedTagViewText() {
        List<String> selectedTagText = new ArrayList<>();
        for (int i = 0; i < mChildViews.size(); i++) {
            ItemView tagView = (ItemView) mChildViews.get(i);
            if ((tagView.getIsViewSelected())) {
                selectedTagText.add(tagView.getText());
            }
        }
        return selectedTagText;
    }

    /**
     * Return number of child tags
     *
     * @return size
     */
    public int size() {
        return mChildViews.size();
    }

    /**
     * Get ItemView text.
     *
     * @param position
     * @return
     */
    public String getTagText(int position) {
        return ((ItemView) mChildViews.get(position)).getText();
    }

    /**
     * Get a string list for all tags in TagContainerLayout.
     *
     * @return
     */
    public List<String> getTags() {
        List<String> tmpList = new ArrayList<String>();
        for (View view : mChildViews) {
            if (view instanceof ItemView) {
                tmpList.add(((ItemView) view).getText());
            }
        }
        return tmpList;
    }

    /**
     * Set whether the child view can be dragged.
     *
     * @param enable
     */
    public void setDragEnable(boolean enable) {
        this.mDragEnable = enable;
    }

    /**
     * Get current view is drag enable attribute.
     *
     * @return
     */
    public boolean getDragEnable() {
        return mDragEnable;
    }

    /**
     * Set vertical interval
     *
     * @param interval
     */
    public void setVerticalInterval(float interval) {
        mVerticalInterval = (int) dp2px(getContext(), interval);
        postInvalidate();
    }

    /**
     * Get vertical interval in this view.
     *
     * @return
     */
    public int getVerticalInterval() {
        return mVerticalInterval;
    }

    /**
     * Set horizontal interval.
     *
     * @param interval
     */
    public void setHorizontalInterval(float interval) {
        mHorizontalInterval = (int) dp2px(getContext(), interval);
        postInvalidate();
    }

    /**
     * Get horizontal interval in this view.
     *
     * @return
     */
    public int getHorizontalInterval() {
        return mHorizontalInterval;
    }

    /**
     * Get TagContainerLayout border width.
     *
     * @return
     */
    public float getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * Set TagContainerLayout border width.
     *
     * @param width
     */
    public void setBorderWidth(float width) {
        this.mBorderWidth = width;
    }

    /**
     * Get TagContainerLayout border radius.
     *
     * @return
     */
    public float getBorderRadius() {
        return mBorderRadius;
    }

    /**
     * Set TagContainerLayout border radius.
     *
     * @param radius
     */
    public void setBorderRadius(float radius) {
        this.mBorderRadius = radius;
    }

    /**
     * Get TagContainerLayout border color.
     *
     * @return
     */
    public int getBorderColor() {
        return mBorderColor;
    }

    /**
     * Set TagContainerLayout border color.
     *
     * @param color
     */
    public void setBorderColor(int color) {
        this.mBorderColor = color;
    }

    /**
     * Get TagContainerLayout background color.
     *
     * @return
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * Set TagContainerLayout background color.
     *
     * @param color
     */
    public void setBackgroundColor(int color) {
        this.mBackgroundColor = color;
    }

    /**
     * Get container layout gravity.
     *
     * @return
     */
    public int getGravity() {
        return mGravity;
    }

    /**
     * Set container layout gravity.
     *
     * @param gravity
     */
    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    /**
     * Get TagContainerLayout ViewDragHelper sensitivity.
     *
     * @return
     */
    public float getSensitivity() {
        return mSensitivity;
    }

    /**
     * Set TagContainerLayout ViewDragHelper sensitivity.
     *
     * @param sensitivity
     */
    public void setSensitivity(float sensitivity) {
        this.mSensitivity = sensitivity;
    }

    /**
     * Get default tag image
     *
     * @return
     */
    public int getDefaultImageDrawableID() {
        return mDefaultImageDrawableID;
    }

    /**
     * Set default image for tags.
     *
     * @param imageID
     */
    public void setDefaultImageDrawableID(int imageID) {
        this.mDefaultImageDrawableID = imageID;
    }

    /**
     * Set max line count for TagContainerLayout
     *
     * @param maxLines max line count
     */
    public void setMaxLines(int maxLines) {
        mMaxLines = maxLines;
        postInvalidate();
    }

    /**
     * Get TagContainerLayout's max lines
     *
     * @return maxLines
     */
    public int getMaxLines() {
        return mMaxLines;
    }

    /**
     * Set the ItemView text max length(must greater or equal to 3).
     *
     * @param maxLength
     */
    public void setTagMaxLength(int maxLength) {
        mTagMaxLength = maxLength < TAG_MIN_LENGTH ? TAG_MIN_LENGTH : maxLength;
    }

    /**
     * Get ItemView max length.
     *
     * @return
     */
    public int getTagMaxLength() {
        return mTagMaxLength;
    }

    /**
     * Set ItemView theme.
     *
     * @param theme
     */
    public void setTheme(int theme) {
        mTheme = theme;
    }

    /**
     * Get ItemView theme.
     *
     * @return
     */
    public int getTheme() {
        return mTheme;
    }

    /**
     * Get ItemView is clickable.
     *
     * @return
     */
    public boolean getIsTagViewClickable() {
        return isTagViewClickable;
    }

    /**
     * Set ItemView is clickable
     *
     * @param clickable
     */
    public void setIsTagViewClickable(boolean clickable) {
        this.isTagViewClickable = clickable;
    }

    /**
     * Get ItemView is selectable.
     *
     * @return
     */
    public boolean getIsTagViewSelectable() {
        return isTagViewSelectable;
    }

    /**
     * Set ItemView is selectable
     *
     * @param selectable
     */
    public void setIsTagViewSelectable(boolean selectable) {
        this.isTagViewSelectable = selectable;
    }

    /**
     * Get ItemView border width.
     *
     * @return
     */
    public float getTagBorderWidth() {
        return mTagBorderWidth;
    }

    /**
     * Set ItemView border width.
     *
     * @param width
     */
    public void setTagBorderWidth(float width) {
        this.mTagBorderWidth = width;
    }

    /**
     * Get ItemView border radius.
     *
     * @return
     */
    public float getTagBorderRadius() {
        return mTagBorderRadius;
    }

    /**
     * Set ItemView border radius.
     *
     * @param radius
     */
    public void setTagBorderRadius(float radius) {
        this.mTagBorderRadius = radius;
    }

    /**
     * Get ItemView text size.
     *
     * @return
     */
    public float getTagTextSize() {
        return mTagTextSize;
    }

    /**
     * Set ItemView text size.
     *
     * @param size
     */
    public void setTagTextSize(float size) {
        this.mTagTextSize = size;
    }

    /**
     * Get ItemView horizontal padding.
     *
     * @return
     */
    public int getTagHorizontalPadding() {
        return mTagHorizontalPadding;
    }

    /**
     * Set ItemView horizontal padding.
     *
     * @param padding
     */
    public void setTagHorizontalPadding(int padding) {
        int ceilWidth = ceilTagBorderWidth();
        this.mTagHorizontalPadding = padding < ceilWidth ? ceilWidth : padding;
    }

    /**
     * Get ItemView vertical padding.
     *
     * @return
     */
    public int getTagVerticalPadding() {
        return mTagVerticalPadding;
    }

    /**
     * Set ItemView vertical padding.
     *
     * @param padding
     */
    public void setTagVerticalPadding(int padding) {
        int ceilWidth = ceilTagBorderWidth();
        this.mTagVerticalPadding = padding < ceilWidth ? ceilWidth : padding;
    }

    /**
     * Get ItemView border color.
     *
     * @return
     */
    public int getTagBorderColor() {
        return mTagBorderColor;
    }

    /**
     * Set ItemView border color.
     *
     * @param color
     */
    public void setTagBorderColor(int color) {
        this.mTagBorderColor = color;
    }

    /**
     * Get ItemView background color.
     *
     * @return
     */
    public int getTagBackgroundColor() {
        return mTagBackgroundColor;
    }

    /**
     * Set ItemView background color.
     *
     * @param color
     */
    public void setTagBackgroundColor(int color) {
        this.mTagBackgroundColor = color;
    }

    /**
     * Get ItemView text color.
     *
     * @return
     */
    public int getTagTextColor() {
        return mTagTextColor;
    }

    /**
     * Set tag text direction, support:View.TEXT_DIRECTION_RTL and View.TEXT_DIRECTION_LTR,
     * default View.TEXT_DIRECTION_LTR
     *
     * @param textDirection
     */
    public void setTagTextDirection(int textDirection) {
        this.mTagTextDirection = textDirection;
    }

    /**
     * Get ItemView typeface.
     *
     * @return
     */
    public Typeface getTagTypeface() {
        return mTagTypeface;
    }

    /**
     * Set ItemView typeface.
     *
     * @param typeface
     */
    public void setTagTypeface(Typeface typeface) {
        this.mTagTypeface = typeface;
    }

    /**
     * Get tag text direction
     *
     * @return
     */
    public int getTagTextDirection() {
        return mTagTextDirection;
    }

    /**
     * Set ItemView text color.
     *
     * @param color
     */
    public void setTagTextColor(int color) {
        this.mTagTextColor = color;
    }

    /**
     * Get the ripple effect color's alpha.
     *
     * @return
     */
    public int getRippleAlpha() {
        return mRippleAlpha;
    }

    /**
     * Set ItemView ripple effect alpha, the value may between 0 to 255, default is 128.
     *
     * @param mRippleAlpha
     */
    public void setRippleAlpha(int mRippleAlpha) {
        this.mRippleAlpha = mRippleAlpha;
    }

    /**
     * Get the ripple effect color.
     *
     * @return
     */
    public int getRippleColor() {
        return mRippleColor;
    }

    /**
     * Set ItemView ripple effect color.
     *
     * @param mRippleColor
     */
    public void setRippleColor(int mRippleColor) {
        this.mRippleColor = mRippleColor;
    }

    /**
     * Get the ripple effect duration.
     *
     * @return
     */
    public int getRippleDuration() {
        return mRippleDuration;
    }

    /**
     * Set ItemView ripple effect duration, default is 1000ms.
     *
     * @param mRippleDuration
     */
    public void setRippleDuration(int mRippleDuration) {
        this.mRippleDuration = mRippleDuration;
    }

    /**
     * Set ItemView cross color.
     *
     * @return
     */
    public int getCrossColor() {
        return mCrossColor;
    }

    /**
     * Set ItemView cross color, default Color.BLACK.
     *
     * @param mCrossColor
     */
    public void setCrossColor(int mCrossColor) {
        this.mCrossColor = mCrossColor;
    }

    /**
     * Get agView cross area's padding.
     *
     * @return
     */
    public float getCrossAreaPadding() {
        return mCrossAreaPadding;
    }

    /**
     * Set ItemView cross area padding, default 10dp.
     *
     * @param mCrossAreaPadding
     */
    public void setCrossAreaPadding(float mCrossAreaPadding) {
        this.mCrossAreaPadding = mCrossAreaPadding;
    }

    /**
     * Get is the ItemView's cross enable, default false.
     *
     * @return
     */
    public boolean isEnableCross() {
        return mEnableCross;
    }

    /**
     * Enable or disable the ItemView's cross.
     *
     * @param mEnableCross
     */
    public void setEnableCross(boolean mEnableCross) {
        this.mEnableCross = mEnableCross;
    }

    /**
     * Get ItemView cross area width.
     *
     * @return
     */
    public float getCrossAreaWidth() {
        return mCrossAreaWidth;
    }

    /**
     * Set ItemView area width.
     *
     * @param mCrossAreaWidth
     */
    public void setCrossAreaWidth(float mCrossAreaWidth) {
        this.mCrossAreaWidth = mCrossAreaWidth;
    }

    /**
     * Get ItemView cross line width.
     *
     * @return
     */
    public float getCrossLineWidth() {
        return mCrossLineWidth;
    }

    /**
     * Set ItemView cross line width, default 1dp.
     *
     * @param mCrossLineWidth
     */
    public void setCrossLineWidth(float mCrossLineWidth) {
        this.mCrossLineWidth = mCrossLineWidth;
    }

    /**
     * Get the 'letters show with RTL(like: Android to diordnA)' style if it's enabled
     *
     * @return
     */
    public boolean isTagSupportLettersRTL() {
        return mTagSupportLettersRTL;
    }

    /**
     * Set whether the 'support letters show with RTL(like: Android to diordnA)' style is enabled.
     *
     * @param mTagSupportLettersRTL
     */
    public void setTagSupportLettersRTL(boolean mTagSupportLettersRTL) {
        this.mTagSupportLettersRTL = mTagSupportLettersRTL;
    }

    /**
     * Get ItemView in specified position.
     *
     * @param position the position of the ItemView
     * @return
     */
    public ItemView getTagView(int position) {
        if (position < 0 || position >= mChildViews.size()) {
            throw new RuntimeException("Illegal position!");
        }
        return (ItemView) mChildViews.get(position);
    }

    /**
     * Get ItemView background resource
     *
     * @return
     */
    public int getTagBackgroundResource() {
        return mTagBackgroundResource;
    }

    /**
     * Set ItemView background resource
     *
     * @param tagBackgroundResource
     */
    public void setTagBackgroundResource(@DrawableRes int tagBackgroundResource) {
        this.mTagBackgroundResource = tagBackgroundResource;
    }

    public interface OnTagClickListener {
        void onTagClick(int position, String text);

        void onTagLongClick(int position, String text);

        void onSelectedTagDrag(int position, String text);

        void onTagCrossClick(int position);
    }

    public class ItemView extends View {

        /**
         * Border width
         */
        private float mBorderWidth;

        /**
         * Border radius
         */
        private float mBorderRadius;

        /**
         * Text size
         */
        private float mTextSize;

        /**
         * Horizontal padding for this view, include left & right padding(left & right padding are equal
         */
        private int mHorizontalPadding;

        /**
         * Vertical padding for this view, include top & bottom padding(top & bottom padding are equal)
         */
        private int mVerticalPadding;

        /**
         * ItemView border color
         */
        private int mBorderColor;

        /**
         * ItemView background color
         */
        private int mBackgroundColor;

        /**
         * ItemView background color
         */
        private int mSelectedBackgroundColor;

        /**
         * ItemView text color
         */
        private int mTextColor;

        /**
         * Whether this view clickable
         */
        private boolean isViewClickable;

        /**
         * Whether this view selectable
         */
        private boolean isViewSelectable;

        /**
         * Whether this view selected
         */
        private boolean isViewSelected;

        /**
         * The max length for this tag view
         */
        private int mTagMaxLength;

        /**
         * OnTagClickListener for click action
         */
        private OnTagClickListener mOnTagClickListener;

        /**
         * Move slop(default 5dp)
         */
        private int mMoveSlop = 5;

        /**
         * Scroll slop threshold 4dp
         */
        private int mSlopThreshold = 4;

        /**
         * How long trigger long click callback(default 500ms)
         */
        private int mLongPressTime = 500;

        /**
         * Text direction(support:TEXT_DIRECTION_RTL & TEXT_DIRECTION_LTR, default TEXT_DIRECTION_LTR)
         */
        private int mTextDirection = View.TEXT_DIRECTION_LTR;

        /**
         * The distance between baseline and descent
         */
        private float bdDistance;

        /**
         * Whether to support 'letters show with RTL(eg: Android to diordnA)' style(default false)
         */
        private boolean mTagSupportLettersRTL = false;

        private Paint mPaint, mRipplePaint;

        private RectF mRectF;

        private String mAbstractText, mOriginText;

        private boolean isUp, isMoved, isExecLongClick;

        private int mLastX, mLastY;

        private float fontH, fontW;

        private float mTouchX, mTouchY;

        /**
         * The ripple effect duration(default 1000ms)
         */
        private int mRippleDuration = 1000;

        private float mRippleRadius;

        private int mRippleColor;

        private int mRippleAlpha;

        private Path mPath;

        private Typeface mTypeface;

        private ValueAnimator mRippleValueAnimator;

        private Bitmap mBitmapImage;

        private boolean mEnableCross;

        private float mCrossAreaWidth;

        private float mCrossAreaPadding;

        private int mCrossColor;

        private float mCrossLineWidth;

        private boolean unSupportedClipPath = false;

        private Runnable mLongClickHandle = new Runnable() {
            @Override
            public void run() {
                if (!isMoved && !isUp) {
                    int state = ((TagView) getParent()).getTagViewState();
                    if (state == ViewDragHelper.STATE_IDLE) {
                        isExecLongClick = true;
                        mOnTagClickListener.onTagLongClick((int) getTag(), getText());
                    }
                }
            }
        };

        public ItemView(Context context, String text) {
            super(context);
            init(context, text);
        }

        public ItemView(Context context, String text, int defaultImageID) {
            super(context);
            init(context, text);
            mBitmapImage = BitmapFactory.decodeResource(getResources(), defaultImageID);
        }

        private void init(Context context, String text) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mRipplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mRipplePaint.setStyle(Paint.Style.FILL);
            mRectF = new RectF();
            mPath = new Path();
            mOriginText = text == null ? "" : text;
            mMoveSlop = (int) dp2px(context, mMoveSlop);
            mSlopThreshold = (int) dp2px(context, mSlopThreshold);
        }

        private void onDealText() {
            if (!TextUtils.isEmpty(mOriginText)) {
                mAbstractText = mOriginText.length() <= mTagMaxLength ? mOriginText
                        : mOriginText.substring(0, mTagMaxLength - 3) + "...";
            } else {
                mAbstractText = "";
            }
            mPaint.setTypeface(mTypeface);
            mPaint.setTextSize(mTextSize);
            final Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            fontH = fontMetrics.descent - fontMetrics.ascent;
            if (mTextDirection == View.TEXT_DIRECTION_RTL) {
                fontW = 0;
                for (char c : mAbstractText.toCharArray()) {
                    String sc = String.valueOf(c);
                    fontW += mPaint.measureText(sc);
                }
            } else {
                fontW = mPaint.measureText(mAbstractText);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int height = mVerticalPadding * 2 + (int) fontH;
            int width = mHorizontalPadding * 2 + (int) fontW + (isEnableCross() ? height : 0) + (isEnableImage() ? height : 0);
            mCrossAreaWidth = Math.min(Math.max(mCrossAreaWidth, height), width);
            setMeasuredDimension(width, height);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mRectF.set(mBorderWidth, mBorderWidth, w - mBorderWidth, h - mBorderWidth);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // draw background
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(getIsViewSelected() ? mSelectedBackgroundColor : mBackgroundColor);
            canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);

            // draw border
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mBorderWidth);
            mPaint.setColor(mBorderColor);
            canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mPaint);

            // draw ripple for ItemView
            drawRipple(canvas);

            // draw text
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mTextColor);

            if (mTextDirection == View.TEXT_DIRECTION_RTL) {
                if (mTagSupportLettersRTL) {
                    float tmpX = (isEnableCross() ? getWidth() + getHeight() : getWidth()) / 2
                            + fontW / 2;
                    for (char c : mAbstractText.toCharArray()) {
                        String sc = String.valueOf(c);
                        tmpX -= mPaint.measureText(sc);
                        canvas.drawText(sc, tmpX, getHeight() / 2 + fontH / 2 - bdDistance, mPaint);
                    }
                } else {
                    canvas.drawText(mAbstractText,
                            (isEnableCross() ? getWidth() + fontW : getWidth()) / 2 - fontW / 2,
                            getHeight() / 2 + fontH / 2 - bdDistance, mPaint);
                }
            } else {
                canvas.drawText(mAbstractText,
                        (isEnableCross() ? getWidth() - getHeight() : getWidth()) / 2 - fontW / 2 + (isEnableImage() ? getHeight() / 2 : 0),
                        getHeight() / 2 + fontH / 2 - bdDistance, mPaint);
            }

            // draw cross
            drawCross(canvas);

            // draw image
            drawImage(canvas);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            if (isViewClickable) {
                int y = (int) event.getY();
                int x = (int) event.getX();
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (getParent() != null) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        mLastY = y;
                        mLastX = x;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (!isViewSelected && (Math.abs(mLastY - y) > mSlopThreshold
                                || Math.abs(mLastX - x) > mSlopThreshold)) {
                            if (getParent() != null) {
                                getParent().requestDisallowInterceptTouchEvent(false);
                            }
                            isMoved = true;
                            return false;
                        }
                        break;
                }
            }
            return super.dispatchTouchEvent(event);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                mRippleRadius = 0.0f;
                mTouchX = event.getX();
                mTouchY = event.getY();
                splashRipple();
            }
            if (isEnableCross() && isClickCrossArea(event) && mOnTagClickListener != null) {
                if (action == MotionEvent.ACTION_UP) {
                    mOnTagClickListener.onTagCrossClick((int) getTag());
                }
                return true;
            } else if (isViewClickable && mOnTagClickListener != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mLastY = y;
                        mLastX = x;
                        isMoved = false;
                        isUp = false;
                        isExecLongClick = false;
                        postDelayed(mLongClickHandle, mLongPressTime);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (isMoved) {
                            break;
                        }
                        if (Math.abs(mLastX - x) > mMoveSlop || Math.abs(mLastY - y) > mMoveSlop) {
                            isMoved = true;
                            if (isViewSelected) {
                                mOnTagClickListener.onSelectedTagDrag((int) getTag(), getText());
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        isUp = true;
                        if (!isExecLongClick && !isMoved) {
                            mOnTagClickListener.onTagClick((int) getTag(), getText());
                        }
                        break;
                }
                return true;
            }
            return super.onTouchEvent(event);
        }

        private boolean isClickCrossArea(MotionEvent event) {
            if (mTextDirection == View.TEXT_DIRECTION_RTL) {
                return event.getX() <= mCrossAreaWidth;
            }
            return event.getX() >= getWidth() - mCrossAreaWidth;
        }

        private void drawImage(Canvas canvas) {
            if (isEnableImage()) {
                Bitmap scaledImageBitmap = Bitmap.createScaledBitmap(mBitmapImage, Math.round(getHeight() - mBorderWidth), Math.round(getHeight() - mBorderWidth), false);

                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setShader(new BitmapShader(scaledImageBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                RectF rect = new RectF(mBorderWidth, mBorderWidth, getHeight() - mBorderWidth, getHeight() - mBorderWidth);
                canvas.drawRoundRect(rect, rect.height() / 2, rect.height() / 2, paint);
            }
        }

        private void drawCross(Canvas canvas) {
            if (isEnableCross()) {
                mCrossAreaPadding = mCrossAreaPadding > getHeight() / 2 ? getHeight() / 2 :
                        mCrossAreaPadding;
                int ltX, ltY, rbX, rbY, lbX, lbY, rtX, rtY;
                ltX = mTextDirection == View.TEXT_DIRECTION_RTL ? (int) (mCrossAreaPadding) :
                        (int) (getWidth() - getHeight() + mCrossAreaPadding);
                ltY = mTextDirection == View.TEXT_DIRECTION_RTL ? (int) (mCrossAreaPadding) :
                        (int) (mCrossAreaPadding);
                lbX = mTextDirection == View.TEXT_DIRECTION_RTL ? (int) (mCrossAreaPadding) :
                        (int) (getWidth() - getHeight() + mCrossAreaPadding);
                lbY = mTextDirection == View.TEXT_DIRECTION_RTL ?
                        (int) (getHeight() - mCrossAreaPadding) : (int) (getHeight() - mCrossAreaPadding);
                rtX = mTextDirection == View.TEXT_DIRECTION_RTL ?
                        (int) (getHeight() - mCrossAreaPadding) : (int) (getWidth() - mCrossAreaPadding);
                rtY = mTextDirection == View.TEXT_DIRECTION_RTL ? (int) (mCrossAreaPadding) :
                        (int) (mCrossAreaPadding);
                rbX = mTextDirection == View.TEXT_DIRECTION_RTL ?
                        (int) (getHeight() - mCrossAreaPadding) : (int) (getWidth() - mCrossAreaPadding);
                rbY = mTextDirection == View.TEXT_DIRECTION_RTL ?
                        (int) (getHeight() - mCrossAreaPadding) : (int) (getHeight() - mCrossAreaPadding);

                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(mCrossColor);
                mPaint.setStrokeWidth(mCrossLineWidth);
                canvas.drawLine(ltX, ltY, rbX, rbY, mPaint);
                canvas.drawLine(lbX, lbY, rtX, rtY, mPaint);
            }
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void drawRipple(Canvas canvas) {
            if (isViewClickable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB &&
                    canvas != null && !unSupportedClipPath) {

                // Disable hardware acceleration for 'Canvas.clipPath()' when running on API from 11 to 17
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    setLayerType(LAYER_TYPE_SOFTWARE, null);
                }
                try {
                    canvas.save();
                    mPath.reset();

                    canvas.clipPath(mPath);
                    mPath.addRoundRect(mRectF, mBorderRadius, mBorderRadius, Path.Direction.CCW);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        canvas.clipPath(mPath);
                    } else {
                        canvas.clipPath(mPath, Region.Op.REPLACE);
                    }

                    canvas.drawCircle(mTouchX, mTouchY, mRippleRadius, mRipplePaint);
                    canvas.restore();
                } catch (UnsupportedOperationException e) {
                    unSupportedClipPath = true;
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void splashRipple() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && mTouchX > 0 && mTouchY > 0) {
                mRipplePaint.setColor(mRippleColor);
                mRipplePaint.setAlpha(mRippleAlpha);
                final float maxDis = Math.max(Math.max(Math.max(mTouchX, mTouchY),
                        Math.abs(getMeasuredWidth() - mTouchX)), Math.abs(getMeasuredHeight() - mTouchY));

                mRippleValueAnimator = ValueAnimator.ofFloat(0.0f, maxDis).setDuration(mRippleDuration);
                mRippleValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animValue = (float) animation.getAnimatedValue();
                        mRippleRadius = animValue >= maxDis ? 0 : animValue;
                        postInvalidate();
                    }
                });
                mRippleValueAnimator.start();
            }
        }

        public String getText() {
            return mOriginText;
        }

        public boolean getIsViewClickable() {
            return isViewClickable;
        }

        public boolean getIsViewSelected() {
            return isViewSelected;
        }

        public void setTagMaxLength(int maxLength) {
            this.mTagMaxLength = maxLength;
            onDealText();
        }

        public void setOnTagClickListener(OnTagClickListener listener) {
            this.mOnTagClickListener = listener;
        }

        public int getTagBackgroundColor() {
            return mBackgroundColor;
        }

        public int getTagSelectedBackgroundColor() {
            return mSelectedBackgroundColor;
        }

        public void setTagBackgroundColor(int color) {
            this.mBackgroundColor = color;
        }

        public void setTagSelectedBackgroundColor(int color) {
            this.mSelectedBackgroundColor = color;
        }

        public void setTagBorderColor(int color) {
            this.mBorderColor = color;
        }

        public void setTagTextColor(int color) {
            this.mTextColor = color;
        }

        public void setBorderWidth(float width) {
            this.mBorderWidth = width;
        }

        public void setBorderRadius(float radius) {
            this.mBorderRadius = radius;
        }

        public void setTextSize(float size) {
            this.mTextSize = size;
            onDealText();
        }

        public void setHorizontalPadding(int padding) {
            this.mHorizontalPadding = padding;
        }

        public void setVerticalPadding(int padding) {
            this.mVerticalPadding = padding;
        }

        public void setIsViewClickable(boolean clickable) {
            this.isViewClickable = clickable;
        }

        public void setImage(Bitmap newImage) {
            this.mBitmapImage = newImage;
            this.invalidate();
        }

        public void setIsViewSelectable(boolean viewSelectable) {
            isViewSelectable = viewSelectable;
        }

        //TODO change background color
        public void selectView() {
            if (isViewSelectable && !getIsViewSelected()) {
                this.isViewSelected = true;
                postInvalidate();
            }
        }

        public void deselectView() {
            if (isViewSelectable && getIsViewSelected()) {
                this.isViewSelected = false;
                postInvalidate();
            }
        }

        public int getTextDirection() {
            return mTextDirection;
        }

        public void setTextDirection(int textDirection) {
            this.mTextDirection = textDirection;
        }

        public void setTypeface(Typeface typeface) {
            this.mTypeface = typeface;
            onDealText();
        }

        public void setRippleAlpha(int mRippleAlpha) {
            this.mRippleAlpha = mRippleAlpha;
        }

        public void setRippleColor(int mRippleColor) {
            this.mRippleColor = mRippleColor;
        }

        public void setRippleDuration(int mRippleDuration) {
            this.mRippleDuration = mRippleDuration;
        }

        public void setBdDistance(float bdDistance) {
            this.bdDistance = bdDistance;
        }

        public boolean isEnableImage() {
            return mBitmapImage != null && mTextDirection != View.TEXT_DIRECTION_RTL;
        }

        public boolean isEnableCross() {
            return mEnableCross;
        }

        public void setEnableCross(boolean mEnableCross) {
            this.mEnableCross = mEnableCross;
        }

        public float getCrossAreaWidth() {
            return mCrossAreaWidth;
        }

        public void setCrossAreaWidth(float mCrossAreaWidth) {
            this.mCrossAreaWidth = mCrossAreaWidth;
        }

        public float getCrossLineWidth() {
            return mCrossLineWidth;
        }

        public void setCrossLineWidth(float mCrossLineWidth) {
            this.mCrossLineWidth = mCrossLineWidth;
        }

        public float getCrossAreaPadding() {
            return mCrossAreaPadding;
        }

        public void setCrossAreaPadding(float mCrossAreaPadding) {
            this.mCrossAreaPadding = mCrossAreaPadding;
        }

        public int getCrossColor() {
            return mCrossColor;
        }

        public void setCrossColor(int mCrossColor) {
            this.mCrossColor = mCrossColor;
        }

        public boolean isTagSupportLettersRTL() {
            return mTagSupportLettersRTL;
        }

        public void setTagSupportLettersRTL(boolean mTagSupportLettersRTL) {
            this.mTagSupportLettersRTL = mTagSupportLettersRTL;
        }
    }

    public static class ColorFactory {

        /**
         * ============= --border color
         * background color---||-  Text --||--text color
         * =============
         */

        public static final String BG_COLOR_ALPHA = "33";
        public static final String BD_COLOR_ALPHA = "88";

        public static final String RED = "F44336";
        public static final String LIGHTBLUE = "03A9F4";
        public static final String AMBER = "FFC107";
        public static final String ORANGE = "FF9800";
        public static final String YELLOW = "FFEB3B";
        public static final String LIME = "CDDC39";
        public static final String BLUE = "2196F3";
        public static final String INDIGO = "3F51B5";
        public static final String LIGHTGREEN = "8BC34A";
        public static final String GREY = "9E9E9E";
        public static final String DEEPPURPLE = "673AB7";
        public static final String TEAL = "009688";
        public static final String CYAN = "00BCD4";

        public enum PURE_COLOR {CYAN, TEAL}

        public static final int NONE = -1;
        public static final int RANDOM = 0;
        public static final int PURE_CYAN = 1;
        public static final int PURE_TEAL = 2;

        public static final int SHARP666666 = Color.parseColor("#FF666666");
        public static final int SHARP727272 = Color.parseColor("#FF727272");

        private static final String[] COLORS = new String[]{RED, LIGHTBLUE, AMBER, ORANGE, YELLOW,
                LIME, BLUE, INDIGO, LIGHTGREEN, GREY, DEEPPURPLE, TEAL, CYAN};

        public static int[] onRandomBuild() {
            int random = (int) (Math.random() * COLORS.length);
            int bgColor = Color.parseColor("#" + BG_COLOR_ALPHA + COLORS[random]);
            int bdColor = Color.parseColor("#" + BD_COLOR_ALPHA + COLORS[random]);
            int tColor = SHARP666666;
            int tColor2 = SHARP727272;
            return new int[]{bgColor, bdColor, tColor, tColor2};
        }

        public static int[] onPureBuild(PURE_COLOR type) {
            String color = type == PURE_COLOR.CYAN ? CYAN : TEAL;
            int bgColor = Color.parseColor("#" + BG_COLOR_ALPHA + color);
            int bdColor = Color.parseColor("#" + BD_COLOR_ALPHA + color);
            int tColor = SHARP727272;
            int tColor2 = SHARP666666;
            return new int[]{bgColor, bdColor, tColor, tColor2};
        }

    }
}
