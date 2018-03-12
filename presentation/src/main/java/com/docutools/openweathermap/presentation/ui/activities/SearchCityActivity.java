package com.docutools.openweathermap.presentation.ui.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.docutools.openweathermap.domain.entities.Place;
import com.docutools.openweathermap.presentation.R;
import com.docutools.openweathermap.presentation.ui.adapters.PlaceAdapter;
import com.docutools.openweathermap.presentation.ui.adapters.decorator.GridDividerDecoration;
import com.docutools.openweathermap.presentation.ui.mvvm.MvvmActivity;
import com.docutools.openweathermap.presentation.viewmodels.SearchCityViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Notification;

public class SearchCityActivity
        extends MvvmActivity<SearchCityViewModel> {

    public static final int REQUEST_CODE = 0x1237;
    public static final int RESULT_CODE = 0x1237;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchCityActivity.class);
        return intent;
    }

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    private EditText searchCityEditText;
    private ImageView clearImageView;
    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        searchCityEditText = findViewById(R.id.search_city_edit_text);
        clearImageView = findViewById(R.id.clear_image_view);
        placeAdapter = new PlaceAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new GridDividerDecoration(this));
        recyclerView.setAdapter(placeAdapter);

        searchCityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                clearImageView.setVisibility(charSequence.length() == 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchCity(editable.toString());
            }
        });

        clearImageView.setOnClickListener(v -> searchCityEditText.setText(null));

        searchCityEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearchClick(v);
                dismissKeyBoard(searchCityEditText);
                return true;
            }
            return false;
        });

        toolbar.setNavigationOnClickListener(v -> finish());

        viewModel
                .city()
                .observe(this, this::onSearchCity);

        placeAdapter
                .place()
                .subscribe(this::onPlaceSelected);
    }

    private void onPlaceSelected(Place place) {
        Intent intent = new Intent();
        intent.putExtra(Place.class.getSimpleName(), place);
        setResult(RESULT_CODE, intent);
        finish();
    }

    private void onSearchCity(Notification<List<Place>> notification) {
        if (notification.isOnNext()) {
            List<Place> places = notification.getValue();
            placeAdapter.clear();
            placeAdapter.addAll(places);
            placeAdapter.notifyDataSetChanged();
        }
    }

    private void dismissKeyBoard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void onSearchClick(TextView textView) {
        String query = textView.getText().toString();
        searchCity(query);
    }

    private void searchCity(String query) {
        viewModel.searchCity(query);
    }

    @NonNull
    @Override
    public SearchCityViewModel createViewModel() {
        return ViewModelProviders.of(this, viewModelFactory).get(SearchCityViewModel.class);
    }
}
