package com.example.recipelister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ButtonListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_list);
    }

    public void ButtonClickHandler(View view) {
        Button b = (Button)view;
        Intent i = new Intent(this,RecipeActivity.class);
        i.putExtra("RECIPE_HEADER", b.getText().toString());
        startActivity(i);
    }
}