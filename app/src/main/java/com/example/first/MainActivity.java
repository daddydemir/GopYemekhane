package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.reyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParseAdapter(parseItems , this);
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute();

    }

    private class Content extends AsyncTask<Void , Void , Void > {
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            adapter.notifyDataSetChanged();

        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            int sayac = 0;
            String[] days = {"Pazartesi" , "Salı" , "Çarşamba" , "Perşembe" , "Cuma"};
            try{
                String url = "https://sosyaltesisler.gop.edu.tr/yemekhane_menu.aspx";
                Document doc = Jsoup.connect(url).get();
                Elements day = doc.select("td.style19");
                for(Element i : day){
                    sayac += 1;
                    if(sayac >= 6){
                        String a = i.toString();
                        int index = sayac - 6;
                        daysOfEat(days[index] , a);
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
                }
            return null;
        }
        public void daysOfEat(String day , String content){
            //content = content.replaceAll(" " , "");
            String alfabe = "aeıiüuoöuü0123456789";

            String sil = "";
            String[] dizi = new String[22];
            String[] yemek = new String[6];
            int index = 0;
            boolean status = false;

            for(int i =0; i<content.length();i++){

                if(content.charAt(i) == '>'){
                    status = true;
                    continue;
                }
                if(content.charAt(i) == '<'){
                    if(sil == null){
                        continue;
                    }
                    for(int a=0;a<sil.length();a++){
                        for(int b=0;b<alfabe.length();b++){
                            if(alfabe.charAt(b) == sil.charAt(a)){
                                dizi[index] = sil;
                            }
                        }
                    }
                    index += 1;
                    sil = "";
                    status = false;
                    continue;
                }
                if(status){
                    sil += content.charAt(i);
                }
            }
            index = 0;

            for(int i = 0; i<dizi.length; i++){
                if(dizi[i] == null){
                    continue;
                }
                yemek[index] = dizi[i];
                index += 1;
            }

            String _eat1 = checkCaharacter(yemek[0]);
            String _eat2 = checkCaharacter(yemek[1]);
            String _eat3 = checkCaharacter(yemek[2]);
            String _eat4 = checkCaharacter(yemek[3]);
            String _eat5 = checkCaharacter(yemek[5]);
            parseItems.add(new ParseItem(day , _eat1 , _eat2 , _eat3 , _eat4 , _eat5));

        }
        public String checkCaharacter(String eat){
            boolean stat = true;
            String tmp = "";
            if(eat == null){
                return "</daddydemir>";
            }
            if(eat.length() >= 15){
                for(int i=0; i<eat.length();i++){
                    if(eat.charAt(i) == ' ' && i >= 5 && stat){
                        tmp += "\n ";
                        stat = false;
                        continue;
                    }
                    tmp += eat.charAt(i);
                }
                return tmp;
            }
            else{
                return eat;
            }
        }
    }
}