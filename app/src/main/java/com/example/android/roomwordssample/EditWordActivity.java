package com.example.android.roomwordssample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditWordActivity extends AppCompatActivity {
    public static final String NOTE_ID = "note_id";
    static final String UPDATED_NOTE = "note_text";
    static final byte[] IMG_IMAGE = new byte[0];

    private EditText etNote;
    private Bundle bundle;
    private String noteId;
    private LiveData<Word> note;
    EditWordViewModel noteModel;
    ImageView img;


    private static int SELECT_PICTURE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word2);
        etNote=findViewById(R.id.edit_word1);
        img=findViewById(R.id.imageView);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            noteId = bundle.getString("note_id");
        }
        noteModel= ViewModelProviders.of(this).get(EditWordViewModel.class);
        note=noteModel.getWord(noteId);
        note.observe(this, new Observer<Word>() {
            @Override
            public void onChanged(@Nullable Word word) {
                etNote.setText(word.getWord());
            }
        });
    }
    public void updateWord(View view) {
        String updatedNote = etNote.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NOTE_ID, noteId);
        resultIntent.putExtra(UPDATED_NOTE, updatedNote);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void cancelUpdate(View view) {
        finish();
    }

    public void takePicture(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        int code = SELECT_PICTURE;
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,SELECT_PICTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE){
            Uri selectedImage = data.getData();
            InputStream is;
            try {
                is = getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bis = new BufferedInputStream(is);
                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                ImageView iv = (ImageView)findViewById(R.id.imageView);
                iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {}
        }
    }
}