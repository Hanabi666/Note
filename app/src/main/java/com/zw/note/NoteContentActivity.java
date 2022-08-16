package com.zw.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;

import entity.NoteEntity;

public class NoteContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NoteEntity noteEntity = (NoteEntity) getIntent().getSerializableExtra("NoteEntity");
        NoteContentFragment noteContentFragment = (NoteContentFragment) getSupportFragmentManager()
                .findFragmentById(R.id.note_content_fragment);
        assert noteContentFragment != null;
        noteContentFragment.refresh(noteEntity);
    }

    public static void actionStart(Context context, NoteEntity noteEntity){
        Intent intent = new Intent(context, NoteContentActivity.class);
        if (noteEntity == null){
            NoteEntity note = new NoteEntity();
            note.setId(-1);
            intent.putExtra("NoteEntity", (Serializable) note);
            context.startActivity(intent);
        }
        else{
            intent.putExtra("NoteEntity", (Serializable) noteEntity);
            context.startActivity(intent);
        }
    }
}