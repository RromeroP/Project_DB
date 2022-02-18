package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.loader.content.CursorLoader;

import com.example.projectdb.MainActivity;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CommentDB.db";

    //COMMENT Table keys
    private final String COMMENT_TABLE = "COMMENT";
    private final String COMMENT_ID = "_id";
    private final String COMMENT_NAME = "name";
    private final String COMMENT_TEXT = "text";

    String COMMENT_CREATE = "CREATE TABLE IF NOT EXISTS " + COMMENT_TABLE + "(" +
            COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COMMENT_NAME + " TEXT," +
            COMMENT_TEXT + " TEXT)";

    Cursor cur;
    SQLiteDatabase database;

    String selectedId;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + COMMENT_TABLE);
        onCreate(db);
    }

    public void insertData(String newName, String newText) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COMMENT_NAME, newName);
        values.put(COMMENT_TEXT, newText);

        db.insert(COMMENT_TABLE, null, values);
        db.close();
    }

    public void spinner(Context context, Spinner spinner) {
        cur = database.rawQuery("SELECT * FROM " + COMMENT_TABLE, null);

        String[] from = new String[]{COMMENT_NAME};
        int[] to = new int[]{android.R.id.text1};

        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(context,
                android.R.layout.simple_spinner_item, cur, from, to, 0);

        spinner.setAdapter(mAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedId = String.valueOf(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void deleteData() {
        database.delete(COMMENT_TABLE, COMMENT_ID + "=" + selectedId, null);
    }

    public String getData() {

        Cursor cursor = database.rawQuery("SELECT " + COMMENT_TEXT + " FROM " +
                COMMENT_TABLE + " WHERE " + COMMENT_ID + "=" + selectedId, null);

        cursor.moveToFirst();

        String data = cursor.getString(0);

        cursor.close();

        return data;
    }
}
