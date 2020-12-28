package com.lechuan.midunovel2.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lechuan.midunovel2.bean.DemoBean;

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
        View v;
        if (convertView == null){
            v = new AdListViewHolder(parent.getContext(), parent, dataList.get(position)).getView();
        }else {
            v = ((AdListViewHolder) convertView.getTag()).getView();
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
}
