package com.example.recipelister;

import android.content.Context;
import android.os.FileUtils;


import com.google.gson.Gson;

import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class JSONWorker {
    Context con;
    String path;
    File jfile;

    public JSONWorker(Context con){
        this.con = con;
        this.path = con.getFilesDir() + "/test.json";
        this.jfile = new File(path);
    }

    public void InitializeJFile(){
        Gson gson = new Gson();
        JSONObject obj = gson.fromJson(CreateNewJObject("Categories", new JSONArray()), JSONObject.class);

    }

    public String CreateNewJObject(String key, Object value){
        Gson gson = new Gson();
        String Jstring = null;
        try {
            Jstring = gson.toJson(new org.json.JSONObject().put(key,value));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Jstring;
    }

    public String ReadJson(){
        String text = "";
        try {
            //Make your FilePath and File
            //Make an InputStream with your File in the constructor
            InputStream inputStream = new FileInputStream(this.jfile);
            StringBuilder stringBuilder = new StringBuilder();
            //Check to see if your inputStream is null
            //If it isn't use the inputStream to make a InputStreamReader
            //Use that to make a BufferedReader
            //Also create an empty String
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                //Use a while loop to append the lines from the Buffered reader
                while ((receiveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(receiveString);
                }
                //Close your InputStream and save stringBuilder as a String
                inputStream.close();
                text = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            //Log your error with Log.e
        } catch (IOException e) {
            //Log your error with Log.e
        }
        return text;
    }

    public void WriteJson(String jObj, boolean flag){

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(this.jfile,flag);

            fileOutputStream.write(jObj.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ReadValuesFromJson(){
        Gson gson = new Gson();
        org.json.JSONObject obj = gson.fromJson(ReadJson(), JSONObject.class);
        return obj.toString();
    }

    public void AddObjToJson(String key, String value, boolean flag){
        String newJObjectString = CreateNewJObject(key, value);
        WriteJson(newJObjectString, flag);
    }
}
