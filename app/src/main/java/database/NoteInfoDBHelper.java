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
        mWDB.close();
        mRDB.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "title VARVHAR,"+
                "content VARCHAR NOT NULL,"+
                "edit_data VARCHAR NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<NoteEntity> getALLNoteInfo() throws ParseException {
        List<NoteEntity> noteEntityList = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+";";
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setId(cursor.getInt(0));
            noteEntity.setNoteTitle(cursor.getString(1));
            noteEntity.setNoteContent(cursor.getString(2));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date date = format.parse(cursor.getString(3));
            noteEntity.setEditDate(date);
            noteEntityList.add(noteEntity);
        }
        cursor.close();
        return noteEntityList;
    }

    public List<NoteEntity> queryNoteInfoById(int id) throws ParseException {
        List<NoteEntity> noteEntityList = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE _id == "+id;
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setId(cursor.getInt(0));
            noteEntity.setNoteTitle(cursor.getString(1));
            noteEntity.setNoteContent(cursor.getString(2));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date date = format.parse(cursor.getString(3));
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
            contentValues.put("_id", noteEntity.getId());
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
        insertPic.setNoteTitle("插入图片");
        insertPic.setNoteContent("在便签详情页，点击底部工具条上的图片按钮，即可插入图片。" +
                "您可以直接拍照，或者从本地图库选择一张图片插入。" +
                "便签含有图片时，图片会以缩略图的样式显示在便签列表首页，方便您随时查找。");
        insertPic.setEditDate(new Date());
        NoteEntity setList = new NoteEntity();
        setList.setNoteTitle("设置清单");
        setList.setNoteContent("您可以使用清单功能来记录和管理待办事项。" +
                "在便签详情页，点击底部工具条上的清单按钮，可生成一条待办清单项。" +
                "点击小圈，可将该条事项标记为完成。");
        setList.setEditDate(new Date());
        NoteEntity setTodo = new NoteEntity();
        setTodo.setNoteTitle("设置提醒");
        setTodo.setNoteContent("您可以为便签设置时间提醒。" +
                "在便签详情页，点击右上角的“更多”，开启提醒功能，设定好时间即可。");
        setTodo.setEditDate(new Date());
        noteEntityList.add(insertPic);
        noteEntityList.add(setList);
        noteEntityList.add(setTodo);
        insertNoteInfo(noteEntityList);
    }
}
