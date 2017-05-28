package com.example.administrator.yuekaob;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * date:2017/5/28 0028
 * authom:贾雪茹
 * function:
 */

public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<Bean>list;

    public MyAdapter(Context context, List<Bean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder;
        if(view==null){

            view=View.inflate(context,R.layout.item,null);
            holder=new MyHolder();
            holder.text= (TextView) view.findViewById(R.id.textview);
            view.setTag(holder);
        }else{
            holder= (MyHolder) view.getTag();
        }
        holder.text.setText(list.get(i).getName());
        holder.box.setChecked(list.get(i).isCb());
        return view;
    }
    class MyHolder{
        TextView text;
        CheckBox box;
    }
}
