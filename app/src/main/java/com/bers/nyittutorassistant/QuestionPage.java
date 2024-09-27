package com.bers.nyittutorassistant;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;

public class QuestionPage extends ComponentActivity implements AdapterView.OnItemSelectedListener {
    EditText titleBox;
    EditText descriptionBox;
    EditText codes;
    Button finish;
    Spinner dropdown;
    TextView category;
    ProgressBar progressBar;
    ScrollView root;
    MainViewModel viewModel;
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            updateFinishButtonState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_page);
        String selectedCategory = getIntent().getStringExtra("category");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        category = findViewById(R.id.category);
        category.setText("Category: " + selectedCategory);
        finish = findViewById(R.id.finish);
        titleBox = findViewById(R.id.titleBox);
        codes = findViewById(R.id.codeBox);
        descriptionBox = findViewById(R.id.descriptionBox);
        root = findViewById(R.id.root);
        titleBox.addTextChangedListener(textWatcher);
        descriptionBox.addTextChangedListener(textWatcher);

        dropdown = findViewById(R.id.tutorSpinner);
        progressBar = findViewById(R.id.progressBar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.tutorNames);

        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                viewModel.updateQuestionData(new QuestionData(titleBox.getText().toString(), dropdown.getSelectedItem().toString(), codes.getText().toString(), descriptionBox.getText().toString(), selectedCategory));
            }
        });

        viewModel.getViewState().observe(this, viewState -> {
            if (viewState == MainViewModel.ViewState.SUCCESS) {
                Toast.makeText(QuestionPage.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionPage.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                root.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(QuestionPage.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateFinishButtonState();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void updateFinishButtonState() {
        if (!titleBox.getText().toString().isEmpty() && !descriptionBox.getText().toString().isEmpty() && !dropdown.getSelectedItem().toString().equals("Select Name")) {
            finish.setEnabled(true);
        } else {
            finish.setEnabled(false);
        }
    }
}
