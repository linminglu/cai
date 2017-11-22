package com.example.admin.caipiao33.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;

import com.example.admin.caipiao33.BuildConfig;
import com.example.admin.caipiao33.bean.BaseUrlBean;
import com.example.admin.caipiao33.utils.Global;
import com.socks.library.KLog;

import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application
{
    private static MyApplication app;
    private BaseUrlBean mBaseUrlBean;
    private static final String TAG = MyApplication.class.getName();
    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
    private Handler handler;

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        app = this;
        Global.setApplicationContext(this);
        // 日志工具初始化
        KLog.init(BuildConfig.LOG_DEBUG);
        KLog.e("MyApplication onCreate");

        //        PushAgent mPushAgent = PushAgent.getInstance(this);
        //        mPushAgent.setResourcePackageName("com.example.admin.caipiao33");
        //注册推送服务，每次调用register方法都会回调该接口
        //        mPushAgent.register(new IUmengRegisterCallback() {
        //
        //            @Override
        //            public void onSuccess(String deviceToken) {
        //                //注册成功会返回device token
        //            }
        //
        //            @Override
        //            public void onFailure(String s, String s1) {
        //
        //            }
        //        });

        //sdk开启通知声音
        //        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
        //		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
        //		mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

        //		mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        //		mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);


        //        UmengMessageHandler messageHandler = new UmengMessageHandler()
        //        {
        //            /**
        //             * 自定义消息的回调方法
        //             * */
        //            @Override
        //            public void dealWithCustomMessage(final Context context, final UMessage msg)
        //            {
        //
        //                handler.post(new Runnable()
        //                {
        //
        //                    @Override
        //                    public void run()
        //                    {
        //                        // TODO Auto-generated method stub
        //                        // 对自定义消息的处理方式，点击或者忽略
        //                        boolean isClickOrDismissed = true;
        //                        if (isClickOrDismissed)
        //                        {
        //                            //自定义消息的点击统计
        //                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
        //                        }
        //                        else
        //                        {
        //                            //自定义消息的忽略统计
        //                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
        //                        }
        //                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
        //                    }
        //                });
        //            }
        //
        //            /**
        //             * 自定义通知栏样式的回调方法
        //             * */
        //            @Override
        //            public Notification getNotification(Context context, UMessage msg)
        //            {
        //                switch (msg.builder_id)
        //                {
        //                    case 1:
        //                        Notification.Builder builder = new Notification.Builder(context);
        //                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
        //                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
        //                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
        //                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
        //                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
        //                        builder.setContent(myNotificationView)
        //                                .setSmallIcon(getSmallIconId(context, msg))
        //                                .setTicker(msg.ticker)
        //                                .setAutoCancel(true);
        //
        //                        return builder.getNotification();
        //                    default:
        //                        //默认为0，若填写的builder_id并不存在，也使用默认。
        //                        return super.getNotification(context, msg);
        //                }
        //            }
        //        };
        //        mPushAgent.setMessageHandler(messageHandler);

        //        /**
        //         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
        //         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
        //         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
        //         * */
        //        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler()
        //        {
        //            @Override
        //            public void dealWithCustomAction(Context context, UMessage msg)
        //            {
        //                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
        //            }
        //        };
        //        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
        //        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        //        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        //
        //
        //        //注册推送服务 每次调用register都会回调该接口
        //        mPushAgent.register(new IUmengRegisterCallback()
        //        {
        //            @Override
        //            public void onSuccess(String deviceToken)
        //            {
        //                UmLog.i(TAG, "device token: " + deviceToken);
        //                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
        //            }
        //
        //            @Override
        //            public void onFailure(String s, String s1)
        //            {
        //                UmLog.i(TAG, "register failed: " + s + " " + s1);
        //                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
        //            }
        //        });
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    public synchronized static MyApplication getInstance()
    {
        return app;
    }

    public void setBaseUrlBean(BaseUrlBean result)
    {
        this.mBaseUrlBean = result;
    }

    public BaseUrlBean getBaseUrlBean()
    {
        return mBaseUrlBean;
    }
}
