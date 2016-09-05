package com.dachen.dgroupdoctorcompany.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dachen.dgroupdoctorcompany.R;
import com.dachen.dgroupdoctorcompany.activity.LitterAppActivity;
import com.dachen.dgroupdoctorcompany.activity.MeetingListActivity;
import com.dachen.dgroupdoctorcompany.activity.MenuWithFABActivity;
import com.dachen.dgroupdoctorcompany.activity.RecordActivity;
import com.dachen.dgroupdoctorcompany.activity.VisitListActivity;
import com.dachen.dgroupdoctorcompany.adapter.AppcenterAdapter;
import com.dachen.dgroupdoctorcompany.app.Constants;
import com.dachen.dgroupdoctorcompany.db.dbdao.DepAdminsListDao;
import com.dachen.dgroupdoctorcompany.entity.MyAppBean;
import com.dachen.medicine.entity.Result;
import com.dachen.medicine.net.HttpManager;
import com.dachen.medicine.net.HttpManager.OnHttpListener;
import com.dachen.medicine.net.Params;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Burt on 2016/2/18.
 */
public class CompanyCenterFragment extends BaseFragment implements OnHttpListener, View.OnClickListener, AdapterView
        .OnItemClickListener {
    private View mRootView;

    TextView tv_login_title;
    RelativeLayout rl_companycontact;
    RelativeLayout rl_litterApp;
    RelativeLayout rl_sign_in;
    RelativeLayout rl_singrecord;
    DepAdminsListDao dao;
    private ListView mLvAppCenter;
    private AppcenterAdapter mAdapter;
    private List<MyAppBean.DataBean.PageDataBean> mPageData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mRootView = LayoutInflater.from(mActivity).inflate(
                R.layout.activity_companycenter, null);
        ButterKnife.bind(mActivity);
        dao = new DepAdminsListDao(mActivity);
        tv_login_title = (TextView) mRootView.findViewById(R.id.tv_title);
        tv_login_title.setText("企业中心");
        mLvAppCenter = (ListView) mRootView.findViewById(R.id.lv_appcenter);
        mLvAppCenter.setOnItemClickListener(this);
        mAdapter = new AppcenterAdapter(mActivity,mPageData);
        mLvAppCenter.setAdapter(mAdapter);
        initData();
        return mRootView;
    }

    private void initData() {
        new HttpManager<MyAppBean>().post(mActivity, Constants.GET_APPCENTER, MyAppBean.class,
                Params.getAppCenterParams(mActivity), new OnHttpListener<Result>() {

            @Override
            public void onSuccess(Result response) {
                if (response instanceof MyAppBean ) {
                    MyAppBean bean = (MyAppBean) response;
                    mPageData = bean.data.pageData;
                    Log.d("zxy :", "79 : CompanyCenterFragment : onSuccess : "+mPageData.size()+", "+mPageData.toString());
                    mAdapter.addData(mPageData);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onSuccess(ArrayList<Result> response) {
            }

            @Override
            public void onFailure(Exception e, String errorMsg, int s) {
            }
        },false,1);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // setUpViews();
    }


    @Override
    public void onClick(View v) {
/*        switch (v.getId()){
            case R.id.rl_conferencemangerment:
                showLoadingDialog();
                Intent intent = new Intent(mActivity, MeetingListActivity.class);//会议
                startActivity(intent);
                break;
            case R.id.rl_companycontact:
//                new HttpManager().get(mActivity, Constants.GET_VISIT_URL, H5Url.class, Params
//                        .getInfoParams(mActivity),this,false, 1);
                Intent visitIntent = new Intent(mActivity,VisitListActivity.class);
                startActivity(visitIntent);
                break;
            case R.id.rl_sign_in:
                showLoadingDialog();
                GuiderDialog dialog = new GuiderDialog(mActivity);
                dialog.show();
                *//*Intent signIntent = new Intent(mActivity,SignInActivity.class);
                startActivity(signIntent);*//*
                Intent signIntent = new Intent(mActivity,MenuWithFABActivity.class);
                startActivity(signIntent);
                break;
            case R.id.rl_singrecord:

                Intent singRecordIntent = new Intent(mActivity,RecordActivity.class);
                startActivity(singRecordIntent);
                break;
            case R.id.rl_litterapp:
                Intent litterAppIntent = new Intent(mActivity,LitterAppActivity.class);
                startActivity(litterAppIntent);
                break;
        }*/
    }

    @Override
    public void onStop() {
        super.onStop();
        closeLoadingDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String protocol = mAdapter.getItem(position).protocol;
        switch (protocol){
            case "local://meeting"://会议直播
                showLoadingDialog();
                Intent intent = new Intent(mActivity, MeetingListActivity.class);//会议
                startActivity(intent);
                break;
            case "local://visit"://客户拜访
                Intent visitIntent = new Intent(mActivity,VisitListActivity.class);
                startActivity(visitIntent);
                break;
            case "local://signed"://签到
                showLoadingDialog();
                /*GuiderDialog dialog = new GuiderDialog(mActivity);
                dialog.show();*/
                Intent signIntent = new Intent(mActivity,MenuWithFABActivity.class);
                startActivity(signIntent);
                break;
            case "local://statistics"://业务统计
                Intent singRecordIntent = new Intent(mActivity,RecordActivity.class);
                startActivity(singRecordIntent);
                break;
            case "lightapp://":
                Intent litterAppIntent = new Intent(mActivity,LitterAppActivity.class);
                startActivity(litterAppIntent);
                break;
        }
    }
}
