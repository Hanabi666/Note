package com.zw.note;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;


public class NoteContentFragment extends Fragment {

    private static View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_note_content, container, false);
        return view;
    }

    public static void refresh(String noteTitle, String noteContent){
        TextView title = view.findViewById(R.id.note_content_page_title);
        TextView content = view.findViewById(R.id.note_content_page_content);
        title.setText(noteTitle);
        content.setText(noteContent);
    }
}