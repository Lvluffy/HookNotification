package com.luffy.hooknotification.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by lvlufei on 2019/5/21
 *
 * @name 通知监听服务-辅助工具
 */
public class NotificationListenerServiceUtils {

    private NotificationListenerServiceUtils() {
    }

    public static NotificationListenerServiceUtils getInstance() {
        return NotificationListenerServiceUtilsHolder.instance;
    }

    private static class NotificationListenerServiceUtilsHolder {
        private static final NotificationListenerServiceUtils instance = new NotificationListenerServiceUtils();
    }

    /**
     * 检测通知监听服务是否被授权
     *
     * @param context 上下文
     * @return 通知监听服务是否被授权
     */
    public boolean isNotificationListenerEnabled(Context context) {
        String pkgName = context.getPackageName();
        final String flat = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (String name : names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 打开通知监听设置界面
     *
     * @param contex 上下文
     */
    public void openNotificationListenerSettings(Context contex) {
        contex.startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    /**
     * 把应用的NotificationListenerService实现类disable再enable，即可触发系统rebing操作
     *
     * @param context                     上下文
     * @param notificationListenerService NotificationListenerService
     */
    public void toggleNotificationListenerService(Context context, Class<? extends NotificationListenerService> notificationListenerService) {
        ComponentName thisComponent = new ComponentName(context, notificationListenerService);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(thisComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(thisComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 确保运行
     *
     * @param context                     上下文
     * @param notificationListenerService NotificationListenerService
     */
    public void ensureCollectorRunning(Context context, Class<? extends NotificationListenerService> notificationListenerService) {
        ComponentName collectorComponent = new ComponentName(context, notificationListenerService);
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        boolean collectorRunning = false;
        List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);
        if (runningServices == null) {
            return;
        }
        for (ActivityManager.RunningServiceInfo service : runningServices) {
            if (service.service.equals(collectorComponent)) {
                if (service.pid == Process.myPid()) {
                    collectorRunning = true;
                }
            }
        }
        if (collectorRunning) {
            return;
        }
        toggleNotificationListenerService(context, notificationListenerService);
    }
}
