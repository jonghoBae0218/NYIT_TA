package com.bers.nyittutorassistant;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;

public class QuestionViewActivity extends ComponentActivity {
    EditText titleBox;
    EditText descriptionBox;
    EditText codes;
    Button finish;
    Spinner dropdown;
    TextView category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_page);

        QuestionData questionData = (QuestionData) getIntent().getSerializableExtra("question");

        category = findViewById(R.id.category);
        finish = findViewById(R.id.finish);
        titleBox = findViewById(R.id.titleBox);
        codes = findViewById(R.id.codeBox);
        dropdown = findViewById(R.id.tutorSpinner);
        descriptionBox = findViewById(R.id.descriptionBox);

        finish.setVisibility(View.GONE);
        titleBox.setEnabled(false);
        codes.setEnabled(false);
        descriptionBox.setEnabled(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{questionData.tutorName});
        dropdown.setAdapter(adapter);
        dropdown.setEnabled(false);

        titleBox.setText(questionData.title);
        codes.setText(questionData.codes);
        descriptionBox.setText(questionData.description);
        category.setText("Category: " + questionData.category);
    }
}
