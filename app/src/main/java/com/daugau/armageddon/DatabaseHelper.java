package com.daugau.armageddon;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.daugau.armageddon.model.Chapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Khiem on 3/23/2017.
 */

public class DatabaseHelper {

    private static final String DATABASE_NAME = "pokemon_armageddon.db";

    public DatabaseHelper(Context context) {
        this.context = context;
    }

    private Context context;

    public void copyDatabase() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        try {
            if (!dbFile.exists()) {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            }

            InputStream is = context.getAssets().open(DATABASE_NAME);
            OutputStream os = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }

            os.flush();
            os.close();
            is.close();
        } catch (IOException ex) {
        }
    }

    public ArrayList<Chapter> getListTitle() {
        ArrayList<Chapter> chapters = new ArrayList<>();

        File dbFile = context.getDatabasePath(DATABASE_NAME);
        SQLiteDatabase database = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);

        String sql = "SELECT id, title FROM chapters";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            Chapter chapter = new Chapter(id, title, "");
            chapters.add(chapter);
        }
        cursor.close();

        return chapters;
    }

    public Chapter getChapter(int chapterId) {
        Chapter chapter = new Chapter();

        File dbFile = context.getDatabasePath(DATABASE_NAME);
        SQLiteDatabase database = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);

        String sql = "SELECT id, title, content FROM chapters WHERE id=?";
        String[] args = new String[] {String.valueOf(chapterId)};
        Cursor cursor = database.rawQuery(sql, args);

        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);

            chapter.setId(id);
            chapter.setTitle(title);
            chapter.setContent(content);
        }
        cursor.close();

        return chapter;
    }

    public int getMaxId() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        SQLiteDatabase database = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.CREATE_IF_NECESSARY);

        String sql = "SELECT MAX(id) FROM chapters";
        Cursor cursor = database.rawQuery(sql, null);

        int max = 0;

        if (cursor.moveToNext()) {
            max = cursor.getInt(0);
        }
        cursor.close();

        return max;
    }
}
