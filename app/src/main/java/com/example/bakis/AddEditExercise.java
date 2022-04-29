package com.example.bakis;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class AddEditExercise extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.bakis.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.bakis.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.bakis.EXTRA_DESCRIPTION";
    public static final String EXTRA_GIF =
            "com.example.bakis.EXTRA_GIF";
    int SELECT_IMAGE_CODE = 4;
    int PICK_PHOTO_FOR_AVATAR = 5;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private GifImageView editGifImageView;
    private Uri newUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editGifImageView = findViewById(R.id.edit_gif_image_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        editGifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Test"), SELECT_IMAGE_CODE);
            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit exercise");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            //TODO no permission to take image need to fix
            editGifImageView.setImageURI(Uri.parse(intent.getStringExtra(EXTRA_GIF)));
            newUri = Uri.parse(intent.getStringExtra(EXTRA_GIF));
            //tempResource = intent.getIntExtra(EXTRA_GIF, R.drawable.defaultgif);
        } else {
            setTitle("Add exercise");
            editGifImageView.setImageResource(R.drawable.defaultgif);
        }
    }

    /*private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_IMAGE_CODE){
            newUri = data.getData();
            editGifImageView.setImageURI(newUri);
        }/*else if(requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK){

        }*/
    }

    /*public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent.setData(newUri), PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }*/



    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    private void saveExercise() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        if(newUri.toString().isEmpty())
            data.putExtra(EXTRA_GIF, getUriToDrawable(this, R.drawable.defaultgif).toString());
        else
            data.putExtra(EXTRA_GIF, newUri.toString());

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_exercise_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_exercise:
                saveExercise();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}