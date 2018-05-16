package com.example.akshay.firebase1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Akshay on 15-03-2018.
 */

public class TrackList extends ArrayAdapter<Track> {
    private Activity context;
    private List<Track> trackList;
    private static final String TAG = "TrackList";


    public TrackList(@NonNull Activity context, List<Track> trackList) {
        super(context, R.layout.track_list_layout,trackList);
        this.context = context;
        this.trackList = trackList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.track_list_layout,null,true);
        Log.d(TAG, "getView: Layout Inflated");

        TextView tvTrackName = (TextView) listViewItem.findViewById(R.id.tvTrackName);
        TextView tvTrackRating = (TextView) listViewItem.findViewById(R.id.tvTrackRating);

        Track track = trackList.get(position);

        tvTrackName.setText(track.getTrackName());
        tvTrackRating.setText(String.valueOf(track.getTrackRating()));

        return listViewItem;
    }

}
