package com.zw.note;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import adapter.NoteListAdapter;
import database.NoteInfoDBHelper;
import entity.NoteEntity;
import preference.MySharedPreference;

public class NoteListFragment extends Fragment {

    private View view;
    private List<NoteEntity> noteEntityList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_note_list, container, false);
        initView();
        initRecyclerView();
        return view;
    }

    private void initView() {
        int maxWidthDP = (int) (getResources().getDisplayMetrics().widthPixels
                /getResources().getDisplayMetrics().density + 0.5f);
        Log.d("maxWidthDP", String.valueOf(maxWidthDP));
        if(maxWidthDP >= 600){
            MySharedPreference.getInstance(view.getContext()).save("isTwoPage", true);
        }else{
            MySharedPreference.getInstance(view.getContext()).save("isTwoPage", false);
        }
        ImageButton topBarLeftBtn = view.findViewById(R.id.top_bar_left_btn);
        TextView topBarTitle = view.findViewById(R.id.top_bar_title);
        Button topBarSaveBtn = view.findViewById(R.id.save_note_btn);
        topBarLeftBtn.setVisibility(View.GONE);
        topBarTitle.setText("便签");
        topBarSaveBtn.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        noteEntityList = NoteInfoDBHelper.getInstance(view.getContext()).getALLNoteInfo();
        RecyclerView recyclerView = view.findViewById(R.id.note_list_recyclerview);
        NoteListAdapter noteListAdapter = new NoteListAdapter(noteEntityList,
                MySharedPreference.getInstance(view.getContext()).getBoolean("isTwoPage", false));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(noteListAdapter);
    }
}