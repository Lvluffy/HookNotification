package com.luffy.hooknotification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by lvlufei on 2019/3/12
 *
 * @desc 通知监控服务
 */
@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService {

    // 在收到消息时触发
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        // 获取接收通知的时间
        long l = sbn.getPostTime();
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);


        // 测试-拿到所有的数据
        Set<String> stringSet = extras.keySet();
        Iterator<String> it = stringSet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = extras.get(key);
            Log.i("text", "key = " + key + "value = " + value);
        }

        // 打印数据、显示数据
        String log = "Notification Posted " + "time:" + l + "notificationPkg:" + notificationPkg + "notificationTitle:" + notificationTitle + "notificationText:" + notificationText;
        Log.i("XSL_Test", log);
        EventBus.getDefault().post(new MessageEvent(log + "\n"));
    }

    // 在删除消息时触发
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        // 获取接收通知的时间
        long l = sbn.getPostTime();
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);

        // 测试-拿到所有的数据
        Set<String> stringSet = extras.keySet();
        Iterator<String> it = stringSet.iterator();
        while (it.hasNext()) {
            String key = it.next();
            Object value = extras.get(key);
            Log.i("text", "key = " + key + "value = " + value);
        }

        // 打印数据、显示数据
        String log = "Notification removed " + "time:" + l + "notificationPkg:" + notificationPkg + "notificationTitle:" + notificationTitle + "notificationText:" + notificationText;
        Log.i("XSL_Test", log);
        EventBus.getDefault().post(new MessageEvent(log + "\n"));
    }
}