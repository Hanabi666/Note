package com.zw.note;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Objects;

import database.NoteInfoDBHelper;
import entity.NoteEntity;


public class NoteContentFragment extends Fragment implements View.OnClickListener {

    private View view;
    private NoteEntity noteEntity;
    private TextView title;
    private TextView content;

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
        this.title = view.findViewById(R.id.note_content_page_title);
        this.content = view.findViewById(R.id.note_content_page_content);
        topBarLeftBtn.setImageResource(R.drawable.ic_baseline_arrow_back_36);
        topBarLeftBtn.setVisibility(View.VISIBLE);
        topBarSaveBtn.setOnClickListener(this);
        topBarTitle.setText("编辑");
        topBarSaveBtn.setText("保存");
        topBarSaveBtn.setVisibility(View.VISIBLE);
        topBarSaveBtn.setOnClickListener(this);
    }

    public void refresh(NoteEntity noteEntity){
        Log.d("NoteContent", "2");
        this.noteEntity = noteEntity;
        if (noteEntity.getId() != -1){
            title.setText(noteEntity.getNoteTitle());
            content.setText(noteEntity.getNoteContent());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.top_bar_left_btn){
            Toast.makeText(view.getContext(), "click button", Toast.LENGTH_SHORT).show();
            if (title.getText().toString().equals(noteEntity.getNoteTitle()) &&
                    content.getText().toString().equals(noteEntity.getNoteContent())){
                requireActivity().onBackPressed();
            } else{
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("提示");
                builder.setMessage("未保存是否直接退出？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requireActivity().onBackPressed();
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }else if(view.getId() == R.id.save_note_btn){
            if(noteEntity.getId() == -1){
                NoteInfoDBHelper.getInstance(view.getContext()).insertNoteInfo(
                        new NoteEntity(title.getText().toString(),
                                content.getText().toString(), new Date()));

            }else {
                NoteEntity note = new NoteEntity(noteEntity.getId(), title.getText().toString(),
                        content.getText().toString(), noteEntity.getEditDate());
                NoteInfoDBHelper.getInstance(view.getContext()).alterNoteInfoById(note);
            }
            Toast.makeText(view.getContext(), "click save button", Toast.LENGTH_SHORT).show();
        }
    }
}