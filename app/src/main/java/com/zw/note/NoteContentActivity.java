package com.zw.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Date;

public class NoteContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String noteTitle = getIntent().getStringExtra("noteTitle");
        String noteContent = getIntent().getStringExtra("noteContent");
        NoteContentFragment noteContentFragment = (NoteContentFragment) getSupportFragmentManager()
                .findFragmentById(R.id.note_content_fragment);
        noteContentFragment.refresh(noteTitle, noteContent);
    }

    public static void actionStart(Context context, String noteTitle, String noteContent){
        Intent intent = new Intent(context, NoteContentActivity.class);
        intent.putExtra("noteTitle", noteTitle);
        intent.putExtra("noteContent", noteContent);
        context.startActivity(intent);
    }
}