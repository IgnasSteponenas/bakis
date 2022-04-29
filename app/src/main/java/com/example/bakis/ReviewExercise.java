package com.example.bakis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class ReviewExercise extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.example.bakis.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.bakis.EXTRA_DESCRIPTION";
    public static final String EXTRA_GIF =
            "com.example.bakis.EXTRA_GIF";

    private TextView reviewTextTitle;
    private TextView reviewTextDescription;
    private GifImageView reviewGifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_exercise);

        reviewTextTitle = findViewById(R.id.review_text_title);
        reviewTextDescription = findViewById(R.id.review_text_description);
        reviewGifImageView = findViewById(R.id.review_gif_image_view);

        reviewTextDescription.setMovementMethod(new ScrollingMovementMethod());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        setTitle("Review exercise");

        reviewTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
        reviewTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        reviewGifImageView.setImageResource(intent.getIntExtra(EXTRA_GIF, R.drawable.defaultgif));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}