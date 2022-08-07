package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zw.note.NoteContentFragment;
import com.zw.note.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.NoteEntity;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder>{

    List<NoteEntity> noteEntityList = new ArrayList<>();
    View view;
    Boolean isTwoPage;
    public NoteListAdapter(List<NoteEntity> noteEntityList) {
        this.noteEntityList = noteEntityList;
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
        ViewHolder holder = new ViewHolder(view);
        holder.mView.setOnClickListener(view1 -> {
            //此处编写点击item的监听方法

        });
        holder.mView.setOnLongClickListener(view1 -> {
            //此处编写长按item的监听方法
            return true;
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder holder, int position) {
        NoteEntity noteEntity = noteEntityList.get(position);
        holder.noteTitle.setText(noteEntity.getNoteTitle());
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
    }

    @Override
    public int getItemCount() {
        return noteEntityList.size();
    }
}
