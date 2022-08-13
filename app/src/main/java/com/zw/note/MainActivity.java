package com.zw.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import preference.MySharedPreference;

public class MainActivity extends AppCompatActivity {

    MyBroadCast myBroadCast = new MyBroadCast();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zw.note"+".FRESH_NOTE_CONTENT_FRAGMENT");
        registerReceiver(myBroadCast, intentFilter);
        initView();
    }

    private void initView() {
        int maxWidthDP = (int) (getResources().getDisplayMetrics().widthPixels
                /getResources().getDisplayMetrics().density + 0.5f);
        if(maxWidthDP >= 600){
            MySharedPreference.getInstance(this).save("isTwoPage", true);
        }else{
            MySharedPreference.getInstance(this).save("isTwoPage", false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadCast);
    }

    class MyBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String noteTitle = intent.getStringExtra("noteTitle");
            String noteContent = intent.getStringExtra("noteContent");
            NoteContentFragment noteContentFragment = (NoteContentFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.note_content_fragment);
            View view = findViewById(R.id.note_content_layout);
            view.setVisibility(View.VISIBLE);
            noteContentFragment.refresh(noteTitle, noteContent);
        }
    }
}