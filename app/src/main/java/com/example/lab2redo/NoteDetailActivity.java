package com.example.lab2redo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        titleTextView = findViewById(R.id.noteDetailTitle);
        contentTextView = findViewById(R.id.noteDetailContent);

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        // Set data to views
        titleTextView.setText(title);
        contentTextView.setText(content);
    }
}
