package com.testanim.longwu.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.testanim.longwu.R;
import com.testanim.longwu.bean.loanCycleResBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class loanCycleButtonAdapter extends BaseQuickAdapter<loanCycleResBean, BaseViewHolder> {

    public loanCycleButtonAdapter(@LayoutRes int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    Map<Integer, Boolean> map = new HashMap<>();

    @Override
    protected void convert(BaseViewHolder helper, loanCycleResBean item) {
        final int position = helper.getAdapterPosition();
        helper.addOnClickListener(R.id.fllayout);
        helper.setText(R.id.borrowing_button, item.getLoanDays());
        TextView tv = helper.getView(R.id.hot_text);
        if (item.isShowImage()) {
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }

        //adapter 中设置子元素的放大比例
        RelativeLayout view = helper.getView(R.id.fllayout);
        TextView textView = helper.getView(R.id.borrowing_button);
        helper.addOnClickListener(R.id.borrowing_button);

        if (map != null && map.containsKey(position)) {
            textView.setSelected(true);
        } else {
            textView.setSelected(false);
        }

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp =
                    (FlexboxLayoutManager.LayoutParams) view.getLayoutParams();
            flexboxLp.setFlexGrow(1.0f);
        }

    }

    public void selectedPosition(int position) {
        map.clear();
        map.put(position, true);
        notifyDataSetChanged();
    }
}
