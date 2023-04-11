package com.laironlf.kitchen_master.DB;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class UserProducts {
    static final String TAG = "UserProducts";
    public static String fileName = "appdata";
    public static ArrayList<Product> userProducts = new ArrayList<>();
    public static Context mContext = null;
    public static void setContext(Context context){
        mContext = context;
    }
    public static void capacity(int capacity){
        userProducts.ensureCapacity(capacity);
    }
    public static void add(Product product){
        userProducts.add(product);
    }
    public static void remove(Product product){
        userProducts.remove(product);
    }
    public static void remove(int index){
        userProducts.remove(index);
    }
    public static String getString(){
        StringBuilder res = new StringBuilder();
        if (userProducts.size() == 0){
            return "";
        }
        for (Product i : userProducts) {
            res.append(i.productID).append(",");
        }
        res.delete(res.length()-1,res.length());
        return res.toString();
    }
    private static String stringForWrite(){
        StringBuilder res = new StringBuilder();
        for (Product i : userProducts) {
            res.append(i.productID).append(":").append(i.name).append("|");
        }
        res.delete(res.length()-1,res.length());
//        Log.i(TAG, "stringForWrite: " + res.toString());
        return res.toString();
    }
    public static void writeData(){
//        Log.i(TAG, "writeData: " + userProducts.size());
//        if (userProducts.size() == 0) return;
        FileOutputStream fos = null;
        try {
            String text;
            if (userProducts.size() == 0) text = "";
            else {
                text = stringForWrite();
            }
            fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (IOException | NullPointerException ex) {
            Log.e(TAG, "writeData: \n"+ex);
        } finally {
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){
                Log.e(TAG, "writeData: \n"+ex);
            }
        }
    }
    public static void readData(){
//        Log.i(TAG, "readData: " + userProducts.size());
        if (userProducts.size() != 0) return;
        FileInputStream fin = null;
        try {
            fin = mContext.openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            if (text.equals("")){
                return;
            }
            String[] t = text.split("\\|");
            userProducts.ensureCapacity(t.length);
            for (String i : t) {
                String[] g = i.split(":");
                userProducts.add(new Product(Integer.parseInt(g[0]), g[1]));
            }
        }
        catch(IOException | java.lang.NullPointerException ex) {
            Log.e(TAG, "readData: \n"+ex);
        } finally {
            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){
                Log.e(TAG, "readData: \n"+ex);
            }
        }
    }
}
