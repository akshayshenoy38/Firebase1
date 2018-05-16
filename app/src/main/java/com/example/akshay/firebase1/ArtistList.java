package com.example.akshay.firebase1;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Akshay on 11-03-2018.
 */

public class ArtistList extends ArrayAdapter<Artist> {
    private Activity context;
    private List<Artist> artistList;


    public ArtistList(@NonNull Activity context, List<Artist> artistList) {
        super(context, R.layout.list_layout,artistList);
        this.context = context;
        this.artistList = artistList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView tvArtistName = (TextView) listViewItem.findViewById(R.id.tvArtistName);
        TextView tvArtistGenre = (TextView) listViewItem.findViewById(R.id.tvArtistGenre);

        Artist artist = artistList.get(position);

        tvArtistName.setText(artist.getArtistName());
        tvArtistGenre.setText(artist.getArtistGenre());

        return listViewItem;
    }
}
