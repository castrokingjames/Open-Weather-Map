package com.docutools.openweathermap.presentation.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.docutools.openweathermap.domain.entities.Place;
import com.docutools.openweathermap.presentation.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class PlaceAdapter
        extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private Subject<Place> place = PublishSubject.create();
    private LayoutInflater layoutInflater;
    private List<Place> places;

    public PlaceAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        places = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Place place = places.get(position);
        holder.nameTextView.setText(place.city + ", " + place.code);
        holder.itemView.setOnClickListener(v -> this.place.onNext(place));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void addAll(List<Place> places) {
        this.places.addAll(places);
    }

    public void clear() {
        this.places.clear();
    }

    public Subject<Place> place() {
        return place;
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        private TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name_text_view);
        }
    }
}
