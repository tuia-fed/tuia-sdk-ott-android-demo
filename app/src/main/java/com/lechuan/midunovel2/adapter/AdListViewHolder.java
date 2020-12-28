package com.lechuan.midunovel2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lechuan.midunovel2.R;
import com.lechuan.midunovel2.bean.DemoBean;
import com.lechuan.midunovel2.utils.TADemoAnimationUtil;
import com.mediamain.android.view.FoxWallView;
import com.mediamain.android.view.interfaces.IFoxTempletInfoFeedAd;

/**
 * <p> File description: <p>
 * <p> Creator: Adroll   <p>
 * <p> Created date: 12/11/20 <p>
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class AdListViewHolder implements View.OnFocusChangeListener {

    private RelativeLayout rlContainer;
    private View view;

    public AdListViewHolder(Context context, ViewGroup viewGroup, DemoBean bean){
        if (context == null){
            return;
        }

        view = LayoutInflater.from(context).inflate(R.layout.item_list_view, viewGroup, false);
        rlContainer = view.findViewById(R.id.rl_item_listview_container);
        view.setTag(this);

        IFoxTempletInfoFeedAd ad = bean.getAd();
        if (ad == null){
            TextView tv = new TextView(context);
            tv.setText("This is null");
            tv.setTextColor(context.getResources().getColor(R.color.black));
            tv.setTextSize(24);
            tv.setFocusable(true);
            tv.setOnFocusChangeListener(this);
            rlContainer.addView(tv);
            return;
        }

        if (ad.getView().getParent() instanceof ViewGroup){
            ((ViewGroup)ad.getView().getParent()).removeView(ad.getView());
        }
        rlContainer.removeAllViews();
        rlContainer.addView(bean.getAd().getView());
        bean.getAd().getView().requestFocus();
    }

    public View getView() {
        return view;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            TADemoAnimationUtil.enlarge(v, 1.2F, 300);
        }else {
            TADemoAnimationUtil.shrink(v, 1.2F, 300);
        }
    }
}
