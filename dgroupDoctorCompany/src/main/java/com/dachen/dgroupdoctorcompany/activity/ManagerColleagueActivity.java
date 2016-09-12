package com.dachen.dgroupdoctorcompany.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.dachen.dgroupdoctorcompany.R;
import com.dachen.dgroupdoctorcompany.db.dbdao.CompanyContactDao;
import com.dachen.dgroupdoctorcompany.entity.BaseSearch;
import com.dachen.dgroupdoctorcompany.entity.CompanyContactListEntity;
import com.dachen.dgroupdoctorcompany.entity.CompanyDepment;
import com.dachen.dgroupdoctorcompany.fragment.AddressList;
import com.dachen.dgroupdoctorcompany.utils.UserInfo;

import java.util.List;

/**
 * Created by Burt on 2016/8/17.
 */
public class ManagerColleagueActivity extends CompanyContactListActivity implements View.OnClickListener{
    CompanyContactDao companyContactDao;

    boolean isAddPeople = false;
   // String idDep;
    String companyId;
    CompanyContactListEntity entity;
    RelativeLayout rl_addpeople;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    if (null!=entity){
                        isAddPeople = true;
                        getOrganization(entity.id);
                    }
                    break;
            }
        }
    };
    private boolean fistAdd  = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        rl_addpeople = (RelativeLayout) findViewById(R.id.rl_addpeople);
        rl_addpeople.setOnClickListener(this);
        rl_addpeople.setVisibility(View.VISIBLE);
        companyContactDao = new CompanyContactDao(ManagerColleagueActivity.this);
        List<CompanyContactListEntity> lists = companyContactDao.queryAll();
      //  idDep = AddressList.deptId;
        companyId = UserInfo.getInstance(this).getCompanyId();
        String depName = getIntent().getStringExtra("depName");
        setBaseDepartName(depName);
        setTitle(baseDepartName);
    }

    @Override
    public int getContent() {
        return isManager;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 300) {
            Message message = new Message();
            message.what = 1001;
            mHandler.sendMessage(message);
        }
       /* mMyThread = new MyThread();
        mMyThread.start();*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
       switch (v.getId()){
            case R.id.rl_addpeople:
                Intent intent = new Intent(this, FriendsContactsActivity.class);
                if (fistAdd){
                    intent.putExtra("deptid", AddressList.deptId);//第一次取固定的
                    fistAdd = false;
                    Log.d("zxy :", "91 : ManagerColleagueActivity : onClick : deptid = "+idDep);
                }else if(!android.text.TextUtils.isEmpty(idDep)) {
                    intent.putExtra("deptid",idDep);
                }else{
                    intent.putExtra("deptid", AddressList.deptId);
                }
                startActivityForResult(intent, 200);
                break;
        }
    }

    public void onColleagueEdit(CompanyContactListEntity c2,int position){
        entity = c2;
        Intent intent = new Intent(ManagerColleagueActivity.this, DeleteColleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("colleage", c2);
        intent.putExtra("colleage", bundle);
        intent.putExtra("position",position+"");
        startActivityForResult(intent, 300);
    }
    public void checkDepChecked(BaseSearch contact){
        for (int i=0;i<list.size();i++){
            if (list.get(i) instanceof CompanyDepment.Data.Depaments ){
                CompanyDepment.Data.Depaments depaments = (CompanyDepment.Data.Depaments)list.get(i);
                depaments.check = false;
                list.set(i,contact);
            }

        }
         if (contact instanceof CompanyDepment.Data.Depaments) {
             CompanyDepment.Data.Depaments depaments = (CompanyDepment.Data.Depaments)contact;
             depaments.check = true;
             int position = list.indexOf(contact);
             list.set(position,contact);
        }
    }

}
