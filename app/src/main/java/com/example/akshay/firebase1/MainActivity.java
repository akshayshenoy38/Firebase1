package com.example.akshay.firebase1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText etArtistName;
    Spinner spGenres;
    Button btnAddData,btnShowArtist;
    DatabaseReference databaseArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        etArtistName = (EditText) findViewById(R.id.etArtistName);
        spGenres = (Spinner) findViewById(R.id.spGenres);
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnShowArtist = (Button) findViewById(R.id.btnShowArtist);

    }

    public void addData(View view) {
        String artistName = etArtistName.getText().toString().trim();
        String genre = spGenres.getSelectedItem().toString();

        if (!TextUtils.isEmpty(artistName)){
            String id = databaseArtists.push().getKey();

            Artist artist = new Artist(id,artistName,genre);
            databaseArtists.child(id).setValue(artist);

            toastMessage("Artist Added");
        } else {
            toastMessage("Please Enter Artist Name");
        }
    }



    public void showArtists(View view) {
        startActivity(new Intent(this,ArtistsListActivity.class));
    }

    private void toastMessage(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }






}
