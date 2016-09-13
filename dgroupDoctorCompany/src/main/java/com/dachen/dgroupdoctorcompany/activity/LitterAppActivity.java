package com.dachen.dgroupdoctorcompany.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dachen.dgroupdoctorcompany.R;
import com.dachen.dgroupdoctorcompany.adapter.BaseAdapter;
import com.dachen.dgroupdoctorcompany.js.MenuButtonBean;
import com.dachen.dgroupdoctorcompany.js.TitleBean;
import com.google.gson.Gson;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.engine.SystemWebChromeClient;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot1.event.EventBus;

/**
 * @项目名 MedicineProject
 * @Author: zxy on 16/8/26下午3:05.
 * @描述 TODO
 */
public class LitterAppActivity extends CordovaActivity {
    private TextView mTitle;
    private boolean mIsFirstLoadPage = true;//是否是第一次加载页面
    private TextView mClose;
    private Button mMenu;
    private PopupWindow popWindow;
    MenuButtonBean mMenuButtonBean;
    private ProgressBar mProgress;
    private String mUrl;
    private SystemWebView mSystemWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ActionSheetStyleiOS7);
        EventBus.getDefault().register(this);
        mUrl = getIntent().getStringExtra("url");
        loadUrl(mUrl);
    }


    private void loadProgress() {

    }

    @Override
    @SuppressWarnings({"deprecation", "ResourceType"})
    protected void createViews() {
        setContentView(R.layout.activity_litter_app);

        initWebView();

        initView();
        mProgress = (ProgressBar) findViewById(R.id.litter_app_progress);
        loadProgress();
    }


    public void onEventMainThread(TitleBean title){
        Gson gson = new Gson();
        TitleBean titleBean = gson.fromJson(title.title, TitleBean.class);
        mTitle.setText(titleBean.title);
    }
    public void onEventMainThread(MenuButtonBean bean){
        mMenuButtonBean = bean;
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title);
        mClose = (TextView) findViewById(R.id.close);
        mMenu = (Button) findViewById(R.id.right_menu);
        mProgress = (ProgressBar) findViewById(R.id.litter_app_progress);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.basicInfoActivity_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appView.canGoBack()) {
                    appView.backHistory();
                    mClose.setVisibility(View.VISIBLE);
                } else {
                    finish();
                }
            }
        });

        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow == null) {
                    View contentView = LitterAppActivity.this.getLayoutInflater().inflate(R.layout.apppopwindow, null);
                    popWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    popWindow.setFocusable(true);
                    popWindow.setBackgroundDrawable(new BitmapDrawable());
                    View rlScan = contentView.findViewById(R.id.rl_scan);
                    View rlFind = contentView.findViewById(R.id.rl_find);
                    popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                        }
                    });
                    rlScan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != popWindow && popWindow.isShowing()) {
                                popWindow.dismiss();
                            }
                            mProgress.setVisibility(View.VISIBLE);
                            mProgress.setMax(100);
                            mSystemWebView.reload();
                        }
                    });
                    rlFind.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != popWindow && popWindow.isShowing()) {
                                popWindow.dismiss();
                            }
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(mUrl);
                            intent.setData(content_url);
                            startActivity(intent);
                        }
                    });
                }
                popWindow.showAsDropDown(mMenu, 0, 10);
               /* if (popWindow == null) {
                    View contentView = View.inflate(LitterAppActivity.this,R.layout.litterapp_popwindow, null);
                    ListView popLv = (ListView) contentView.findViewById(R.id.pop_lv);
                    List<String> menu = initMenu();
                    popLv.setAdapter(new PopAdapter(LitterAppActivity.this, menu));
                    *//*if (mMenuButtonBean!=null&&"singleMenu".equals(mMenuButtonBean.menuType)) {
                        TextView textView = new TextView(LitterAppActivity.this);
                        textView.setText(mMenuButtonBean.singleMenu.menuText);
                        LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.pop_ll);
                        layout.addView(textView);
                    }*//*
                    popWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    popWindow.setFocusable(true);
                    popWindow.setBackgroundDrawable(new BitmapDrawable());
                    popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                        }
                    });
                }
                popWindow.showAsDropDown(mMenu, 0, 10);*/
            }
        });

    }

    private List<String> initMenu() {
        ArrayList<String> list = new ArrayList<>();
        list.add("刷新");
        list.add("在浏览器打开");
        return list;
    }

    private void initWebView() {
        appView.getView().setId(R.id.cordova_web_view_id);
        appView.getView().setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.container);
        viewGroup.addView(appView.getView());

        if (preferences.contains("BackgroundColor")) {
            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
            appView.getView().setBackgroundColor(backgroundColor);
        }

        appView.getView().requestFocusFromTouch();
    }

    @Override
    protected void inited() {
        //必须在初始化后才能设置WebChromeClient，不然systemWebView.getSystemWebViewEngine()为空
        mSystemWebView = (SystemWebView) appView.getView();

        mSystemWebView.setWebChromeClient(new SystemWebChromeClientEx(mSystemWebView.getSystemWebViewEngine()));
    }

    class SystemWebChromeClientEx extends SystemWebChromeClient {

        public SystemWebChromeClientEx(SystemWebViewEngine parentEngine) {
            super(parentEngine);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mTitle.setText(title);
            if (mIsFirstLoadPage) {
                //清除红点
               // ImSpUtils.spCommon().edit().putBoolean(AppImConst.USER_SP_DOC_COMMUNITY_NEW, false).apply();
                //EventBus.getDefault().post(new UnreadEvent(UnreadEvent.TYPE_NEW_COMMUNITY, 0));
                mIsFirstLoadPage = false;
            }

        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view,newProgress);

            mProgress.setProgress(newProgress);
            if (newProgress == 100) {
                mProgress.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            mClose.setVisibility(View.VISIBLE);
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    class PopAdapter extends BaseAdapter<String> {

        public PopAdapter(Context context) {
            super(context);
        }

        public PopAdapter(Context context, List<String> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(LitterAppActivity.this, R.layout.item_app_menupop, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            String menu = dataSet.get(position);
            holder.itemmenutext.setText(menu);
            if (position == getCount() -1) {
                holder.ivline1.setVisibility(View.GONE);
            }
            return convertView;
        }

        public class ViewHolder {
            public final TextView itemmenutext;
            public final LinearLayout itemappmenu;
            public final ImageView ivline1;
            public final View root;

            public ViewHolder(View root) {
                itemmenutext = (TextView) root.findViewById(R.id.item_menutext);
                itemappmenu = (LinearLayout) root.findViewById(R.id.item_appmenu);
                ivline1 = (ImageView) root.findViewById(R.id.iv_line1);
                this.root = root;
            }
        }
    }

}
