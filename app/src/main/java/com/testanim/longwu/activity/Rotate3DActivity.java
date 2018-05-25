package com.testanim.longwu.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.testanim.longwu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Rotate3DActivity extends AppCompatActivity {


    @BindView(R.id.main_fl_card_back)
    FrameLayout mFlCardBack;
    @BindView(R.id.main_fl_card_front)
    FrameLayout mFlCardFront;
    @BindView(R.id.main_fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.im_btn)
    CardView imBtn;
    @BindView(R.id.im_1)
    ImageView im1;
    @BindView(R.id.im_2)
    ImageView im2;
    @BindView(R.id.im_3)
    ImageView im3;


    private AnimatorSet mRightOutSet; // 右出动画
    private AnimatorSet mLeftInSet; // 左入动画

    private boolean mIsShowBack;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate3_d);
        unbinder = ButterKnife.bind(this);
        list.add(im1);
        list.add(im2);
        list.add(im3);
        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离
    }

    // 设置动画
    private void setAnimators() {
//        mRightOutSet = new AnimatorSet();
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);


//        mLeftInSet = new AnimatorSet();
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);

        // 设置点击事件
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mFlContainer.setClickable(false);
            }
        });
        mLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFlContainer.setClickable(true);
            }
        });
    }

    /*
        <!--消失-->
    <objectAnimator
        android:duration="0"
        android:propertyName="alpha"
        android:valueFrom="1.0"
        android:valueTo="0.0"/>

    <!--旋转-->
    <objectAnimator
        android:duration="@integer/anim_length"
        android:propertyName="rotationY"
        android:valueFrom="-180"
        android:valueTo="0"/>

    <!--出现-->
    <objectAnimator
        android:duration="0"
        android:propertyName="alpha"
        android:startOffset="@integer/anim_half_length"
        android:valueFrom="0.0"
        android:valueTo="1.0"/>*/
    private void animIn(FrameLayout frameLayout) {
//         <!--消失-->
        ObjectAnimator animatorAlpha1 = ObjectAnimator.ofFloat(frameLayout, "alpha", 1.0f, 0.0f);
        animatorAlpha1.setDuration(0);
//旋转
        ObjectAnimator animatorRotationY = ObjectAnimator.ofFloat(frameLayout, "rotationY", -180f, 0f);
        animatorRotationY.setDuration(getResources().getInteger(R.integer.anim_length));
//出现
        ObjectAnimator animatorAlpha2 = ObjectAnimator.ofFloat(frameLayout, "alpha", 0f, 1f);
        animatorAlpha2.setStartDelay(getResources().getInteger(R.integer.anim_half_length));
        animatorAlpha2.setDuration(0);

        mLeftInSet.play(animatorRotationY).with(animatorAlpha1).with(animatorAlpha2);
        mLeftInSet.start();
    }

    /* <!--旋转-->
      <objectAnimator
          android:duration="@integer/anim_length"
          android:propertyName="rotationY"
          android:valueFrom="0"
          android:valueTo="180"/>
      <!--消失-->
      <objectAnimator
          android:duration="0"
          android:propertyName="alpha"
          android:startOffset="@integer/anim_half_length"
          android:valueFrom="1.0"
          android:valueTo="0.0"/>*/
    private void animOut(FrameLayout view) {

        ObjectAnimator animatorRotationY = ObjectAnimator.ofFloat(view, "rotationY", 0f, 180f);
        animatorRotationY.setDuration(getResources().getInteger(R.integer.anim_length));

        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animatorAlpha.setStartDelay(getResources().getInteger(R.integer.anim_half_length));
        animatorAlpha.setDuration(0);
        mRightOutSet.play(animatorRotationY).with(animatorAlpha);
        mRightOutSet.start();
    }

    // 改变视角距离, 贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardFront.setCameraDistance(scale);
        mFlCardBack.setCameraDistance(scale);
    }

    // 翻转卡片
    public void flipCard(View view) {
        // 正面朝上
        if (!mIsShowBack) {   //正 转 反
            mRightOutSet.setTarget(mFlCardFront);
            mLeftInSet.setTarget(mFlCardBack);
            mRightOutSet.start();
            mLeftInSet.start();
//            animOut(mFlCardFront);
//            animIn(mFlCardBack);
            mIsShowBack = true;
        } else { // 背面朝上       反转正

            mRightOutSet.setTarget(mFlCardBack);
            mLeftInSet.setTarget(mFlCardFront);
            mRightOutSet.start();
            mLeftInSet.start();

//            animOut(mFlCardBack);
//            animIn(mFlCardFront);
            mIsShowBack = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    List<ImageView> list = new ArrayList<ImageView>();
    private boolean isOpen;

    @OnClick(R.id.im_btn)
    public void onViewClicked() {

        for (int i = 0; i < list.size(); i++) {
            ImageView menu = list.get(i);
            double angle = Math.toRadians(i * (90 * 1.0 / (list.size() - 1))); // 角度
            double radius = 450; // 半径
            float distanceX = (float) (Math.cos(angle) * radius); // X坐标偏移量
            float distanceY = (float) (Math.sin(angle) * radius); // Y坐标偏移量
            ObjectAnimator animatorX;
            ObjectAnimator animatorY;
            if (isOpen) { // 如果菜单是打开的则关闭菜单
                animatorX = ObjectAnimator.ofFloat(menu, "translationX", -distanceX, 0f);
                animatorY = ObjectAnimator.ofFloat(menu, "translationY", -distanceY, 0f);
            } else { // 如果菜单是关闭的则打开菜单
                animatorX = ObjectAnimator.ofFloat(menu, "translationX", 0f, -distanceX);
                animatorY = ObjectAnimator.ofFloat(menu, "translationY", 0f, -distanceY);
            }
            AnimatorSet set = new AnimatorSet(); // X、Y轴同时移动
            set.playTogether(animatorX, animatorY);
            set.setDuration(500);
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.start();
        }
        isOpen = !isOpen;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public final static float getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public final static float getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.heightPixels;
    }
}
