package com.example.sqlite_demo_2_provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String AUTHORITY = "com.example.sqlite_demo_2";
    static final String CONTENT_PATH = "bookdata";
    static final String URL = "content://" + AUTHORITY + "/" + CONTENT_PATH;
    static final Uri CONTENT_URI = Uri.parse(URL);

    EditText editText_maso, editText_tieude, editText_masotacgia;
    Button button_select, button_save, button_update, button_delete, button_exit;
    GridView gridView_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        eventClickSaveProvider();
        eventClickSelectProvider();
    }

    private void eventClickSaveProvider() {
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id_book", editText_maso.getText().toString());
                values.put("title", editText_tieude.getText().toString());
                values.put("id_author", editText_masotacgia.getText().toString());
                Uri insert_uri = getContentResolver().insert(CONTENT_URI, values);
                Toast.makeText(getApplicationContext(), "Lưu thành công !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eventClickSelectProvider() {
        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list_string = new ArrayList<>();
                String uri = "content://com.example.sqlite_demo_2"; //truy cap vao app 1
                Uri book = Uri.parse(uri);
                Cursor cursor = getContentResolver().query(book, null, null, null, "title");
                if (cursor != null) {
                    cursor.moveToFirst();
                    do {
                        list_string.add(cursor.getInt(0) + "");
                        list_string.add(cursor.getString(1) + "");
                        list_string.add(cursor.getInt(2) + "");
                    } while (cursor.moveToNext());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_1, list_string);
                    gridView_display.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Không có kết quả !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        editText_maso = (EditText) findViewById(R.id.editText_maso);
        editText_tieude = (EditText) findViewById(R.id.editText_tieude);
        editText_masotacgia = (EditText) findViewById(R.id.editText_masotacgia);

        button_select = (Button) findViewById(R.id.button_select);
        button_save = (Button) findViewById(R.id.button_save);
        button_update = (Button) findViewById(R.id.button_update);
        button_delete = (Button) findViewById(R.id.button_delete);
        button_exit = (Button) findViewById(R.id.button_exit);

        gridView_display = (GridView) findViewById(R.id.gridView_display);
    }
}
