package com.example.lab2redo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import yuku.ambilwarna.*;

public class NewNote extends AppCompatActivity {
    private com.example.lab2redo.DatabaseHelper myDB;
    private Button doneButton;
    private Button backButton;
    private EditText title;
    private EditText subtitle;
    private EditText content;
    private View colorDisplay;
    private Button buttonChooseColor;
    private int selectedColor = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        doneButton = findViewById(R.id.doneButton);
        backButton = findViewById(R.id.backButton);
        title = findViewById(R.id.noteTitle);
        subtitle = findViewById(R.id.noteSubtitle);
        content = findViewById(R.id.noteContent);

        colorDisplay = findViewById(R.id.colorDisplay);
        buttonChooseColor = findViewById(R.id.buttonChooseColor);

        myDB = new com.example.lab2redo.DatabaseHelper(NewNote.this);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                boolean isInserted = myDB.insertData(title.getText().toString(), subtitle.getText().toString(), content.getText().toString(), selectedColor);

                if (isInserted) {
                    Toast.makeText(NewNote.this, "Data Inserted...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(NewNote.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(NewNote.this, com.example.lab2redo.MainActivity.class);
                startActivity(intent);
            }
        });


        // Go back to Home screen on clicking Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewNote.this, com.example.lab2redo.MainActivity.class);
                startActivity(intent);
                finish(); // Optionally close NewNote activity
            }
        });
    }

    public void openColorPicker(View view) {
        AmbilWarnaDialog colorPickerDialog = new AmbilWarnaDialog(this, selectedColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                selectedColor = color;
                colorDisplay.setBackgroundColor(selectedColor);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }
        });

        colorPickerDialog.show();
    }

}
