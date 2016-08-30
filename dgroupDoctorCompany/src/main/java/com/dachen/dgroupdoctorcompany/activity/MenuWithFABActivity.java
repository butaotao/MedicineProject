package com.dachen.dgroupdoctorcompany.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dachen.common.utils.ToastUtil;
import com.dachen.dgroupdoctorcompany.R;
import com.dachen.dgroupdoctorcompany.adapter.SingnTodayAdapter;
import com.dachen.dgroupdoctorcompany.app.Constants;
import com.dachen.dgroupdoctorcompany.base.BaseActivity;
import com.dachen.dgroupdoctorcompany.entity.SignInList;
import com.dachen.dgroupdoctorcompany.utils.TitleManager;
import com.dachen.dgroupdoctorcompany.views.CustomButtonFragment;
import com.dachen.dgroupdoctorcompany.views.FloatingActionButton;
import com.dachen.dgroupdoctorcompany.views.FloatingActionMenu;
import com.dachen.dgroupdoctorcompany.views.SubActionButton;
import com.dachen.medicine.common.utils.TimeUtils;
import com.dachen.medicine.entity.Result;
import com.dachen.medicine.net.HttpManager;
import com.dachen.medicine.net.Params;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;


public class MenuWithFABActivity extends SignInActivity implements View.OnClickListener{
    PullToRefreshListView refreshScrollView;
    int pageIndex = 0;
    private int pageSize = 20;
    private List<SignInList.Data.DataList.ListVisitVo> mDataLists = new ArrayList<>();
    SingnTodayAdapter mAdapter;
    TextView tv_week;
    TextView tv_time;
    ImageView iv_alert;
    RelativeLayout rl_titlebar;
    ImageView iv_back;
    TextView tv_back;
    //adapter_menusign
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_menu_sing, null);
        setContentView(view);
        setTitle("");
        rl_titlebar = (RelativeLayout) findViewById(R.id.rl_titlebar);
        rl_titlebar.setBackgroundColor(getResources().getColor(R.color.color_3cbaff));
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setTextColor(Color.WHITE);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setBackgroundResource(R.drawable.icon_back_n);
        findViewById(R.id.line_titlebar).setVisibility(View.GONE);
        TitleManager.showImage(this,view,this,"",R.drawable.notice);
        changerTitleBar();
        findViewById(R.id.btn_sinrecord).setOnClickListener(this);
        tv_week = (TextView) findViewById(R.id.tv_week);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_week.setText(TimeUtils.getWeek(System.currentTimeMillis()));
        tv_time.setText(TimeUtils.getTimeDay( )+" "+TimeUtils.getTimesHourMinute(System.currentTimeMillis()));
        iv_alert = (ImageView) findViewById(R.id.iv_alert);
        refreshScrollView = (PullToRefreshListView) findViewById(R.id.refresh_scroll_view);
        refreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshScrollView.setFocusable(false);
        CustomButtonFragment fragment = new CustomButtonFragment();
        fragment.setActivity(this);
        refreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex = 0;
                //mMeetingList.clear();
                getListData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshScrollView.onRefreshComplete();
            }
        });
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
        mAdapter = new SingnTodayAdapter(this);
        refreshScrollView.setAdapter(mAdapter);
        getListData();
    }

    private void getListData() {
        showLoadingDialog();
        String type = "";
        new HttpManager().get(this, Constants.GET_VISIT_LIST, SignInList.class,
                Params.getList(MenuWithFABActivity.this, type, "", pageIndex, pageSize),
                this, false, 4);
    }
    public void start(){
        Intent intent2 = new Intent(this,SelectAddressActivity.class);
        intent2.putExtra("mode",AddSignInActivity.MODE_VISIT);
        intent2.putExtra("type","signle");
        intent2.putExtra("poi",this.POI);
        intent2.putExtra("distance",this.distance);
        intent2.putExtra("latitude",this.latitude);
        intent2.putExtra("longitude",this.longitude);
        intent2.putExtra("city", this.city);
        startActivity(intent2);
    }
    @Override
    public void onSuccess(Result response) {
        closeLoadingDialog();
        refreshScrollView.onRefreshComplete();
        if(null!=response){
            if (response instanceof SignInList){
                SignInList signInList = (SignInList) response;
                SignInList.Data data = signInList.data;
                if(null != data ){
                    int beforeSize = mDataLists.size();
                    if(null != data.dataList && data.dataList.size()>0){
                        if (pageIndex==0){
                            mDataLists.clear();
                        }
                        mDataLists.addAll(data.dataList.get(0).listVisitVo);
//                    findViewById(R.id.empty_view).setVisibility(View.GONE);
                    }else{
//                    if(pageIndex == 0){
//                        findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
//                    }
                    }

                    if(pageIndex==0){

                        mAdapter.addData(mDataLists,true);
                    }else{
                        mAdapter.addData(mDataLists,false);
                    }
                    mAdapter.notifyDataSetChanged();
                    if(beforeSize>0){
                        int afterSize = mDataLists.size();
                        if(beforeSize == afterSize){
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }else{
                }

            }
            }

    }

    @Override
    public void onSuccess(ArrayList response) {

    }

    @Override
    public void onFailure(Exception e, String errorMsg, int s) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_sinrecord:
                Intent signIntent = new Intent(this,SignListActivity.class);
                startActivity(signIntent);
                break;
            case R.id.iv_stub:
                Intent intent4 = new Intent(this,SigninRemindActivity.class);
                startActivity(intent4);
                break;
        }
    }
}