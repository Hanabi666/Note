package com.zw.note;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class NoteContentFragment extends Fragment implements View.OnClickListener {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_note_content, container, false);
        Log.d("NoteContent", "1");
        initView();
        return view;
    }

    private void initView() {
        ImageButton topBarLeftBtn = view.findViewById(R.id.top_bar_left_btn);
        TextView topBarTitle = view.findViewById(R.id.top_bar_title);
        Button topBarSaveBtn = view.findViewById(R.id.save_note_btn);
        topBarLeftBtn.setImageResource(R.drawable.ic_baseline_arrow_back_36);
        topBarLeftBtn.setVisibility(View.VISIBLE);
        topBarSaveBtn.setOnClickListener(this);
        topBarTitle.setText("编辑");
        topBarSaveBtn.setText("保存");
        topBarSaveBtn.setVisibility(View.VISIBLE);
        topBarSaveBtn.setOnClickListener(this);
    }

    public void refresh(String noteTitle, String noteContent){
        Log.d("NoteContent", "2");
        TextView title = view.findViewById(R.id.note_content_page_title);
        TextView content = view.findViewById(R.id.note_content_page_content);
        title.setText(noteTitle);
        content.setText(noteContent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.top_bar_left_btn){
            Toast.makeText(view.getContext(), "click button", Toast.LENGTH_SHORT).show();
        }else if(view.getId() == R.id.save_note_btn){
            Toast.makeText(view.getContext(), "click save button", Toast.LENGTH_SHORT).show();
        }
    }
}