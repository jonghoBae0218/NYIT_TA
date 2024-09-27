package com.bers.nyittutorassistant;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

public class BrowseQuestionsActivity extends ComponentActivity implements AdapterView.OnItemSelectedListener {
    Spinner dropdown;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_q_activity);

        dropdown = findViewById(R.id.tutorSpinner);
        next = findViewById(R.id.next);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Constants.tutorNames);

        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowseQuestionsActivity.this, BrowseTitleActivity.class);
                intent.putExtra("tutorName", dropdown.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        next.setEnabled(position != 0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
