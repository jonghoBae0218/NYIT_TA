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

public class AskQActivity extends ComponentActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_q_activity);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.category);
//create a list of items for the spinner.
        String[] items = new String[]{"Select Category", "For Loop", "While Loop", "Class Declaration", "Others"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AskQActivity.this, QuestionPage.class);
                intent.putExtra("category", dropdown.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Button next = findViewById(R.id.next);
        TextView l1 = (TextView) findViewById(R.id.link1);
        l1.setMovementMethod(LinkMovementMethod.getInstance());

        TextView l2 = (TextView) findViewById(R.id.link2);
        l2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView l3 = (TextView) findViewById(R.id.link3);
        l3.setMovementMethod(LinkMovementMethod.getInstance());

        if(position==0){
            l1.setVisibility(View.GONE);
            l2.setVisibility(View.GONE);
            l3.setVisibility(View.GONE);
            next.setEnabled(false);
        }
        else if(position==1){
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.VISIBLE);
            l3.setVisibility(View.VISIBLE);
            l1.setText(R.string.forLoopLink1);
            l2.setText(R.string.forLoopLink2);
            l3.setText(R.string.forLoopLink3);
            next.setEnabled(true);

        }
        else if(position==2){
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.VISIBLE);
            l3.setVisibility(View.GONE);
            l1.setText(R.string.whileLoopLink1);
            l2.setText(R.string.whileLoopLink2);
            next.setEnabled(true);

        }
        else if(position == 3){
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.VISIBLE);
            l3.setVisibility(View.VISIBLE);
            l1.setText(R.string.classDecLink1);
            l2.setText(R.string.classDecLink2);
            l3.setText(R.string.classDecLink3);
            next.setEnabled(true);
        }

        else if(position == 4){
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.VISIBLE);
            l3.setVisibility(View.GONE);
            l1.setText(R.string.other1);
            l2.setText(R.string.other2);
            next.setEnabled(true);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
