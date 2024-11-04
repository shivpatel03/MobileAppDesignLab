package com.example.lab2redo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private OnNoteClickListener onNoteClickListener;

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public NoteAdapter(List<Note> notes, OnNoteClickListener listener) {
        this.notes = notes;
        this.onNoteClickListener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getContent());
//        holder.idTextView.setText("ID: " + note.getId());  // Display the ID

        // Set the background color of the noteColorView
        holder.noteColorView.setBackgroundColor(note.getColor());

        // Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoteClickListener.onNoteClick(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateNotes(List<Note> newNotes) {
        notes = newNotes;
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
//        TextView idTextView;  // Declare the ID TextView
        View noteColorView;   // Declare the Color View

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.noteTitle);
            contentTextView = itemView.findViewById(R.id.noteContent);
            noteColorView = itemView.findViewById(R.id.noteColorView);  // Initialize the color view
        }
    }
}
