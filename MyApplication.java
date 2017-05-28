package com.example.administrator.yuekaob;

import android.app.Application;

import org.xutils.x;

/**
 * Effect :
 * <p>
 * Created by Administrator
 * Time by 2017/5/4 0004
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }
}
