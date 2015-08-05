/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.testdemo.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckBox;

import com.example.testdemo.R;

public class SlidingButton extends CheckBox implements AnimatorListener, AnimatorUpdateListener {

    public static final String TAG = "SlidingButton";

    private ViewParent mParent;

    private Drawable mMask;

    private Drawable mBackground;

    private Drawable mBackgroundOff;

    private Drawable mBackgroundOffDisable;

    private Drawable mBackgroundOn;

    private Drawable mBackgroundOnDisable;

    private Drawable mCurrentButton;

    private Drawable mButtonOff;

    private Drawable mButtonOffDisable;

    private Drawable mButtonOn;

    private Drawable mButtonOnDisable;

    private RectF mSaveLayerRectF;

    private float mFirstDownY;

    private float mFirstDownX;

    private float mButtonPosition;

    private float mRealButtonPosition;

    private float mButtonOnPosition;

    private float mButtonOffPosition;

    private float mButtonInitPosition;

    private int mMaskWidth;

    private int mMaskHeight;

    private int mButtonWidth;

    private int mButtonHeight;

    private int mClickTimeout;

    private int mTouchSlop;

    private boolean mTurningOn;

    private PerformClick mPerformClick;

    private ValueAnimator mAnimation;

    private boolean mIsDoingAnimation = false;

    public SlidingButton(Context context) {
        this(context, null);
    }

