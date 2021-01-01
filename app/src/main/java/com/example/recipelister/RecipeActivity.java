package com.example.recipelister;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    ListView RecipeLV;
    ArrayList<String> Recipes;
    Button AddRecipe;
    JSONWorker jworker;
    String mainkey;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mainkey = "Recipes";
        key = "Recipe";
        jworker = new JSONWorker(this, "/recipes.json");
        jworker.InitializeJFile(mainkey);
        RecipeLV = (ListView)findViewById(R.id.Category_ListView);
        Recipes = jworker.ReadValuesFromJFile(mainkey, key);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_button_list, R.id.Category_List_Text, Recipes);
        RecipeLV.setAdapter(adapter);
        AddRecipe = (Button)findViewById(R.id.Button_Add_Category);
        AddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeActivity.this);
                builder.setTitle("New Recipe");

// Set up the input
                final EditText input = new EditText(RecipeActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String NewRecipeName = input.getText().toString();
                        jworker.AddObjToJArray(mainkey, key, NewRecipeName);
                        Recipes = jworker.ReadValuesFromJFile(mainkey, key);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(RecipeActivity.this, R.layout.activity_button_list, R.id.Category_List_Text, Recipes);
                        RecipeLV.setAdapter(adapter1);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }
}