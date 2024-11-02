package com.example.lab2redo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText subtitleEditText;
    private EditText contentEditText;
    private Button editButton;
    private Button saveButton;
    private Button deleteButton;
    private DatabaseHelper databaseHelper;
    private String noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        titleEditText = findViewById(R.id.noteDetailTitle);
        subtitleEditText = findViewById(R.id.noteDetailSubtitle);
        contentEditText = findViewById(R.id.noteDetailContent);
        editButton = findViewById(R.id.editButton);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Get data from intent
        noteId = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("title");
        String subtitle = getIntent().getStringExtra("subtitle");
        String content = getIntent().getStringExtra("content");

        // Set data to views
        titleEditText.setText(title);
        subtitleEditText.setText(subtitle);
        contentEditText.setText(content);

        // Initially disable editing
        setEditingEnabled(false);

        // Set up button click listeners
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditingEnabled(true);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void setEditingEnabled(boolean enabled) {
        titleEditText.setEnabled(enabled);
        subtitleEditText.setEnabled(enabled);
        contentEditText.setEnabled(enabled);
        editButton.setVisibility(enabled ? View.GONE : View.VISIBLE);
        saveButton.setVisibility(enabled ? View.VISIBLE : View.GONE);
    }

    private void saveChanges() {
        String newTitle = titleEditText.getText().toString().trim();
        String newSubtitle = subtitleEditText.getText().toString().trim();
        String newContent = contentEditText.getText().toString().trim();

        if (newTitle.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdated = databaseHelper.updateNote(noteId, newTitle, newSubtitle, newContent);

        if (isUpdated) {
            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
            setEditingEnabled(false);
            finish(); // Return to MainActivity
        } else {
            Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteNote() {
        Integer result = databaseHelper.deleteData(noteId);
        if (result > 0) {
            Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show();
        }
    }
}