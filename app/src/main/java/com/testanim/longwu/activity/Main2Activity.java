package com.testanim.longwu.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.testanim.longwu.R;
import com.testanim.longwu.base.BaseFragment;
import com.testanim.longwu.bean.TabEntity;
import com.testanim.longwu.fragment.FirstTabFragment;
import com.testanim.longwu.fragment.FourthTabFragment;
import com.testanim.longwu.fragment.SecondTabFragment;
import com.testanim.longwu.fragment.ThirdTabFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity implements FirstTabFragment.OnFragmentInteractionListener {

    @BindView(R.id.vp_2)
    ViewPager mViewPager;
    @BindView(R.id.tl_2)
    CommonTabLayout commonTabLayout;
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"首页", "认证", "发现", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private FirstTabFragment firstTabFragment;
    private SecondTabFragment secondTabFragment;
    private ThirdTabFragment thirdTabFragment;
    private FourthTabFragment fourthTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        firstTabFragment = FirstTabFragment.newInstance(mTitles[0], "");
        secondTabFragment = SecondTabFragment.newInstance(mTitles[1], "");
        thirdTabFragment = ThirdTabFragment.newInstance(mTitles[2], "");
        fourthTabFragment = FourthTabFragment.newInstance(mTitles[3], "");


        mFragments.add(firstTabFragment);
        mFragments.add(secondTabFragment);
        mFragments.add(thirdTabFragment);
        mFragments.add(fourthTabFragment);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
