package entity;

import java.util.Date;

public class NoteEntity {
    private int id;
    private String noteTitle;
    private String noteContent;
    private Date editDate;

    public int getId() {
        return id;
    }
    public String getNoteTitle(){
        return noteTitle;
    }
    public String getNoteContent(){
        return noteContent;
    }
    public Date getEditDate(){
        return editDate;
    }
    public void setNoteTitle(String title){
        this.noteTitle = title;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
    public void setEditDate(Date date){
        this.editDate = date;
    }
}
