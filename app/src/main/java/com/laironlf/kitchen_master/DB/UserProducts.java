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
    public static Context mContext;
    public void setContext(Context context){
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
        return res.toString();
    }
    public static void writeData(){
        FileOutputStream fos = null;
        try {
            String text = stringForWrite();
            fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch(IOException ex) {
            Log.e(TAG, "writeData: \n"+ex);
        }
        finally{
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
        FileInputStream fin = null;
        try {
            fin = mContext.openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            String[] t = text.split("/|");
            for (String i : t) {
                String[] g = i.split(":");
                int productID = Integer.parseInt(g[0]);
                String name = g[1];
                Product temp = new Product(productID, name);
            }
        }
        catch(IOException ex) {
            Log.e(TAG, "readData: \n"+ex);
        }
        finally{
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
