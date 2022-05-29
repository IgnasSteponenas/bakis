package com.example.bakis;

import android.content.Context;
import android.net.Uri;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class AddCourseAdapter extends RecyclerView.Adapter<AddCourseAdapter.AddCourseHolder> {
    private List<Exercise> exercises = new ArrayList<>();
    private List<Exercise> selected = new ArrayList<>();
    private List<Integer> selectedRepeats = new ArrayList<>();
    private List<Integer> selectedTimePerRepeat = new ArrayList<>();
    Context context;
    private ExerciseAdapter.OnItemClickListener listener;
    private int temp = 0;

    @NonNull
    @Override
    public AddCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item_to_add, parent, false);

        return new AddCourseHolder(itemView);
    }

    public AddCourseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull AddCourseHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);

        if (Locale.getDefault().getLanguage().equals("lt") && currentExercise.getTitleInLithuanian() != null) {
            holder.textViewTitle.setText(currentExercise.getTitleInLithuanian());
            holder.textViewDescription.setText(currentExercise.getDescriptionInLithuanian());
        } else {
            holder.textViewTitle.setText(currentExercise.getTitleInEnglish());
            holder.textViewDescription.setText(currentExercise.getDescriptionInEnglish());
        }
        holder.textViewTitle.setTag(holder);
        holder.textViewDescription.setTag(holder);

        if (currentExercise.getGif() == 0) {//default gif
            holder.gifImageView.setImageResource(R.drawable.defaultgif);
            holder.gifImageView.setTag(holder);
        } else {
            holder.gifImageView.setImageResource(currentExercise.getGif());
            holder.gifImageView.setTag(holder);
        }

        if (currentExercise.getGif() == 0 && currentExercise.getUri() != null) {
            holder.gifImageView.setImageURI(Uri.parse(currentExercise.getUri()));
            holder.gifImageView.setTag(holder);
        } else if (currentExercise.getGif() != 0 && currentExercise.getUri() == null) {
            holder.gifImageView.setImageResource(currentExercise.getGif());
            holder.gifImageView.setTag(holder);
        } else {
            holder.gifImageView.setImageResource(R.drawable.defaultgif);
            holder.gifImageView.setTag(holder);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (containsId(selected, currentExercise) != null) {
                    int index = selected.indexOf(containsId(selected, currentExercise));
                    selectedRepeats.remove(index);
                    selectedTimePerRepeat.remove(index);
                    selected.remove(index);
                    unhighlightView(holder);
                } else if (containsId(selected, currentExercise) == null
                        && Integer.parseInt(holder.repeats.getText().toString()) > 0
                        && Integer.parseInt(holder.timePerRepeat.getText().toString()) > 0) {
                    int index = selected.indexOf(containsId(selected, currentExercise));
                    selected.add(currentExercise);
                    selectedRepeats.add(Integer.parseInt(holder.repeats.getText().toString()));
                    selectedTimePerRepeat.add(Integer.parseInt(holder.timePerRepeat.getText().toString()));
                    highlightView(holder);
                } else {
                    Toast.makeText(context, R.string.choose_repeat_time_per_repeat, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (containsId(selected, currentExercise) != null) {
            highlightView(holder);
            if (holder.repeats.getText().toString().equals("0")) {
                int index = selected.indexOf(containsId(selected, currentExercise));
                holder.repeats.setText(selectedRepeats.get(index).toString());
                holder.timePerRepeat.setText(selectedTimePerRepeat.get(index).toString());
            }
        } else {
            unhighlightView(holder);
        }
    }

    public Exercise containsId(List<Exercise> exercises, Exercise exercise) {
        for (int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).getExerciseId() == exercise.getExerciseId())
                return exercises.get(i);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    private void highlightView(AddCourseHolder holder) {
        holder.itemView.findViewById(R.id.card).setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200)); //hardcore color values
    }

    private void unhighlightView(AddCourseHolder holder) {
        holder.itemView.findViewById(R.id.card).setBackgroundColor(ContextCompat.getColor(context, R.color.white));//hardcore color values
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public void setSelected(List<Exercise> selected) {
        this.selected = selected;
    }

    public void setSelectedRepeats(List<Integer> selectedRepeats) {
        this.selectedRepeats = selectedRepeats;
    }

    public void setSelectedTimePerRepeat(List<Integer> selectedTimePerRepeat) {
        this.selectedTimePerRepeat = selectedTimePerRepeat;
    }

    public Exercise getExerciseAt(int position) {
        return exercises.get(position);
    }

    public List<Exercise> getSelected() {
        return selected;
    }

    public List<Integer> getSelectedRepeats() {
        return selectedRepeats;
    }

    public List<Integer> getSelectedTimePerRepeat() {
        return selectedTimePerRepeat;
    }

    class AddCourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private GifImageView gifImageView;
        private EditText repeats;
        private EditText timePerRepeat;

        public AddCourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            gifImageView = itemView.findViewById(R.id.gif_image_view);
            repeats = itemView.findViewById(R.id.repeats_number);
            timePerRepeat = itemView.findViewById(R.id.time_per_repeat_number);
        }
    }


}

