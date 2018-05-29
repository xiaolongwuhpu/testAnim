package com.testanim.longwu.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.testanim.longwu.R;
import com.testanim.longwu.view.BallView;
import com.testanim.longwu.view.MetaBallView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.myview)
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

    private boolean isBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv, R.id.myview, R.id.testrotate3d, R.id.button3d, R.id.buttonrotate, R.id.bezier, R.id.metaball_bezier})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv:
                objectAnim();
                break;
            case R.id.myview:
                myview.reStartPoint();
                break;
            case R.id.testrotate3d:
                Intent intent = new Intent(this, Rotate3DActivity.class);
                startActivity(intent);
                break;
//            case R.id.button3d:
//                Intent intent2 = new Intent(this, Rotate3DforCustomActivity.class);
//                startActivity(intent2);
//                break;
            case R.id.bezier:
                Intent bezier = new Intent(this, BezierActivity.class);
                startActivity(bezier);
                break;
            case R.id.metaball_bezier:
                Intent metaballBezier = new Intent(this, MetaBallActivity.class);
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
            case R.id.btn1:
                ValueAnimator animator = ValueAnimator.ofFloat((float) btn1.getLayoutParams().width, 500f);
                animator.setDuration(3000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentvalue = (float) animation.getAnimatedValue();
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
