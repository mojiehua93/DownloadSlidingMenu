package com.example.mojiehua93.downloadslidingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private SlidingMenu slidingMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams
        .FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        slidingMenu = (SlidingMenu) findViewById(R.id.left_menu);
    }

    public void toggleMenu(View view){
        slidingMenu.toggle();
    }
}
