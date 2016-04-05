package indi.yume.app.passwordbox.model.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by bush2 on 2016/4/4.
 */
@Database(name = MainDataBase.NAME, version = MainDataBase.VERSION)
public class MainDataBase {
    public static final String NAME = "main_database";

    public static final int VERSION = 1;
}
