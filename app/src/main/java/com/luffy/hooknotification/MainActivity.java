package com.luffy.hooknotification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luffy.hooknotification.utils.NotificationListenerServiceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lvlufei on 2019/3/12
 *
 * @desc 主界面
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text)
    TextView text;

    StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @OnClick({R.id.notification_monitor_on_btn, R.id.notification_monitor_off_btn, R.id.clear_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notification_monitor_on_btn:
                // 通知栏监控器开关-打开
                if (NotificationListenerServiceUtils.getInstance().isNotificationListenerEnabled(this)) {
                    Toast.makeText(getApplicationContext(), "监控器开关已打开", Toast.LENGTH_SHORT).show();
                } else {
                    NotificationListenerServiceUtils.getInstance().openNotificationListenerSettings(this);
                }
                break;
            case R.id.notification_monitor_off_btn:
                // 通知栏监控器开关-关闭
                if (NotificationListenerServiceUtils.getInstance().isNotificationListenerEnabled(this)) {
                    NotificationListenerServiceUtils.getInstance().openNotificationListenerSettings(this);
                } else {
                    Toast.makeText(getApplicationContext(), "监控器开关已关闭", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clear_data:
                // 清空数据
                text.setText("");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        stringBuilder.append(event.message);
        text.setText(stringBuilder.toString());
    }
}
