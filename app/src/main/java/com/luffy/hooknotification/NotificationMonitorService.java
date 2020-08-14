package com.luffy.hooknotification;

import android.annotation.SuppressLint;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.luffy.hooknotification.utils.BundleUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lvlufei on 2019/3/12
 *
 * @desc 通知监控服务
 */
@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService {

    public final String TAG = getClass().getSimpleName();

    // 在收到消息时触发
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String log = BundleUtils.getInstance().printBundle(sbn.getNotification().extras);
        Log.i(TAG, log);
        EventBus.getDefault().post(new MessageEvent(log + "\n"));
    }

    // 在删除消息时触发
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        String log = BundleUtils.getInstance().printBundle(sbn.getNotification().extras);
        Log.i(TAG, log);
        EventBus.getDefault().post(new MessageEvent(log + "\n"));
    }
}