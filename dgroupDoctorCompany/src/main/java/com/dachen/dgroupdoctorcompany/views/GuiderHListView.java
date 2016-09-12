package com.dachen.dgroupdoctorcompany.views;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.dachen.dgroupdoctorcompany.adapter.CompanyListGuide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @项目名 MedicineProject
 * @Author: zxy on 16/8/23下午7:40.
 * @描述 水平导航列表
 */
public class GuiderHListView extends HListview2 {
    Context mContext;
    private HListview2 mHListView2;
    private ArrayList<Guider> mListGuide;              //导航Listview数据
    private ArrayList<GuiderHListviewBean> mListGuideId;              //导航Listview数据
    private Map<String , ArrayList<Guider>> departList;   //导航Listview数据任务栈
    private CompanyListGuide mListGuideAdapter;
    private Map<Integer, Map<String , ArrayList<String>>> listGuideMap; //公司部门id任务栈;
    private int currentPosition = 0;//任务栈任务数
    private HashMap<Integer, GuiderHListviewBean> mBeanHashMap;


    public GuiderHListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @Override
    public LayoutTransition getLayoutTransition() {
        return super.getLayoutTransition();
    }

    private void initView(Context context) {
        mContext = context;
        mHListView2 = this;
        mListGuide = new ArrayList<>();//储存部门列表
        mListGuideId = new ArrayList<>();
        departList = new LinkedHashMap<>();
        listGuideMap = new LinkedHashMap<>();
        mBeanHashMap = new HashMap<>();
    }

    /**
     * 设置外部Adapter
     */
    public void setAdapter(CompanyListGuide adapter) {
        if (adapter != null) {
            mListGuideAdapter = adapter;
            mHListView2.setAdapter(mListGuideAdapter);
        }
    }

    /**
     * 设置内置Adapter
     */
    public void setAdapter() {
        mListGuideAdapter = new CompanyListGuide(mContext, mListGuide);
        mHListView2.setAdapter(mListGuideAdapter);
    }

    /**
     * 添加新的任务到任务栈
     *
     * @param departName 名字
     * @param departId   数据id
     */
    public void addTask(String departName, String departId) {
        if (mListGuide != null && listGuideMap != null && departList != null) {
            Guider guider = new Guider(departId,departName);
            mListGuide.add(guider);
            GuiderHListviewBean bean = new GuiderHListviewBean(departId,copyToNewList(mListGuide));
            mBeanHashMap.put(currentPosition,bean);//栈
            currentPosition++;
        }
    }

    /**
     * 移除最后任务
     *
     * @return
     */
    public String reMoveTask() {
        if (mListGuide != null && listGuideMap != null && departList != null) {
            int position = currentPosition - 2;//当前任务栈id数
            GuiderHListviewBean bean = mBeanHashMap.get(position);
            mListGuide.clear();
            mListGuide.addAll(bean.departList);
            mBeanHashMap.remove(position+1);
            --currentPosition;
            return bean.id;
            //mListGuide.addAll(departList.get(position));    //得到前一个任务栈导航列表数据集合
            //listGuideMap.remove(position + 1);//移除当前任务
            //departList.remove(position + 1);//移除
        }
        return "";
    }

    /**
     * 得到倒数第二个的任务id
     *
     * @return id
     */
    public String reMoveTaskId() {
        if (listGuideMap !=null) {
            int position = currentPosition - 2;//当前任务栈id数
            GuiderHListviewBean bean = mBeanHashMap.get(position);
            return bean.id;
            // Map<String, ArrayList<String>> listMap = listGuideMap.get(position);
        }
        return "";
    }

    int oldPosition;
    int backPosition = 0;


