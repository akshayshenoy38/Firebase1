package com.example.akshay.firebase1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArtistsListActivity extends AppCompatActivity {
    DatabaseReference databaseArtists;
    ListView lvArtists;
    List<Artist> artistList;
    Button btnUpdate;
    public static final String ARTIST_NAME = "artistname";
    public static final String ARTIST_ID = "artistid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_list);

        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");
        lvArtists = (ListView) findViewById(R.id.lvArtists);



        artistList = new ArrayList<>();

        lvArtists.setLongClickable(true);

        lvArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = artistList.get(i);
                Intent intent = new Intent(getApplicationContext(),AddTrackActivity.class);

                intent.putExtra(ARTIST_ID,artist.getArtistId());
                intent.putExtra(ARTIST_NAME,artist.getArtistName());

                startActivity(intent);
            }
        });

        lvArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = artistList.get(i);
                showUpdateDialog(artist.getArtistId(),artist.getArtistName());
                return true;
            }
        });

    }





    private void showUpdateDialog(final String artistId, final String artistName){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);


        final EditText etName = (EditText) dialogView.findViewById(R.id.etName);
        final Button btnUpdate = (Button) dialogView.findViewById(R.id.btnUpdate);
        final Spinner spUpdateGenre = (Spinner) dialogView.findViewById(R.id.spUpdateGenre);
        final Button btnDelete = (Button) dialogView.findViewById(R.id.btnDelete);

        dialogBuilder.setTitle("Updating Artist "+artistName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String genre = spUpdateGenre.getSelectedItem().toString();

                if (TextUtils.isEmpty(name)){
                    etName.setError("Name Required");
                    return;
                }

                updateArtist(artistId,name,genre);
                alertDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteArtist(artistId);
                alertDialog.dismiss();
            }
        });



    }

    private void deleteArtist(String artistId) {
        DatabaseReference drArtist = FirebaseDatabase.getInstance().getReference("artists").child(artistId);
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("Tracks").child(artistId);

        drArtist.removeValue();
        drTracks.removeValue();

        toastMessage("Artist deleted");


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Artist artist = artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);
                }
                ArtistList adapter = new ArtistList(ArtistsListActivity.this,artistList);
                lvArtists.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public boolean updateArtist(String id,String name,String genre){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artists").child(id);
        Artist artist = new Artist(id,name,genre);
        databaseReference.setValue(artist);

        toastMessage("Artist Updated Successfully");
        return true;

    }

    public void toastMessage(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
