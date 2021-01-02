package com.example.recipelister;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class CategoryActivity extends AppCompatActivity {
    ListView CategoryLV;
    ArrayList<String> Categories;
    Button AddCategory;
    JSONWorker jworker;
    String mainkey;
    String key;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        i = new Intent(this, RecipeActivity.class);
        mainkey = "Categories";
        key = "Category";
        jworker = new JSONWorker(this, "/categories.json");
        jworker.InitializeJFile(mainkey);
        CategoryLV = (ListView)findViewById(R.id.Category_ListView);
        Categories = jworker.ReadValuesFromJFile(mainkey, key);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_button_list, R.id.Button_List_Text, Categories);
        CategoryLV.setAdapter(adapter);
        AddCategory = (Button)findViewById(R.id.Button_Add_Category);
        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                builder.setTitle("New Category");

// Set up the input
                final EditText input = new EditText(CategoryActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String NewCategoryName = input.getText().toString();
                        jworker.AddObjToJArray(mainkey, key, NewCategoryName);
                        Categories = jworker.ReadValuesFromJFile(mainkey, key);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(CategoryActivity.this, R.layout.activity_button_list, R.id.Button_List_Text, Categories );
                        CategoryLV.setAdapter(adapter1);
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

    public void ButtonClickHandler(View view) {
        Button b = (Button)view;
        Intent i = new Intent(this,RecipeActivity.class);
        i.putExtra("RECIPE_HEADER", b.getText().toString());
        startActivity(i);
    }
}