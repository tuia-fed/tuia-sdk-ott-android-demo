package com.lechuan.midunovel2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lechuan.midunovel2.bean.DemoBean;
import com.mediamain.android.view.interfaces.IFoxTempletInfoFeedAd;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> File description: <p>
 * <p> Creator: Adroll   <p>
 * <p> Created date: 12/11/20 <p>
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class AdListAdapter extends BaseAdapter {

    private List<DemoBean> dataList = new ArrayList<>();

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdListViewHolder viewHolder = null;
        View v;
        if (convertView == null){
            viewHolder = new AdListViewHolder(parent.getContext(), parent, dataList.get(position));
            v = viewHolder.getView();
        }else {
            v = ((AdListViewHolder) convertView.getTag()).getView();
        }

        if (viewHolder != null && position == 0){
            v.requestFocus();
            dataList.get(1).setBeforeView(viewHolder.getTv());
        } else if (viewHolder != null && position == 2){
            dataList.get(1).setNextView(viewHolder.getTv());
        }
        return v;
    }

    public List<DemoBean> getDataList() {
        return dataList;
    }

    public void addData(DemoBean bean){
        dataList.add(bean);
    }

    public void setDataList(List<DemoBean> beanList){
        dataList.clear();
        dataList.addAll(beanList);
    }

    public void updateFocusDirection(){
        IFoxTempletInfoFeedAd iFoxTempletInfoFeedAd = dataList.get(1).getAd();
        if (iFoxTempletInfoFeedAd == null){
            return;
        }

        iFoxTempletInfoFeedAd.setFoxNextUpFocusView(dataList.get(1).getBeforeView());
        iFoxTempletInfoFeedAd.setFoxNextDownFocusView(dataList.get(1).getNextView());
    }
}
