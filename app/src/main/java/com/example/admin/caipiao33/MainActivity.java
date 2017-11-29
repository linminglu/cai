package com.example.admin.caipiao33;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.application.MyApplication;
import com.example.admin.caipiao33.bean.BaseUrlBean;
import com.example.admin.caipiao33.fragment.GouCaiFragment;
import com.example.admin.caipiao33.fragment.HomePageFragment;
import com.example.admin.caipiao33.fragment.KaiJiangFragment;
import com.example.admin.caipiao33.fragment.UserFragment;
import com.example.admin.caipiao33.fragment.ZouShiFragment;
import com.example.admin.caipiao33.pagerBottomTabStrip.Controller;
import com.example.admin.caipiao33.pagerBottomTabStrip.OnTabItemSelectListener;
import com.example.admin.caipiao33.pagerBottomTabStrip.PagerBottomTabLayout;
import com.example.admin.caipiao33.update.DownloadService;
import com.example.admin.caipiao33.utils.AppUtils;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.UserConfig;
import com.umeng.analytics.MobclickAgent;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class MainActivity extends BaseActivity
{
    private int mClicks = 0;
    private Fragment mCurFragment;
    private Controller controller;
    private Handler handler = new Handler();
    private Context mContext;
    private final String mPageName = "MainActivity";

    class MyReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showUpdateDialog();
        mContext = this;
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_UM_NORMAL);

    }

    private void showUpdateDialog()
    {
        // 判断是否弹出更新提示框
        BaseUrlBean baseUrlBean = MyApplication.getInstance().getBaseUrlBean();
        if (null != baseUrlBean)
        {
            String currentVersion = baseUrlBean.getCurrentVersion();
            String lowVersion = baseUrlBean.getLowVersion();
            final String updateUrl = baseUrlBean.getUpdateUrl();

            try
            {
                int intCurrVersion = Integer.valueOf(currentVersion);
                int intLowVersion = Integer.valueOf(lowVersion);
                int appVersionCode = AppUtils.getAppVersionCode(this);
                if (appVersionCode >= intCurrVersion)
                {
                    return;
                }
                if (StringUtils.isEmpty(updateUrl) || !updateUrl.endsWith("apk"))
                {
                    ToastUtil.show("自动更新网址不对");
                    //                    return;
                }
                boolean isNormal = false;
                if (appVersionCode < intLowVersion)
                {
                    // 强制更新
                    isNormal = false;
                }
                else
                {
                    // 普通更新
                    isNormal = true;
                }
                final boolean finalNormal = isNormal;
                new MaterialDialog.Builder(this).content("更新")
                        .positiveText("现在更新")
                        .positiveColor(getResources().getColor(R.color.blue))
                        .cancelable(isNormal)
                        .negativeText(isNormal ? "下次再说" : "直接退出")
                        .negativeColor(getResources().getColor(R.color.middle_gray))
                        .onPositive(new MaterialDialog.SingleButtonCallback()
                        {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                            {
                                Intent intent = new Intent(MyApplication.getInstance(), DownloadService.class);
                                intent.putExtra(Constants.APK_DOWNLOAD_URL, updateUrl);
                                startService(intent);
                                if (!finalNormal)
                                {
                                    new MaterialDialog.Builder(MainActivity.this).content("正在更新，请稍候...")
                                            .cancelable(false)
                                            .show();
                                }
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback()
                        {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                            {
                                // 强制更新
                                if (!finalNormal)
                                {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            }
                        })
                        .show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(mContext);
        JAnalyticsInterface.onPageStart(getApplicationContext(), this.getClass()
                .getCanonicalName());
    }

    @Override
    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(mContext);
        JAnalyticsInterface.onPageEnd(getApplicationContext(), this.getClass().getCanonicalName());
    }

    //解决onResume获取不到传递的Intent
    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        if (intent != null && intent.getBooleanExtra("actionhome", false))
        {
            tabSwitchCenter(HomePageFragment.class);
        }
        setIntent(intent);
    }


    private void BottomTabTest()
    {
        PagerBottomTabLayout pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        //构建导航栏,得到Controller进行后续控制
        controller = pagerBottomTabLayout.builder()
                .addTabItem(R.drawable.selector_radio_check1, getString(R.string.main_tab_homepage), getResources()
                        .getColor(R.color.red))
                .addTabItem(R.drawable.selector_radio_check2, getString(R.string.main_tab_goucai), getResources()
                        .getColor(R.color.red))
                .addTabItem(R.drawable.selector_radio_check3, getString(R.string.main_tab_kaijiang), getResources()
                        .getColor(R.color.red))
                .addTabItem(R.drawable.selector_radio_check4, getString(R.string.main_tab_zoushi), getResources()
                        .getColor(R.color.red))
                .addTabItem(R.drawable.selector_radio_check5, getString(R.string.main_tab_user), getResources()
                        .getColor(R.color.red))
                //                                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                //                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

        //        controller.setMessageNumber("A",2);
        //        controller.setDisplayOval(0,true);

        controller.addTabItemClickListener(listener);
    }

    OnTabItemSelectListener listener = new OnTabItemSelectListener()
    {
        @Override
        public void onSelected(int index, Object tag)
        {
            switch (index)
            {
                case 0:
                    switchCenter(HomePageFragment.class);
                    break;
                case 1:
                    switchCenter(GouCaiFragment.class);
                    break;
                case 2:
                    switchCenter(KaiJiangFragment.class);
                    break;
                case 3:
                    switchCenter(ZouShiFragment.class);
                    break;
                case 4:
                    switchCenter(UserFragment.class);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onRepeatClick(int index, Object tag)
        {
        }
    };

    private void initView()
    {
        BottomTabTest();
        switchCenter(HomePageFragment.class);
    }

    @Override
    public void onBackPressed()
    {
        exitApp();
    }

    // 在2秒内 重复点击2次back键 就退出
    private void exitApp()
    {
        mClicks++;
        if (mClicks == 1)
        {
            ToastUtil.show(getString(R.string.pressargin));
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    mClicks = 0;
                }
            }, 2000);
        }
        else if (mClicks == 2)
        {
            finish();
            System.exit(0);
        }
    }

    public void switchCenter(Class<? extends Fragment> clazz)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment userFragment = fm.findFragmentByTag(clazz.getName());
        if (clazz == UserFragment.class)
        {
            if (UserConfig.getInstance().getToken(MainActivity.this) == null)
            {
                if (mCurFragment instanceof HomePageFragment)
                {
                    controller.setSelect(0);
                }
                else if (mCurFragment instanceof GouCaiFragment)
                {
                    controller.setSelect(1);
                }
                else if (mCurFragment instanceof KaiJiangFragment)
                {
                    controller.setSelect(2);
                }
                else if (mCurFragment instanceof ZouShiFragment)
                {
                    controller.setSelect(3);
                }
                else if (mCurFragment instanceof UserFragment)
                {
                    controller.setSelect(4);
                }
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_Main2_LOGIN);
                return;
            }
            else if (UserConfig.getInstance().getToken(MainActivity.this).getIsLogin() == 0)
            {
                if (mCurFragment instanceof HomePageFragment)
                {
                    controller.setSelect(0);
                }
                else if (mCurFragment instanceof GouCaiFragment)
                {
                    controller.setSelect(1);
                }
                else if (mCurFragment instanceof KaiJiangFragment)
                {
                    controller.setSelect(2);
                }
                else if (mCurFragment instanceof ZouShiFragment)
                {
                    controller.setSelect(3);
                }
                else if (mCurFragment instanceof UserFragment)
                {
                    controller.setSelect(4);
                }
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_Main2_LOGIN);
                return;
            }
        }
        if (userFragment == null)
        {
            try
            {
                userFragment = clazz.newInstance();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (null != mCurFragment && mCurFragment == userFragment)
        {
            // 重复点击同一个，直接返回
            return;
        }
        if (mCurFragment != null && mCurFragment != userFragment)
        {
            ft.hide(mCurFragment);
        }
        if (userFragment != null && !userFragment.isAdded())
        {
            ft.add(R.id.center_frame, userFragment, clazz.getName());
        }
        else
        {
            ft.show(userFragment);
        }

        // 通知fragment当前被显示或者隐藏
        if (clazz == GouCaiFragment.class)
        {
            ((GouCaiFragment) userFragment).fragmentShow();
        }
        else if (mCurFragment instanceof GouCaiFragment)
        {
            ((GouCaiFragment) mCurFragment).fragmentHide();
        }
        if (clazz == HomePageFragment.class)
        {
            ((HomePageFragment) userFragment).fragmentShow();
        }
        else if (mCurFragment instanceof HomePageFragment)
        {
            ((HomePageFragment) mCurFragment).fragmentHide();
        }

        ft.commitAllowingStateLoss();
        mCurFragment = userFragment;
    }

    /**
     * tabbar切换管理
     */
    public void tabSwitchCenter(Class<? extends Fragment> clazz)
    {
        //        String name = clazz.getName();
        if (clazz == HomePageFragment.class)
        {
            controller.setSelect(0);
        }
        else if (clazz == GouCaiFragment.class)
        {
            controller.setSelect(1);
        }
        else if (clazz == KaiJiangFragment.class)
        {
            controller.setSelect(2);
        }
        else if (clazz == ZouShiFragment.class)
        {
            controller.setSelect(3);
        }
        else if (clazz == UserFragment.class)
        {
            controller.setSelect(4);
        }
        switchCenter(clazz);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (mCurFragment != null)
        {
            mCurFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == Constants.REQUEST_CODE_2_LOGIN)
        {
            tabSwitchCenter(UserFragment.class);
        }
        if (resultCode == Constants.REQUEST_CODE_Main2_LOGOUT)
        {
            tabSwitchCenter(HomePageFragment.class);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        // 这里注释很重要，不保存fragment，如果被回收旧相当于重新打开app
        //        super.onSaveInstanceState(outState);
    }
}
