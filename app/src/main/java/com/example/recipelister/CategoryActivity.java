package com.example.recipelister;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;



public class CategoryActivity extends AppCompatActivity {
    ListView CategoryLV;
    String[] Categories;
    Button AddCategory;
    JSONWorker jworker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        jworker = new JSONWorker(this);
        jworker.InitializeJFile("Categories");
        CategoryLV = (ListView)findViewById(R.id.Category_ListView);
        Categories = jworker.ReadValuesFromJFile("Categories");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_category_list, R.id.Category_List_Text, Categories);
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
                        jworker.AddObjToJArray("Categories", "Category", NewCategoryName);
                        Categories = jworker.ReadValuesFromJFile("Categories");
                        CategoryLV.add
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