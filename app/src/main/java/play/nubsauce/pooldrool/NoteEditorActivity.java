package play.nubsauce.pooldrool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nubsauce.droolpool.R;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;
    Button add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        getSupportActionBar().setTitle("Workout Entry");



        EditText editText = (EditText) findViewById(R.id.editText);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if(noteId != -1) {

            editText.setText(SecondFragment.notes.get(noteId));
        } else{

            SecondFragment.notes.add("");
            noteId = SecondFragment.notes.size() -1;
            SecondFragment.arrayAdapter.notifyDataSetChanged();
        }



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                SecondFragment.notes.set(noteId, String.valueOf(charSequence));
                SecondFragment.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("play.nubsauce.pooldrool", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet(SecondFragment.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }






        });
    }
}