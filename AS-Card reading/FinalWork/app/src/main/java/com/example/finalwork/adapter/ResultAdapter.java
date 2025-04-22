package com.example.finalwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalwork.R;
import com.example.finalwork.model.GetDiscernResultResponce;

import java.util.List;

public class ResultAdapter extends ArrayAdapter<GetDiscernResultResponce.ResultBean> {
    private int resourceId;
    public ResultAdapter(Context context,int textViewResourceId, @Nullable List<GetDiscernResultResponce.ResultBean> data){
        super(context,textViewResourceId,data);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GetDiscernResultResponce.ResultBean result=getItem(position);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }
        else {
            view=convertView;
        }
        TextView kw=(TextView) view.findViewById(R.id.tv_keyword);
        TextView rt=(TextView) view.findViewById(R.id.tv_root);
        TextView sc=(TextView) view.findViewById(R.id.tv_score);
        kw.setText(result.getKeyword());
        rt.setText(result.getRoot());
        sc.setText(String.valueOf(result.getScore()));
        return view;


    }
}
