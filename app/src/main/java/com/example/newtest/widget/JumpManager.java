package com.example.newtest.widget;

import android.content.Context;
import android.content.Intent;

import com.example.newtest.MainActivity;
import com.example.newtest.SecondActivity;
import com.example.newtest.ThreeActivity;

public class  JumpManager {

    public static void jumpActivity(Context context,Class classes){
        Intent intent = new Intent(context,classes);
        context.startActivity(intent);
    }
}
