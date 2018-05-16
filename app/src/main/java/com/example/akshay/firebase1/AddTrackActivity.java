package com.example.akshay.firebase1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {
    TextView tvArtistName;
    EditText etTrackName;
    SeekBar sbRating;
    Button btnAddTrack;

    List<Track> trackList;

    ListView lvTracks;

    DatabaseReference databaseTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        tvArtistName = (TextView) findViewById(R.id.tvArtistName);
        etTrackName = (EditText) findViewById(R.id.etTrackName);
        sbRating = (SeekBar) findViewById(R.id.sbRating);

        lvTracks = (ListView) findViewById(R.id.lvTracks);
        btnAddTrack = (Button) findViewById(R.id.btnAddTrack);

        trackList = new ArrayList<>();

        Intent intent = getIntent();
        String id = intent.getStringExtra(ArtistsListActivity.ARTIST_ID);
        String name = intent.getStringExtra(ArtistsListActivity.ARTIST_NAME);

        tvArtistName.setText(name);

        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);
    }

    public void addTrack(View view) {
        String trackName = etTrackName.getText().toString().trim();
        int rating = sbRating.getProgress();

        if (!TextUtils.isEmpty(trackName)){
            String id = databaseTracks.push().getKey();

            Track track = new Track(id,trackName,rating);
            databaseTracks.child(id).setValue(track);

            toastMessage("Track saved successfully");

        } else {
            toastMessage("Track Name Should not be empty");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                trackList.clear();
                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren()){
                    Track track = trackSnapshot.getValue(Track.class);
                    trackList.add(track);
                    Log.d("OnStart", "onDataChange: ");
                }
                TrackList trackListAdapter = new TrackList(AddTrackActivity.this,trackList);
                lvTracks.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void toastMessage(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
