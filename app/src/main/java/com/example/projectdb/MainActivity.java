package com.example.projectdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    private EditText Name, Text;
    private Spinner getName;
    private TextView getText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.comment_name);
        Text = findViewById(R.id.comment_text);
        getName = findViewById(R.id.get_name);
        getText = findViewById(R.id.get_text);

        getSupportActionBar().hide();
        dbHelper = new DatabaseHelper(this);
        dbHelper.spinner(this, getName);
    }

    public void addComment(View view) {
        String newName = Name.getText().toString();
        String newText = Text.getText().toString();

        if (newName.isEmpty() || newText.isEmpty()) {
            Toast.makeText(this,
                    "No pueden estar vacios.", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.insertData(newName, newText);

            Toast.makeText(this,
                    "Comentario agregado.", Toast.LENGTH_SHORT).show();

            Name.setText("");
            Text.setText("");
        }
    }

    public void deleteComment(View view){
        dbHelper.deleteData();

        Toast.makeText(this,
                "Comentario eliminado.", Toast.LENGTH_SHORT).show();
    }

    public void showComment(View view){
        getText.setText(dbHelper.getData());
    }
}

