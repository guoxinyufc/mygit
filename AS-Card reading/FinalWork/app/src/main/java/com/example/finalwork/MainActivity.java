package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button pic_bt,klg_bt,mtc_bt,game_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Listener listener=new Listener();//创建监听
        pic_bt=findViewById(R.id.picture);
        pic_bt.setOnClickListener(listener);
        klg_bt=findViewById(R.id.knowledge);
        klg_bt.setOnClickListener(listener);
        mtc_bt=findViewById(R.id.match);
        mtc_bt.setOnClickListener(listener);
        game_bt=findViewById(R.id.game);
        game_bt.setOnClickListener(listener);
    }
    class Listener implements View.OnClickListener{//创建监听类
        @Override
        public void onClick(View v){
            //直接获得名字
            String i=getResources().getResourceEntryName(v.getId());
            //点击不同按钮有不同操作
            switch (i){
                case "picture":
                    Intent inpic =new Intent(MainActivity.this, PictureActivity.class);
                    startActivity(inpic);
                    break;
                case "knowledge"://显式跳转
                    Intent inklg = new Intent(MainActivity.this,KnowledgeActivity.class);
                    startActivity(inklg);//启动百科
                    break;
                case "match"://显式跳转
                    Intent inmtc = new Intent(MainActivity.this,MatchActivity.class);
                    startActivity(inmtc);//启动匹配
                    break;
                case "game"://显式跳转
                    Intent ingame = new Intent(MainActivity.this,GameActivity.class);
                    startActivity(ingame);//启动游戏
                    break;
                default:
                    throw new IllegalStateException("Unexspected value:"+v.getId());//丢出报错
            }
        }
    }
}