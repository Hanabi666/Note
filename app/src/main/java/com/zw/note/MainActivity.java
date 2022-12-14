package com.zw.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import adapter.NoteListAdapter;
import database.NoteInfoDBHelper;
import entity.NoteEntity;
import preference.MySharedPreference;

public class MainActivity extends AppCompatActivity {

    MyBroadCast myBroadCast = new MyBroadCast();
    NoteInfoDBHelper noteInfoDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zw.note"+".FRESH_NOTE_CONTENT_FRAGMENT");
        registerReceiver(myBroadCast, intentFilter);
        initDataBase();
        FloatingActionButton fab = findViewById(R.id.add_floating_btn);
        fab.setOnClickListener(view -> {
            if (MySharedPreference.getInstance(this).getBoolean("isTwoPage", false)){
                NoteContentActivity.actionStart(this, null);
            }
        });
    }

    private void initDataBase() {
        noteInfoDBHelper = NoteInfoDBHelper.getInstance(this);
        noteInfoDBHelper.openWriteLink();
        noteInfoDBHelper.openReadLink();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadCast);
        noteInfoDBHelper.closeLink();
    }

    static class MyBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            String noteTitle = intent.getStringExtra("noteTitle");
//            String noteContent = intent.getStringExtra("noteContent");
//            NoteContentFragment noteContentFragment = (NoteContentFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.note_content_fragment);
//            View view = findViewById(R.id.note_content_layout);
//            view.setVisibility(View.VISIBLE);
//            noteContentFragment.refresh(noteTitle, noteContent);
        }
    }
}