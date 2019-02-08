package com.example.diego.roomnet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Conexao {

    private static String dados = null;

    public static String GetArduino(String urlString){

        try {
            URL url = new URL(urlString);
            HttpURLConnection HttpURLConnection = (HttpURLConnection) url.openConnection();

            if(HttpURLConnection.getResponseCode() == 200 ){

                InputStream InputStream = new BufferedInputStream(HttpURLConnection.getInputStream());

                BufferedReader BufferedReader = new BufferedReader(new InputStreamReader(InputStream, "UTF-8"));

                StringBuilder StringBuilder = new StringBuilder();

                String Linha;

                while ((Linha = BufferedReader.readLine()) != null){
                    StringBuilder.append(Linha);
                }

                dados = StringBuilder.toString();

                HttpURLConnection.disconnect();

            }
        } catch (IOException ERRO){
            return null;
        }

        return dados;
    }
}
