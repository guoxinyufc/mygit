package com.example.finalwork.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.finalwork.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link testFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class testFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int imageId;
    private String question;
    private String[] answer;
    private AlertDialog dialog;
    public testFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment testFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static testFragment newInstance(String param1, String param2) {
        testFragment fragment = new testFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        imageId=getArguments().getInt("imageId");
        question=getArguments().getString("question");
        answer=getArguments().getStringArray("answer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_test, container, false);
        //设置图片
        ImageView image=root.findViewById(R.id.image_question);
        image.setImageResource(imageId);
        //设置按钮文字
        Button b1=root.findViewById(R.id.answer1);
        b1.setText(answer[0]);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b1.getText()==question){
                    b1.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else{
                    b1.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });
        Button b2=root.findViewById(R.id.answer2);
        b2.setText(answer[1]);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b2.getText()==question){
                    b2.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else{
                    b2.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });
        Button b3=root.findViewById(R.id.answer3);
        b3.setText(answer[2]);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b3.getText()==question){
                    b3.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else{
                    b3.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });
        Button b4=root.findViewById(R.id.answer4);
        b4.setText(answer[3]);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b4.getText()==question){
                    b4.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else{
                    b4.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });

        return root;
    }
}