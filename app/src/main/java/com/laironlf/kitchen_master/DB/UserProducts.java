package com.laironlf.kitchen_master.DB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class UserProducts {
    public static String fileName = "appdata";
    public static ArrayList<Product> userProducts = new ArrayList<>();

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

    public static void writeData(){
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(fileName, MODE_PRIVATE)));
            // пишем данные
            bw.write("Содержимое файла");
            // закрываем поток
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readData(){
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(fileName)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
