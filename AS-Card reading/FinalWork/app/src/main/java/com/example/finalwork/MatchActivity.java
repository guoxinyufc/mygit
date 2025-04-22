package com.example.finalwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalwork.data.Timu;
import com.example.finalwork.fragment.testFragment;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {
    private List<Timu> timuList = new ArrayList<>();
    private Button btUp,btDown;
    private int i=0;//计数，第i+1题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        initTimu();
        PassDataToFragment(timuList.get(i));//初始显示
        btUp=findViewById(R.id.moveUp);
        btDown=findViewById(R.id.moveDown);
        Listener listener=new Listener();
        btUp.setOnClickListener(listener);
        btDown.setOnClickListener(listener);
    }
    //创建监听类
    class Listener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.moveUp){
                if(i==0){//已是第1题
                    Toast.makeText(MatchActivity.this,"已是第1题!",Toast.LENGTH_SHORT).show();
                }
                else if(i>0){
                    i=i-1;
                    PassDataToFragment(timuList.get(i));//显示前1题
                }
            }
            else if(view.getId()==R.id.moveDown){
                if(i==(timuList.size()-1)){//已是最后一题
                    Toast.makeText(MatchActivity.this,"已是最后一题!",Toast.LENGTH_SHORT).show();
                }
                else if(i<(timuList.size()-1)){
                    i=i+1;
                    PassDataToFragment(timuList.get(i));
                }
            }
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();//获取FragmentManager
        FragmentTransaction ft=fm.beginTransaction();//开启一个事务
        ft.replace(R.id.tm_show,fragment);//用fragment替代FrameLayout的tm_show
        ft.addToBackStack(null);//将一个事务添加到返回栈中
        ft.commit();//提交该事务
    }
    private void initTimu(){//初始化题目数据
        Timu mushroom =new Timu(R.drawable.mushroom,"香菇",new String[]{"金针菇","平菇","香菇","茶树菇"});
        timuList.add(mushroom);
        Timu cabbages=new Timu(R.drawable.cabbages,"白菜",new String[]{"青菜","白菜","芹菜","娃娃菜"});
        timuList.add(cabbages);
    }
    private void PassDataToFragment(Timu timu){//向tf碎片传输数据
        Bundle bundle=new Bundle();
        //打包数据
        int imageId = timu.getImageId();
        String question=timu.getQuestion();
        String[] answer=timu.getAnswer();
        bundle.putInt("imageId",imageId);
        bundle.putString("question",question);
        bundle.putStringArray("answer",answer);
        //创建碎片，传输数据
        testFragment tf = new testFragment();
        tf.setArguments(bundle);
        replaceFragment(tf);
    }
}