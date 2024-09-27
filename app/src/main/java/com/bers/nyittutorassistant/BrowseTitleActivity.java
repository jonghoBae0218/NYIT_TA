package com.bers.nyittutorassistant;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BrowseTitleActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_title_activity);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        String tutorName = getIntent().getStringExtra("tutorName");

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getQuestionData(tutorName);

        viewModel.getViewState().observe(this, viewState -> {
            if (viewState == MainViewModel.ViewState.SUCCESS) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(new QuestionsAdapter(this, viewModel.getQuestionDataList()));
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(BrowseTitleActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
