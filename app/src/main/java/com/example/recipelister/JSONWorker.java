package com.example.recipelister;

import android.content.Context;
import android.os.FileUtils;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

    public void InitializeJFile(String key){
        Gson gson = new Gson();
        JsonArray arr = new JsonArray();
        JsonObject obj = new JsonObject();
        obj.add("Category", gson.toJsonTree("test"));
        arr.add(obj);
        AddObjToJson(key, new JsonArray(), false);

    }

    public String CreateNewJObject(String key, JsonElement value){
        Gson gson = new Gson();
        String Jstring = null;
        JsonObject obj = new JsonObject();
        obj.add(key, value);
        Jstring = gson.toJson(obj);
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
            fileOutputStream = new FileOutputStream(this.jfile, flag);

            fileOutputStream.write(jObj.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject ReadJObjectFromJson(){
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(ReadJson(), JsonObject.class);
        return obj;
    }

    public JsonArray ReadJArrayFromJson(String mainkey) {
        JsonObject obj = ReadJObjectFromJson();
        JsonArray arr = null;
        arr = obj.getAsJsonArray(mainkey);
        return arr;
    }

    public void AddObjToJson(String key, JsonElement value, boolean flag){
        String newJObjectString = CreateNewJObject(key, value);
        WriteJson(newJObjectString, flag);
    }

    public void AddObjToJArray(String mainKey, String key, String value){
        Gson gson = new Gson();
        JsonArray arr = ReadJArrayFromJson(mainKey);
        JsonObject obj = new JsonObject();
        obj.add(key,gson.toJsonTree(value));
        arr.add(obj);
        AddObjToJson(mainKey, arr, false);
    }

    public String[] ReadValuesFromJFile(String mainkey){
        JsonArray arr = ReadJArrayFromJson(mainkey);
        String[] values = {"No Current Categories"};
        for (int i = 0; i < arr.size(); i++) {
            values[i] = arr.get(i).toString();
        }
        return values;
    }
}