package com.appxcraft.msquaresample.Common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by darrenso on 6/11/14.
 */
public class ListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Entity> arrContent;
    LayoutInflater mLayoutInflater;


    public ListAdapter(Context mContext){
        this.mContext = mContext;
        arrContent = new ArrayList<Entity>();
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    public synchronized void addItem(Entity content){

        if(content == null)
            return;


        arrContent.add(content);

        notifyDataSetChanged();
    }

    public void removeContent(Entity content)
    {
        arrContent.remove(content);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrContent.size();
    }

    @Override
    public Object getItem(int position) {
        return arrContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null)
        {
            convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1,null,false);
            holder = new ViewHolder();
            holder.lblTitle =(TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();

        if (arrContent.size() > 0)
        {

            Entity objNotification = arrContent.get(position);

            holder.lblTitle.setText(objNotification.getMyValue04());

            holder.position = position;

        }

        return convertView;
    }


    static class ViewHolder {
        TextView lblTitle;
        int position;

    }
}
