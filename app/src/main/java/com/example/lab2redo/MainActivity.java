package com.example.lab2redo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private SearchView searchView;
    private FloatingActionButton addNoteButton;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        addNoteButton = findViewById(R.id.addNoteButton);

        databaseHelper = new DatabaseHelper(this);

        List<Note> notes = getAllNotes();
        noteAdapter = new NoteAdapter(notes, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                // Open NoteDetailActivity when a note is clicked
                Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                intent.putExtra("title", note.getTitle());
                intent.putExtra("content", note.getContent());
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);

        // Handle search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = databaseHelper.searchNotes(newText);
                List<Note> filteredNotes = getFilteredNotes(cursor);
                noteAdapter.updateNotes(filteredNotes);
                return false;
            }
        });

        // Open New Note screen on clicking the Add button
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewNote.class);
                startActivity(intent);
            }
        });
    }

    private List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllEntries();
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("TITLE"));
                String content = cursor.getString(cursor.getColumnIndex("CONTENT"));
                notes.add(new Note(title, content));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    private List<Note> getFilteredNotes(Cursor cursor) {
        List<Note> filteredNotes = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int titleIndex = cursor.getColumnIndex("TITLE");
                int contentIndex = cursor.getColumnIndex("CONTENT");

                if (titleIndex != -1 && contentIndex != -1) {
                    String title = cursor.getString(titleIndex);
                    String content = cursor.getString(contentIndex);

                    if (title != null && content != null) {
                        filteredNotes.add(new Note(title, content));
                    }
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close(); // Always close the cursor when done
        }
        return filteredNotes;
    }
}