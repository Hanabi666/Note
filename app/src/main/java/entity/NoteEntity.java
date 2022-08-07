package entity;

public class NoteEntity {
    private String noteTitle;
    private String noteContent;

    public String getNoteTitle(){
        return noteTitle;
    }
    public String getNoteContent(){
        return noteContent;
    }
    public void setNoteTitle(String title){
        this.noteTitle = title;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
