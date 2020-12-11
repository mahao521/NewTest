package com.example.newtest.widget;

import android.content.Context;
import android.content.Intent;

import com.example.newtest.MainActivity;
import com.example.newtest.SecondActivity;
import com.example.newtest.ThreeActivity;

public class JumpManager {

//    public static JumpManager jumpManager = null;
//
//    private JumpManager(){
//
//    }
//
//    public static  JumpManager getInstance(){
//        if(jumpManager == null){
//            synchronized (JumpManager.class){
//                if(jumpManager == null){
//                    jumpManager = new JumpManager();
//                }
//            }
//        }
//        return jumpManager;
//    }

    public static void jumpSecondActivity(Context context){
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }

    public static void jumpThreeActivity(Context context){
        Intent intent = new Intent(context, ThreeActivity.class);
        context.startActivity(intent);
    }
}