    /**
     * 跳到指定任务栈
     *
     * @param position
     */
    public String addBackTask(int position) {
        if (mListGuide != null && listGuideMap != null && departList != null) {
            int forCount = oldPosition - position;
            for (int i = 0; i < forCount; i++) {
                mListGuide.remove(oldPosition - i);
            }

            GuiderHListviewBean oldBean;
            if (position <backPosition) {
                oldBean = mBeanHashMap.get(position);
            }else {
                oldBean = mBeanHashMap.get(currentPosition - 1 - forCount);
            }

            GuiderHListviewBean bean = new GuiderHListviewBean(oldBean.id,oldBean.departList);

            mBeanHashMap.put(currentPosition,bean);

            backPosition = position;
            currentPosition++;
            // departList.put(listGuideMap.get(position), copyToNewList(mListGuide));
            // listGuideMap.put(currentPosition, listGuideMap.get(position));
            // String idDep = listGuideMap.get(position);
            oldPosition = mListGuideAdapter.getCount() - 1;
            return oldBean.id;
        }
        return "";
    }

    /**
     * 得到返回的id
     *
     * @param position position
     * @return id
     */
    public String addBackTaskId(int position) {
        int forCount = oldPosition - position;
        Log.d("zxy :", "159 : GuiderHListView : addBackTaskId : oldPosition = "+oldPosition+", position = "+position);
        GuiderHListviewBean oldBean;
        if (position <backPosition) {
            oldBean = mBeanHashMap.get(position);
        }else {
            oldBean = mBeanHashMap.get(currentPosition - 1 - forCount);
        }
        Log.d("zxy :", "162 : GuiderHListView : addBackTaskId : oldBean.id = "+oldBean.id);
        return oldBean.id;
    }

    public void setOldPosition() {
        this.oldPosition = mListGuideAdapter.getCount() - 1;
    }

    public void notifyDataSetChanged() {
        mListGuideAdapter.notifyDataSetChanged();
    }




    /**
     * 将集合拷贝到一个新集合中
     */
    public ArrayList<GuiderHListviewBean> copyToNewBeanList(ArrayList<GuiderHListviewBean> list) {
        ArrayList<GuiderHListviewBean> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        return arrayList;
    }
    /**
     * 将集合拷贝到一个新集合中
     */
    public ArrayList<Guider> copyToNewList(ArrayList<Guider> list) {
        ArrayList<Guider> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        return arrayList;
    }
    /**
     * 将集合拷贝到一个新集合中
     */
    public HashMap<String,ArrayList<String>> copyToNewMap(Map<String,ArrayList<String>> map) {
        Map<String,ArrayList<String>> hashMap = new HashMap<>() ;
        hashMap.putAll(map);
        return (HashMap<String, ArrayList<String>>) hashMap;
    }

    /**
     * 清空说有数据
     */
    public void clearData() {
        listGuideMap.clear();
        listGuideMap = null;
        mListGuide.clear();
        mListGuide = null;
        departList.clear();
        departList = null;
    }

    public String getLastDerpartName(int position) {
        if (departList !=null) {
            GuiderHListviewBean bean = mBeanHashMap.get(currentPosition - 1);
            ArrayList<Guider> departList = bean.departList;

            return departList.get(departList.size() - 1).name;
        }
        return "";
    }

    public Map<String , ArrayList<Guider>> getDepartList() {
        return departList;
    }

    public void setDepartList(Map<String , ArrayList<Guider>> departList) {
        this.departList = departList;
    }

    public ArrayList<Guider> getListGuide() {
        return mListGuide;
    }

    public void setListGuide(ArrayList<Guider> listGuide) {
        mListGuide = listGuide;
    }

    public CompanyListGuide getListGuideAdapter() {
        return mListGuideAdapter;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    class GuiderHListviewBean{
        public String id ;
        public ArrayList<Guider> departList;

        public GuiderHListviewBean() {
        }

        public GuiderHListviewBean(String id, ArrayList<Guider> departList) {
            this.id = id;
            this.departList = departList;
        }
    }

   public class Guider {
       public Guider(String id, String name) {
           this.id = id;
           this.name = name;
       }

       public Guider() {
       }

       public String id ;
        public String name;
    }
}
