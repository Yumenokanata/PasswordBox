package indi.yume.app.passwordbox.ui;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by bush2 on 2016/4/5.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(this);
    }
}
