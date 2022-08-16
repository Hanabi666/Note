package adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zw.note.NoteContentActivity;
import com.zw.note.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.NoteEntity;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder>{

    List<NoteEntity> noteEntityList;
    View view;
    Boolean isTwoPage;
    public NoteListAdapter(List<NoteEntity> noteEntityList, Boolean isTwoPage) {
        this.noteEntityList = noteEntityList;
        this.isTwoPage = isTwoPage;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle;
        TextView noteContent;
        TextView editTime;
        View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.noteTitle = itemView.findViewById(R.id.note_item_layout_title);
            this.noteContent = itemView.findViewById(R.id.note_item_layout_content);
            this.editTime = itemView.findViewById(R.id.note_item_layout_edit_time);
            this.mView = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder holder, int position) {
        NoteEntity noteEntity = noteEntityList.get(position);
        if (noteEntity.getNoteTitle() == null){
            holder.noteTitle.setVisibility(View.GONE);
            holder.noteContent.setMaxLines(5);
        } else{
            holder.noteTitle.setText(noteEntity.getNoteTitle());
        }
        holder.noteContent.setText(noteEntity.getNoteContent());
        Date editDate = noteEntity.getEditDate();
        final Date currentDate = new Date();
        if (editDate.getYear() != currentDate.getYear()){
            //显示年月日
            String editTime = String.format("%d年%d月%d日", editDate.getYear(), editDate.getMonth(), editDate.getDay());
            holder.editTime.setText(editTime);
        } else if (editDate.getMonth() != currentDate.getMonth() || editDate.getDay() != currentDate.getDay()){
            //显示月日
            String editTime = String.format("%d月%d日", editDate.getMonth(), editDate.getDay());
            holder.editTime.setText(editTime);
        } else {
            //显示小时
            String editTime = String.format("%d:%d", editDate.getHours(), editDate.getMinutes());
            holder.editTime.setText(editTime);
        }
        holder.mView.setOnClickListener(view1 -> {
            //此处编写点击item的监听方法
            if (isTwoPage)
            {
//                Intent intent = new Intent("com.zw.note"+".FRESH_NOTE_CONTENT_FRAGMENT");
//                intent.putExtra("noteTitle", noteEntity.getNoteTitle());
//                intent.putExtra("noteContent", noteEntity.getNoteContent());
//                view.getContext().sendBroadcast(intent);
                Toast.makeText(view.getContext(),"is two page", Toast.LENGTH_SHORT).show();
            } else{
                NoteContentActivity.actionStart(view.getContext(), noteEntity);
//                Toast.makeText(view.getContext(),"is not two page", Toast.LENGTH_SHORT).show();
            }
        });
        holder.mView.setOnLongClickListener(view1 -> {
            //此处编写长按item的监听方法
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return noteEntityList.size();
    }
}
