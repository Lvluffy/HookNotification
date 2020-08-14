package com.luffy.hooknotification.utils;

import android.os.Bundle;

import java.util.Iterator;

/**
 * Created by lvlufei on 2020-08-10
 *
 * @name Bundle-辅助工具
 */
public class BundleUtils {

    private BundleUtils() {

    }

    public static BundleUtils getInstance() {
        return BundleUtilsHolder.instance;
    }

    private static class BundleUtilsHolder {
        private static final BundleUtils instance = new BundleUtils();
    }

    /**
     * 打印Bundle数据
     *
     * @param bundle
     * @return
     */
    public String printBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> keys = bundle.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = bundle.get(key);
            if (value != null && value instanceof Bundle) {
                stringBuilder.append(printBundle((Bundle) value));
            } else {
                stringBuilder.append(key).append("=").append(value).append(",");
            }
        }
        return stringBuilder.toString();
    }
}
