package com.example.bakis;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {
    private List<Exercise> exercises = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);

        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        Exercise currentExercise = exercises.get(position);

        if(Locale.getDefault().getLanguage().equals("lt") && currentExercise.getTitleInLithuanian() != null){
            holder.textViewTitle.setText(currentExercise.getTitleInLithuanian());
            holder.textViewDescription.setText(currentExercise.getDescriptionInLithuanian());
        }else {
            holder.textViewTitle.setText(currentExercise.getTitleInEnglish());
            holder.textViewDescription.setText(currentExercise.getDescriptionInEnglish());
        }

        if(currentExercise.getGif()==0 && currentExercise.getUri()!=null)//default gif
            holder.gifImageView.setImageURI(Uri.parse(currentExercise.getUri()));
        else if (currentExercise.getGif()!=0 && currentExercise.getUri()==null)
            holder.gifImageView.setImageResource(currentExercise.getGif());
        else
            holder.gifImageView.setImageResource(R.drawable.defaultgif);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public Exercise getExerciseAt(int position) {
        return exercises.get(position);
    }

    class ExerciseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private GifImageView gifImageView;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            gifImageView = itemView.findViewById(R.id.gif_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(exercises.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