    public SlidingButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.slidingButtonStyle);
    }

    public SlidingButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs, defStyle);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyle) {
        Log.i(TAG, "SlidingButton initialize");
        Resources resources = context.getResources();

        TypedArray a = context
                .obtainStyledAttributes(attrs, R.styleable.SlidingButton, defStyle, 0);

        mMask = a.getDrawable(R.styleable.SlidingButton_backgroundOff);

        mBackgroundOff = a.getDrawable(R.styleable.SlidingButton_backgroundOff);
        mBackgroundOffDisable = a.getDrawable(R.styleable.SlidingButton_backgroundOffDisable);
        mBackgroundOn = a.getDrawable(R.styleable.SlidingButton_backgroundOn);
        mBackgroundOnDisable = a.getDrawable(R.styleable.SlidingButton_backgroundOnDisable);

        mButtonOff = a.getDrawable(R.styleable.SlidingButton_buttonOff);
        mButtonOffDisable = a.getDrawable(R.styleable.SlidingButton_buttonOffDisable);
        mButtonOn = a.getDrawable(R.styleable.SlidingButton_buttonOn);
        mButtonOnDisable = a.getDrawable(R.styleable.SlidingButton_buttonOnDisable);

        mMaskWidth = mMask.getIntrinsicWidth();
        mMaskHeight = mMask.getIntrinsicHeight();

        mButtonWidth = mButtonOn.getIntrinsicWidth();
        mButtonHeight = mButtonOn.getIntrinsicHeight();

        mButtonOffPosition = mButtonWidth / 2;
        mButtonOnPosition = mMaskWidth - mButtonWidth / 2;

        mButtonPosition = isChecked() ? mButtonOnPosition : mButtonOffPosition;
        mRealButtonPosition = getRealPos(mButtonPosition);

        a.recycle();

        setChecked(isChecked());

        // get viewConfiguration
        mClickTimeout = ViewConfiguration.getPressedStateDuration()
                + ViewConfiguration.getTapTimeout();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private void refreshPicturesStatus(boolean isChecked) {
        if (isEnabled()) {
            mBackground = isChecked ? mBackgroundOn : mBackgroundOff;
            mCurrentButton = isChecked ? mButtonOn : mButtonOff;
        } else {
            mBackground = isChecked ? mBackgroundOnDisable : mBackgroundOffDisable;
            mCurrentButton = isChecked ? mButtonOnDisable : mButtonOffDisable;
        }
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setTrackResource(int resId) {
    }

    /**
     * <p>
     * Changes the checked state of this button.
     * </p>
     * 
     * @param checked true to check the button, false to uncheck it
     */
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        refreshPicturesStatus(isChecked());
        mButtonPosition = isChecked() ? mButtonOnPosition : mButtonOffPosition;
        mRealButtonPosition = getRealPos(mButtonPosition);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || mIsDoingAnimation) {
            return false;
        }

        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        float deltaX = Math.abs(x - mFirstDownX);
        float deltaY = Math.abs(y - mFirstDownY);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                attemptClaimDrag();
                mFirstDownX = x;
                mFirstDownY = y;
                refreshPicturesStatus(isChecked());
                mButtonInitPosition = isChecked() ? mButtonOnPosition : mButtonOffPosition;
                break;
            case MotionEvent.ACTION_MOVE:
                float time = event.getEventTime() - event.getDownTime();
                mButtonPosition = mButtonInitPosition + event.getX() - mFirstDownX;

                if (mButtonPosition < mButtonOffPosition) {
                    mButtonPosition = mButtonOffPosition;
                }
                if (mButtonPosition > mButtonOnPosition) {
                    mButtonPosition = mButtonOnPosition;
                }
                mTurningOn = mButtonPosition > (mButtonOnPosition - mButtonOffPosition) / 2
                        + mButtonOffPosition;

                mRealButtonPosition = getRealPos(mButtonPosition);
                break;
            case MotionEvent.ACTION_UP:
                refreshPicturesStatus(isChecked());
                time = event.getEventTime() - event.getDownTime();
                if (deltaY < mTouchSlop && deltaX < mTouchSlop
                        && time < mClickTimeout) {
                    if (mPerformClick == null) {
                        mPerformClick = new PerformClick();
                    }
                    if (!post(mPerformClick)) {
                        performClick();
                    }
                } else {
                    startAnimation(mTurningOn);
                }
                break;
        }

        invalidate();
        return isEnabled();
    }

    private final class PerformClick implements Runnable {
        public void run() {
            performClick();
        }
    }

    @Override
    public boolean performClick() {
        startAnimation(!isChecked());
        return true;
    }

    /**
     * Tries to claim the user's drag motion, and requests disallowing any
     * ancestors from stealing events in the drag.
     */
    private void attemptClaimDrag() {
        mParent = getParent();
        if (mParent != null) {
            mParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private float getRealPos(float buttonPosition) {
        return buttonPosition - mButtonWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        refreshPicturesStatus(isChecked());

        mMask.setBounds(0, 0, mMaskWidth, mMaskHeight);
        mMask.draw(canvas);
        canvas.save();

        mBackground.setBounds(0, 0, mMaskWidth, mMaskHeight);
        mBackground.draw(canvas);
        canvas.save();

        mCurrentButton.setBounds((int) mRealButtonPosition, 0,
                ((int) mRealButtonPosition + mButtonWidth), mButtonHeight);
        mCurrentButton.draw(canvas);

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mMaskWidth, mMaskHeight);
    }

    private void startAnimation(boolean turnOn) {
        float finalPosition = turnOn ? mButtonOnPosition : mButtonOffPosition;
        mAnimation = ValueAnimator.ofFloat(mButtonPosition, finalPosition);
        mAnimation.setDuration(300);
        mAnimation.addListener(this);
        mAnimation.addUpdateListener(this);
        mAnimation.start();
    }

    private void moveView(float position) {
        mButtonPosition = position;
        mRealButtonPosition = getRealPos(mButtonPosition);
        invalidate();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        // TODO Auto-generated method stub
        float value = (Float) animation.getAnimatedValue();
        moveView(value);
    }

    @Override
    public void onAnimationStart(Animator animation) {
        // TODO Auto-generated method stub
        mIsDoingAnimation = true;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        // TODO Auto-generated method stub
        float value = (Float) ((ValueAnimator) animation).getAnimatedValue();
        boolean finalStatus = value == mButtonOnPosition;
        setChecked(finalStatus);
        mIsDoingAnimation = false;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(SlidingButton.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(SlidingButton.class.getName());
    }

}
