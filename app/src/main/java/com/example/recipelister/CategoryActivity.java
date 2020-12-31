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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        JSONWorker.InitializeFile(this);
        CategoryLV = (ListView)findViewById(R.id.Category_ListView);
        Categories = JSONWorker.ReadValuesFromJFile(this , "Category");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_category_list, R.id.Category_List_Text, Categories);
        CategoryLV.setAdapter(adapter);
        AddCategory = (Button)findViewById(R.id.Button_Add_Category);
        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                builder.setTitle("Title");

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
                        JSONWorker.AddObjToJFile(CategoryActivity.this , "Category", NewCategoryName);
                        Categories = JSONWorker.ReadValuesFromJFile(CategoryActivity.this, "Category");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategoryActivity.this, R.layout.activity_category_list, R.id.Category_List_Text, Categories);
                        CategoryLV.setAdapter(adapter);
                        CategoryActivity.this.recreate();
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