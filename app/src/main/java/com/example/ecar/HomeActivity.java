package com.example.ecar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class HomeActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void actionStart(Context context,String userName,String totoalOrder,String confirmzString,String finishedOrder){
        Intent intent  = new Intent(context,HomeActivity.class);
        context.startActivity(intent);
    }
}
