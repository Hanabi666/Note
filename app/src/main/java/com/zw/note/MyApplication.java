package com.zw.note;

import android.app.Application;

import database.NoteInfoDBHelper;
import preference.MySharedPreference;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initNoteInfo();
    }

    private void initNoteInfo() {
        if(MySharedPreference.getInstance(this).getBoolean("isFirstOpen", true)){
            NoteInfoDBHelper.getInstance(this).openWriteLink();
            NoteInfoDBHelper.getInstance(this).initNoteInfo();
            NoteInfoDBHelper.getInstance(this).closeLink();
            MySharedPreference.getInstance(this).save("isFirstOpen", true);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
