package com.bers.nyittutorassistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import android.app.AlertDialog;

public class MainActivity extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Button askQ = findViewById(R.id.ask_q);
        Button browseQ = findViewById(R.id.browse_q);

        askQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AskQActivity.class);
                startActivity(intent);
            }
        });

        browseQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View root = inflater.inflate(R.layout.dialog_custom, null);
                EditText passwordEditText = root.findViewById(R.id.password);

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(root)
                        // Add action buttons
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (passwordEditText.getText().toString().equals("NYIT_tutor_2023")) {
                                    Intent intent = new Intent(MainActivity.this, BrowseQuestionsActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Sorry, you entered wrong password.", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                builder.create().show();
            }
        });
    }
}

