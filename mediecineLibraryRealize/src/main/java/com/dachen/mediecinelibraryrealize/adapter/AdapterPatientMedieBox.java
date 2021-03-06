package com.dachen.mediecinelibraryrealize.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dachen.mediecinelibraryrealize.R;
import com.dachen.mediecinelibraryrealize.activity.AdviceActivity;
import com.dachen.mediecinelibraryrealize.activity.BaiduMapActivity;
import com.dachen.mediecinelibraryrealize.activity.ErcordingProductActivity;
import com.dachen.mediecinelibraryrealize.entity.PatientMedieBoxs.Info;
import com.dachen.mediecinelibraryrealize.utils.TimeUtils;

import java.util.List;

public class AdapterPatientMedieBox extends BaseAdapter {
    List<Info> infos;
    Context context;
    String id;

    public AdapterPatientMedieBox(Context context, List<Info> infos, String id) {
        this.context = context;
        this.infos = infos;
        this.id = id;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return infos.size();
    }

    @Override
    public Object getItem(int p) {
        // TODO Auto-generated method stub
        return infos.get(p);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int p, View view, ViewGroup arg2) {
        ViewHolder holder;
        if (null == view) {
            view = View.inflate(context, R.layout.adapterpatientmediebox, null);
            holder = new ViewHolder();
            //holder.tv_company = (TextView) view.findViewById(R.id.tv_company);
            holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            holder.tv_total_des = (TextView) view.findViewById(R.id.tv_total_des);
            holder.rl_showlist = (RelativeLayout) view.findViewById(R.id.rl_showlist);
            holder.rl_recomend = (RelativeLayout) view.findViewById(R.id.rl_recomend);
            holder.rl_detail = (RelativeLayout) view.findViewById(R.id.rl_detail);
            //holder.tv_line = (TextView) view.findViewById(R.id.tv_line);
            holder.iv_havebuy = (ImageView) view.findViewById(R.id.iv_havebuy);
            holder.tv_advice = (TextView) view.findViewById(R.id.tv_advice);
            holder.iv_color = (ImageView) view.findViewById(R.id.iv_color);
            holder.rl_ercode = (RelativeLayout) view.findViewById(R.id.rl_ercode);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Info info = (Info) getItem(p);
        String companyname = "";
        if (!TextUtils.isEmpty(info.groupName)) {
            companyname = info.groupName;
        } else {
        }
        String advice = "";
        if ( !TextUtils.isEmpty(info.doctorName)){
            if (!TextUtils.isEmpty(companyname)) {
                advice = info.doctorName + "-" + companyname;
            } else {
                advice =  info.doctorName;
            }
        }else {
            if (info.type == 2) {
                advice = info.patientName + "用药建议";
            }else if  (info.type == 3) {
                advice = info.patientName + "健康关怀";
            }

        }
        if (info.type ==1){
            advice = info.patientName + "自建药方";
        }
        if (info.type == 2) {

            holder.tv_name.setText(advice);
            holder.tv_advice.setText("用药建议详情");
            holder.iv_color.setBackgroundColor(context.getResources().getColor(R.color
                    .head_bg_color));
        } else if (info.type ==1){
            holder.tv_name.setText(advice);


            holder.tv_advice.setText("自建药方详情");
            holder.iv_color.setBackgroundColor(context.getResources().getColor(R.color
                    .divide_line_color_press));

        }else if  (info.type == 3) {

            holder.tv_name.setText(advice);

            holder.tv_advice.setText("用药关怀详情");
            holder.iv_color.setBackgroundColor(context.getResources().getColor(R.color
                    .head_bg_color));
        }
        if (null != info.date) {
            info.date = info.date.replace("-", "/");
        }
        String time = com.dachen.medicine.common.utils.TimeUtils.getTime(Long.parseLong(info.date));
        holder.tv_time.setText(time);
        holder.tv_total_des.setText(info.species_number + "");

        holder.iv_havebuy.setVisibility(View.GONE);
        if (null != info.buyStatus ) {
            if (info.buyStatus.equals("2")) {
                holder.iv_havebuy.setVisibility(View.VISIBLE);
            }
        }
        holder.rl_ercode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ErcordingProductActivity.class);
                intent.putExtra("ercode", info.id);
                context.startActivity(intent);
            }
        });
        holder.rl_showlist.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, AdviceActivity.class);
                intent.putExtra("recipe_id", info.id);
                intent.putExtra("patient", id);
                if (null != info.doctor) {
                    intent.putExtra("name", info.doctor.name);
                } else {
                    intent.putExtra("name", "");
                }

                context.startActivity(intent);
            }
        });

        holder.rl_recomend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, BaiduMapActivity.class);
                intent.putExtra("code", info.id);
                String companyName = "";
                if (!TextUtils.isEmpty(info.groupName)) {
                    companyName = info.groupName;
                }
                String name = "";
                String patient = "";
                if (null != info.doctorName) {
                    if(!TextUtils.isEmpty(companyName)){
                        name = info.doctorName + "-" + companyName;
                    }else{
                        name = info.doctorName;
                    }
                }else{
                    patient = info.patientName;
                }
                intent.putExtra("name", name);
                intent.putExtra("patient", patient);
                context.startActivity(intent);
            }
        });
        /*holder.rl_detail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
               *//* Intent intent = new Intent(context, AdviceActivity.class);
                intent.putExtra("recipe_id", info.id);
                intent.putExtra("patient", id);
                if (null != info.doctor) {
                    intent.putExtra("name", info.doctor.name);
                } else {
                    intent.putExtra("name", "");
                }

                context.startActivity(intent);*//*
            }
        });*/
        // TODO Auto-generated method stub
        return view;
    }

    public static class ViewHolder {
        TextView tv_company;
        TextView tv_name;
        TextView tv_time;
        TextView tv_total_des;
        TextView tv_line;
        RelativeLayout rl_showlist;
        RelativeLayout rl_recomend;
        RelativeLayout rl_detail;
        ImageView iv_havebuy;
        TextView tv_advice;
        ImageView iv_color;
        RelativeLayout rl_ercode;
    }
}
