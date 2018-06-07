package com.testanim.longwu.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.testanim.longwu.R;
import com.testanim.longwu.activity.BezierActivity;
import com.testanim.longwu.activity.MetaBallActivity;
import com.testanim.longwu.activity.MoveBallActivity;
import com.testanim.longwu.activity.Rotate3DActivity;
import com.testanim.longwu.base.BaseFragment;
import com.testanim.longwu.view.BallView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FirstTabFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.numericalgradient)
    Button btn1;
    @BindView(R.id.ballview)
    BallView myview;
    @BindView(R.id.testrotate3d)
    Button btn2;
    @BindView(R.id.button3d)
    Button button3d;
    @BindView(R.id.buttonrotate)
    Button buttonrotate;
    @BindView(R.id.bezier)
    Button bezier;
    @BindView(R.id.metaball_bezier)
    Button metaballBezier;
    @BindView(R.id.move_ball)
    Button moveBall;
    private boolean isBack = false;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FirstTabFragment() {
    }

    public static FirstTabFragment newInstance(String param1, String param2) {
        FirstTabFragment fragment = new FirstTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_tab_first;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        tv.setText(mParam1 + " : " + mParam2);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv, R.id.ballview, R.id.testrotate3d, R.id.numericalgradient, R.id.button3d, R.id.buttonrotate, R.id.bezier, R.id.metaball_bezier,R.id.move_ball})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv:
                objectAnim();
                break;
            case R.id.ballview:
                myview.reStartPoint();
                break;
            case R.id.move_ball:
                Intent intent2 = new Intent(mActivity, MoveBallActivity.class);
                startActivity(intent2);
                break;
            case R.id.testrotate3d:
                Intent intent = new Intent(mActivity, Rotate3DActivity.class);
                startActivity(intent);
                break;
//            case R.id.button3d:
//                Intent intent2 = new Intent(this, Rotate3DforCustomActivity.class);
//                startActivity(intent2);
//                break;
            case R.id.bezier:
                Intent bezier = new Intent(mActivity, BezierActivity.class);
                startActivity(bezier);
                break;
            case R.id.metaball_bezier:
                Intent metaballBezier = new Intent(mActivity, MetaBallActivity.class);
                startActivity(metaballBezier);
                break;
            case R.id.buttonrotate:
                ObjectAnimator objectAnimator;
                if (!isBack) {  //正 转 反
                    objectAnimator = ObjectAnimator.ofFloat(buttonrotate, "rotationY", 0f, 180f);
                    isBack = true;
                    buttonrotate.setText("转正面了");
                } else {  //反 转 正
                    objectAnimator = ObjectAnimator.ofFloat(buttonrotate, "rotationY", 180f, 360f);
                    isBack = false;
                    buttonrotate.setText("反面哈哈哈哈");
                }
                objectAnimator.setDuration(1000);
                objectAnimator.start();
                break;
            case R.id.numericalgradient:
                ValueAnimator animator = ValueAnimator.ofFloat((float) btn1.getLayoutParams().width, 500f);
                animator.setDuration(3000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentvalue = (float) animation.getAnimatedValue();
                        if(btn1==null)return;
                        btn1.setText(currentvalue + "");
                        btn1.getLayoutParams().width = (int) currentvalue;
                        btn1.requestLayout();
                        if (currentvalue == 500) {
                            currentvalue = 300;
                            btn1.getLayoutParams().width = (int) currentvalue;
                            btn1.requestLayout();
                            return;
                        }
                    }
                });
                animator.start();
                break;

        }
    }

    private void objectAnim() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(tv, "scaleX", 1f, 3f, 2f, 1f);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(tv, "scaleY", 1f, 3f, 2f, 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(tv, "translationX", 0f, 360f, 0, -360F, 0);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(tv, "rotation", 0f, 360f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(tv, "alpha", 1f, 0f, 0.5f);
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(tv, "alpha", 1f, 0f);

        AnimatorSet set = new AnimatorSet();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("---", animation.getDuration() + "取值");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("---==", animation.getDuration() + "取值");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.play(animator1).with(animator5).with(animator2).with(animator3).with(animator4);
        set.setDuration(2000);
        set.start();
    }

}
