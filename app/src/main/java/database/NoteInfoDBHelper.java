package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import entity.NoteEntity;

public class NoteInfoDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "note.db";
    private static final String TABLE_NAME = "note_table";
    private static final int DB_VERSION = 1;
    private static NoteInfoDBHelper noteInfoDBHelper = null;
    private SQLiteDatabase mWDB;
    private SQLiteDatabase mRDB;

    private NoteInfoDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public static NoteInfoDBHelper getInstance(@Nullable Context context){
        if(noteInfoDBHelper == null){
            noteInfoDBHelper = new NoteInfoDBHelper(context);
        }
        return noteInfoDBHelper;
    }

    public SQLiteDatabase openReadLink(){
        if(mRDB == null || !mRDB.isOpen()){
            mRDB = noteInfoDBHelper.getReadableDatabase();
        }
        return mRDB;
    }
    public SQLiteDatabase openWriteLink(){
        if(mWDB == null || !mWDB.isOpen()){
            mWDB = noteInfoDBHelper.getWritableDatabase();
        }
        return mWDB;
    }
    public void closeLink(){
        if(mWDB!=null && mWDB.isOpen())
            mWDB.close();
        if(mRDB!=null && mRDB.isOpen())
            mRDB.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "title VARVHAR,"+
                "content VARCHAR NOT NULL,"+
                "edit_date VARCHAR NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<NoteEntity> getALLNoteInfo() {
        List<NoteEntity> noteEntityList = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+";";
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setId(cursor.getInt(0));
            noteEntity.setNoteTitle(cursor.getString(1));
            noteEntity.setNoteContent(cursor.getString(2));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date date = new Date();
            try {
                date = format.parse(cursor.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            noteEntity.setEditDate(date);
            noteEntityList.add(noteEntity);
        }
        cursor.close();
        return noteEntityList;
    }

    public List<NoteEntity> queryNoteInfoById(int id) {
        List<NoteEntity> noteEntityList = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE _id == "+id;
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setId(cursor.getInt(0));
            noteEntity.setNoteTitle(cursor.getString(1));
            noteEntity.setNoteContent(cursor.getString(2));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date date = new Date();
            try {
                date = format.parse(cursor.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            noteEntity.setEditDate(date);
            noteEntityList.add(noteEntity);
        }
        cursor.close();
        return noteEntityList;
    }

    public void insertNoteInfo(NoteEntity noteEntity){
        try {
            mWDB.beginTransaction();
            ContentValues contentValues = new ContentValues();
            //contentValues.put("_id", noteEntity.getId());
            contentValues.put("title", noteEntity.getNoteTitle());
            contentValues.put("content", noteEntity.getNoteContent());
            Date date = noteEntity.getEditDate();
            String editDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
            contentValues.put("edit_date", editDate);
            mWDB.insert(TABLE_NAME, null, contentValues);
            mWDB.setTransactionSuccessful();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }
    public void insertNoteInfo(List<NoteEntity> noteEntityList){
        try {
            mWDB.beginTransaction();
            for (NoteEntity noteEntity : noteEntityList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", noteEntity.getNoteTitle());
                contentValues.put("content", noteEntity.getNoteContent());
                Date date = noteEntity.getEditDate();
                String editDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
                contentValues.put("edit_date", editDate);
                mWDB.insert(TABLE_NAME, null, contentValues);
            }
            mWDB.setTransactionSuccessful();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }

    public void initNoteInfo(){
        List<NoteEntity> noteEntityList = new ArrayList<>();
        NoteEntity insertPic = new NoteEntity();
        insertPic.setNoteTitle("????????????");
        insertPic.setNoteContent("????????????????????????????????????????????????????????????????????????????????????" +
                "????????????????????????????????????????????????????????????????????????" +
                "????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        insertPic.setEditDate(new Date());
        NoteEntity setList = new NoteEntity();
        setList.setNoteTitle("????????????");
        setList.setNoteContent("????????????????????????????????????????????????????????????" +
                "????????????????????????????????????????????????????????????????????????????????????????????????" +
                "???????????????????????????????????????????????????");
        setList.setEditDate(new Date());
        NoteEntity setTodo = new NoteEntity();
        setTodo.setNoteTitle("????????????");
        setTodo.setNoteContent("???????????????????????????????????????" +
                "???????????????????????????????????????????????????????????????????????????????????????????????????");
        setTodo.setEditDate(new Date());
        noteEntityList.add(insertPic);
        noteEntityList.add(setList);
        noteEntityList.add(setTodo);
        insertNoteInfo(noteEntityList);
    }

    public void alterNoteInfoById(NoteEntity note) {

    }
}
