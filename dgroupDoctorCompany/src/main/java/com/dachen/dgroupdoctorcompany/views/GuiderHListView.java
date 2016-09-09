package com.dachen.dgroupdoctorcompany.views;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.dachen.dgroupdoctorcompany.adapter.CompanyListGuide;

import java.util.ArrayList;
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
    private ArrayList<String> mListGuide;              //导航Listview数据
    private Map<Integer, ArrayList<String>> departList;   //导航Listview数据任务栈
    private CompanyListGuide mListGuideAdapter;
    private Map<Integer, String> listGuideMap; //公司部门id任务栈;
    private int currentPosition = 0;//任务栈任务数


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
        mListGuide = new ArrayList<>();
        departList = new LinkedHashMap<>();
        listGuideMap = new LinkedHashMap<>();
    }

    /**
     * 设置外部Adapter
     */
    public void setAdapter(CompanyListGuide adapter) {
        mListGuideAdapter = adapter;
        mHListView2.setAdapter(mListGuideAdapter);
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
            mListGuide.add(departName);
            listGuideMap.put(currentPosition, departId);
            departList.put(currentPosition, copyToNewList(mListGuide));
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
            mListGuide.clear();
            mListGuide.addAll(departList.get(position));    //得到前一个任务栈导航列表数据集合
            listGuideMap.remove(position + 1);//移除当前任务
            departList.remove(position + 1);//移除
            --currentPosition;
        }
        return "";
    }

    /**
     * 得到倒数第二个的任务id
     *
     * @return id
     */
    public String reMoveTaskId() {
        int position = currentPosition - 2;//当前任务栈id数
        return listGuideMap.get(position);
    }

    int oldPosition;

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
            departList.put(currentPosition, copyToNewList(mListGuide));
            listGuideMap.put(currentPosition, listGuideMap.get(position));
            String idDep = listGuideMap.get(position);
            oldPosition = position;
            currentPosition++;
            return idDep;
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
        return listGuideMap.get(position);
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
    public ArrayList<String> copyToNewList(ArrayList<String> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        return arrayList;
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
        ArrayList<String> arrayList = departList.get(currentPosition - 1);
        Log.d("zxy :", "151 : GuiderHListView : getLastDerpartName : departListsize = " + departList.size() + "  " +
                currentPosition + " " + arrayList.get(arrayList.size() - 1));
        return arrayList.get(arrayList.size() - 1);
    }

    public Map<Integer, ArrayList<String>> getDepartList() {
        return departList;
    }

    public void setDepartList(Map<Integer, ArrayList<String>> departList) {
        this.departList = departList;
    }

    public ArrayList<String> getListGuide() {
        return mListGuide;
    }

    public void setListGuide(ArrayList<String> listGuide) {
        mListGuide = listGuide;
    }

    public CompanyListGuide getListGuideAdapter() {
        return mListGuideAdapter;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
