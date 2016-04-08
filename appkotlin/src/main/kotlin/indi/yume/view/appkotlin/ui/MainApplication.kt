package indi.yume.view.appkotlin.ui

import android.app.Application

import com.raizlabs.android.dbflow.config.FlowManager

/**
 * Created by bush2 on 2016/4/5.
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        FlowManager.init(this)
    }
}
