package com.testanim.longwu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.testanim.longwu.R;
import com.testanim.longwu.view.MetaBallView2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MetaBallActivity extends AppCompatActivity {

    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.metaball_bezier)
    MetaBallView2 metaballBezier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_ball);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_reset)
    public void onViewClicked() {
        metaballBezier.reset();
    }
}
