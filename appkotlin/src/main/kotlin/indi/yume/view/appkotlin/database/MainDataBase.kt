package indi.yume.view.appkotlin.database

import com.raizlabs.android.dbflow.annotation.Database

/**
 * Created by bush2 on 2016/4/4.
 */
@Database(name = MainDataBase.NAME, version = MainDataBase.VERSION)
object MainDataBase {
    const val NAME = "main_database"

    const val VERSION = 1
}
